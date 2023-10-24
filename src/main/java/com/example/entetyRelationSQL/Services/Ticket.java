package com.example.entetyRelationSQL.Services;

import com.example.entetyRelationSQL.CheckTicket;
import com.example.entetyRelationSQL.CheckUser;
import com.example.entetyRelationSQL.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class Ticket {

    private final JdbcTemplate DB;
    @Autowired
    public Ticket(JdbcTemplate jdbcTemplate) {
        this.DB = jdbcTemplate;
    }

    public ResponseEntity<ResponseEntity> create(CheckTicket ticket ,int userId){

        try{
            Status status = null;
            DB.execute("INSERT INTO `ticket`(`title`,`deadLine`, `status` ) VALUES ('"+ ticket.getTitle()+"','"+LocalDateTime.now()+"','"+status.pending+"')");
            DB.query("SELECT * FROM `ticket` ORDER BY `id` DESC LIMIT 1;", new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    try {
                        DB.execute("INSERT INTO `user_tickets`(`user_id`, `ticket_id`) VALUES (" + userId + "," + rs.getLong("id")+")");

                    } catch (Exception e) {

                        DB.execute("DELETE FROM `ticket` WHERE `ticket_id` ="+rs.getLong("id"));

                        throw new RuntimeException("error");
                    }
                    return null;
                }
            });


        }catch (Exception e){
            throw new RuntimeException("error");
        }


        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
    }
