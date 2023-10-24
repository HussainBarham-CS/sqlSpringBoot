package com.example.entetyRelationSQL;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class CheckTicket {
    @NotNull
    private String title;

    public CheckTicket(){}

}
