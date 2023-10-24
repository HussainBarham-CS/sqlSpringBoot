package com.example.entetyRelationSQL.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CreateDB {
    private final JdbcTemplate DB;
    @Autowired
    public CreateDB(JdbcTemplate jdbcTemplate) {
        this.DB = jdbcTemplate;
    }
    public ResponseEntity<ResponseEntity> create(){

        try{
            String createUserTableSql = "CREATE TABLE `user` ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "name VARCHAR(20) NOT NULL"
                    + ");";

            String createTicketTableSql = "CREATE TABLE `ticket` ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "title VARCHAR(50) NOT NULL, "
                    + "deadLine DATE NOT NULL, "
                    + "status VARCHAR(30) NOT NULL"
                    + ");";

            String createUserPhonesTableSql = "CREATE TABLE `user_phones` ("
                    + "phone_number VARCHAR(10) NOT NULL UNIQUE, "
                    + "user_phone_id INT, "
                    + "FOREIGN KEY (user_phone_id) REFERENCES `user`(id)"
                    + ");";

            String createUserTicketsTableSql = "CREATE TABLE `user_tickets` ("
                    + "user_id INT, "
                    + "ticket_id INT, "
                    + "FOREIGN KEY (user_id) REFERENCES `user`(id), "
                    + "FOREIGN KEY (ticket_id) REFERENCES `ticket`(id)"
                    + ");";


            DB.execute(createUserTableSql);
            DB.execute(createTicketTableSql);
            DB.execute(createUserPhonesTableSql);
            DB.execute(createUserTicketsTableSql);

        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
