package com.example.dataserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class DataserverApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataserverApplication.class, args);
    }

}
