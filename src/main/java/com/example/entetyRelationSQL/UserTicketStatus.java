package com.example.entetyRelationSQL;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserTicketStatus {
    private String user_name;
    private String title;
    private String status;
    public UserTicketStatus(){}

}