package com.example.entetyRelationSQL.contrllers;

import com.example.entetyRelationSQL.CheckUser;
import com.example.entetyRelationSQL.Services.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateUser {
    @Autowired
    private User user;

    @PostMapping("/createUser")
    public ResponseEntity<ResponseEntity> createUser(@RequestBody CheckUser person) {
        return user.create(person);
    }}
