package com.example.demochatroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"com.example.demochatroom.mongorepository"})
@EnableJpaRepositories(basePackages={"com.example.demochatroom.mysqlrepository"})
public class DemoChatRoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoChatRoomApplication.class, args);
    }

}
