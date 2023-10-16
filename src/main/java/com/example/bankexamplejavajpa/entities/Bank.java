package com.example.bankexamplejavajpa.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="bank")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String address;
    private String comment;
    private LocalDate date;

    @OneToMany(fetch = FetchType.LAZY)
    public List<Konto> kontos = new ArrayList<>();

}
