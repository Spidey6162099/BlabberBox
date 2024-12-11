package com.example.demochatroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class DemoChatRoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoChatRoomApplication.class, args);
    }

}
