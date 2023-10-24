package com.example.entetyRelationSQL.contrllers;

import com.example.entetyRelationSQL.Services.CreateDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class IndexController {

    @Autowired
    CreateDB createDB;

    @GetMapping("/")
    public ResponseEntity<ResponseEntity> index(){
        return createDB.create();
    }

}
