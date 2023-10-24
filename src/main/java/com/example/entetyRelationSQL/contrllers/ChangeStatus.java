package com.example.entetyRelationSQL.contrllers;


import com.example.entetyRelationSQL.Services.User;
import com.example.entetyRelationSQL.StatusTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChangeStatus {

    @Autowired
    private User user;

    @RequestMapping(value="/changeStatus/{user_id}", method= RequestMethod.POST)
    public ResponseEntity<Object> changeStatusTicket(@RequestBody StatusTicket status_ticket, @PathVariable int user_id) {
        return user.changeStatusTicket(user_id , status_ticket);
    }




}
