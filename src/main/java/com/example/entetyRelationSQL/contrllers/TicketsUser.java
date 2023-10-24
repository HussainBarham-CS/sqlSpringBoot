package com.example.entetyRelationSQL.contrllers;


import com.example.entetyRelationSQL.Services.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TicketsUser {

    @Autowired
    private User user;

    @RequestMapping(value="/getTickets/{user_id}", method= RequestMethod.GET)
    public ResponseEntity<Object> getTickets( @PathVariable int user_id) {
        return user.getTickets(user_id);
    }
}
