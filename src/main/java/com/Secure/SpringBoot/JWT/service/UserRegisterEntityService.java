package com.Secure.SpringBoot.JWT.service;

import com.Secure.SpringBoot.JWT.entity.UserRegisterEntity;
import com.Secure.SpringBoot.JWT.repository.UserRegisterEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterEntityService implements UserDetailsService {

    @Autowired
    private UserRegisterEntityRepository userAuthEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAuthEntityRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public UserDetails save(UserRegisterEntity userRegisterEntity) {
        return userAuthEntityRepository.save(userRegisterEntity);
    }

}
