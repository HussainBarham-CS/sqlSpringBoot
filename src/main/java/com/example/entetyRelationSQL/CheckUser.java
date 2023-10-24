package com.example.entetyRelationSQL;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CheckUser {
    @NotNull
    private String name;
    private String[]phones;

    public CheckUser(){}
}