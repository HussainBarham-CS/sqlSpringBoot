package com.example.entetyRelationSQL.contrllers;


import com.example.entetyRelationSQL.CheckTicket;
import com.example.entetyRelationSQL.Services.Ticket;
import com.example.entetyRelationSQL.Services.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateTicket {
    @Autowired
    private Ticket ticket;

    @PostMapping("/createTicket/{userId}")
    public ResponseEntity<ResponseEntity> createTickit(@RequestBody CheckTicket tick, @PathVariable int userId){
        return ticket.create(tick,userId);
    }
}
