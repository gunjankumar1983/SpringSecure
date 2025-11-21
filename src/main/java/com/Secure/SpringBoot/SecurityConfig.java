package com.Secure.SpringBoot;

import com.Secure.SpringBoot.OAuth.Util.OAuthTokenValidatorUtil;
import com.Secure.SpringBoot.OAuth.filter.CustomOAuth2SuccessHandler;
import com.Secure.SpringBoot.OAuth.filter.OAuthValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
Replace this SecurityConfig with individual package Security Config class:
for example:
if you want to test the JWT functionality, in JWT package there is SecurityConfigForJWT, copy and paste here
 (just uncomment the Bean and configuration annotation)

Similarly, if you want to test OAuth functionality, in OAuth package, there is SecurityConfigForOAuth, same
copy and paste it (just uncomment the bean and configuration annotation)
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private OAuthTokenValidatorUtil tokenValidatorUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   CustomOAuth2SuccessHandler successHandler) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Use sessions
                )
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable())
                )
                .oauth2Login(oauth -> oauth
                        .loginPage("/login") // Custom login page
                        .successHandler(successHandler)
                )
                .logout(logout -> logout.logoutSuccessUrl("/login").permitAll())
                .addFilterBefore(
                        new OAuthValidationFilter(tokenValidatorUtil),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
