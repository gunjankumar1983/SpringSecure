package com.Secure.SpringBoot.OAuth.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserDetailsController {

    @GetMapping("/")
    public String defaultHomePageMethod(){
        return "hello, you are logged in";
    }
    @GetMapping("/users")
    public String getUsersDetails(){
        return "fetched the details of successfully";
    }
}



