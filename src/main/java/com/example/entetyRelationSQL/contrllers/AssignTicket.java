package com.example.entetyRelationSQL.contrllers;

import com.example.entetyRelationSQL.Services.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AssignTicket {

    @Autowired
    private User user;

    @RequestMapping(value="/assignTicket/{user_id}", method= RequestMethod.POST)
    public ResponseEntity<Object> connect(@RequestBody int tickit_id , @PathVariable int user_id) {
        return user.connect(tickit_id,user_id);
    }
}
