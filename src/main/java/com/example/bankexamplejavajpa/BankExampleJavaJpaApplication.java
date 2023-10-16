package com.example.bankexamplejavajpa;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Bank Example Java JPA Api", version = "1.0", description = "A simple CRUD application with REST."))
public class BankExampleJavaJpaApplication  {

    public static void main(String[] args) {
        SpringApplication.run(BankExampleJavaJpaApplication.class, args);
    }

}
