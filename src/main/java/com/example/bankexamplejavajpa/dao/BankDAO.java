package com.example.bankexamplejavajpa.dao;


import lombok.Data;

import java.time.LocalDate;

@Data
public class BankDAO {
    private String name;
    private String address;
    private String comment;
    private LocalDate date;
}
