package com.spring.catalog.products.controller;

import com.spring.catalog.products.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping()
    public ResponseEntity<User> getImage() {
        return new ResponseEntity<>(new User(), HttpStatus.OK);
    }
}
