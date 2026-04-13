package com.iam.identity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/users")
public class UsersController {
    @GetMapping("/{userId}")
    public String getUser(@PathVariable String userId){
        return userId;
    }
}