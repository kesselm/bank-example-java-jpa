package com.example.bankexamplejavajpa.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="konto")
public class Konto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String isbn;
    private String comment;
    private LocalDate date;

    @ManyToOne
    private Bank bank;

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Buchungssatz> buchungssaetze = new ArrayList<>();

}
