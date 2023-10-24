package com.example.entetyRelationSQL.Services;


import com.example.entetyRelationSQL.CheckUser;
import com.example.entetyRelationSQL.Status;
import com.example.entetyRelationSQL.StatusTicket;
import com.example.entetyRelationSQL.UserTicketStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class User {


    private final JdbcTemplate DB;
    @Autowired
    public User(JdbcTemplate jdbcTemplate) {
        this.DB = jdbcTemplate;
    }

    public ResponseEntity<ResponseEntity> create(CheckUser person)
    {
        try {
            DB.execute("INSERT INTO `user` (`name`) VALUES ('" + person.getName() + "');");
            DB.query("SELECT * FROM `user` ORDER BY `id` DESC LIMIT 1;", new RowMapper<User>() {

                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    String []phones = person.getPhones();
                    int check=0;
                    for(int i=0;i<person.getPhones().length;i++){
                        try{
                            DB.execute("INSERT INTO `user_phones`(`phone_number`, `user_phone_id`) VALUES ('"+phones[i]+"',"+rs.getLong("id")+")");

                        }catch (Exception e) {
                            check=1;
                        }

                    }
                    if(check==1){
                        DB.execute("DELETE FROM `user` WHERE `id`="+rs.getLong("id"));
                        DB.execute("DELETE FROM `user_phones` WHERE `id`="+rs.getLong("id"));
                        throw new RuntimeException("error");

                    }

                    return null;
                }


            });


        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>( HttpStatus.ACCEPTED);
    }
    public ResponseEntity<Object> connect(int tickit_id , int user_id){

        int result = DB.queryForObject("SELECT CASE " +
                "WHEN EXISTS (SELECT 1 FROM `user` WHERE id = "+user_id+") AND EXISTS (SELECT 1 FROM `ticket` WHERE id = "+tickit_id+") THEN 1 " +
                "ELSE 0 " +
                "END", Integer.class);
        if(result==1){
            DB.execute("INSERT INTO `user_tickets`(`user_id`, `ticket_id`) VALUES ("+user_id+","+tickit_id+")");
        }
        else{
            new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Object> getTickets( int user_id) {

        int check_id = DB.queryForObject("SELECT CASE " +
                "WHEN EXISTS (SELECT 1 FROM `user` WHERE id = "+user_id+") THEN 1 " +
                "ELSE 0 " +
                "END", Integer.class);
        if(check_id==0){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        else{
            List<UserTicketStatus> list = new ArrayList<>();
            DB.query("SELECT u.name AS user_name, t.title, t.status\n" +
                    "FROM user u\n" +
                    "LEFT JOIN user_tickets ut ON u.id = ut.user_id\n" +
                    "LEFT JOIN ticket t ON ut.ticket_id = t.id\n" +
                    "WHERE u.id = "+user_id+";", new RowMapper<User>() {


                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    try {
                        UserTicketStatus temp = new UserTicketStatus();
                        temp.setTitle(rs.getNString("title"));
                        temp.setStatus(rs.getNString("status"));
                        temp.setUser_name(rs.getNString("user_name"));
                        list.add(temp);

                    } catch (Exception e) {
                        throw new RuntimeException("error");
                    }
                    return null;
                }



            });

            return new ResponseEntity<>(list,HttpStatus.ACCEPTED);
        }





    }

    public ResponseEntity<Object> changeStatusTicket(int user_id , StatusTicket status){

        if(status.getStatus().equals(Status.done.toString())|| status.getStatus().equals(Status.in_progress.toString())|| status.getStatus().equals(Status.pending.toString())){
            int result = DB.queryForObject("SELECT CASE " +
                    "WHEN EXISTS (SELECT 1 FROM `user` WHERE id = "+user_id+")THEN 1 " +
                    "ELSE 0 " +
                    "END", Integer.class);

            if(result==1){
                try{
                    DB.execute("UPDATE ticket\n" +
                            "SET status = '"+status.getStatus()+"'\n" +
                            "WHERE id = 1\n" +
                            "  AND id IN (\n" +
                            "    SELECT ticket_id\n" +
                            "    FROM `user_tickets`\n" +
                            "    WHERE user_id = "+user_id+"\n" +
                            "  );");
                }catch(Exception e){
                    return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
                }
            }
        }
        else{
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }


        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
