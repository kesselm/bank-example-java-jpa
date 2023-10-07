package com.example.bankexamplejavajpa.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="konto")
@NoArgsConstructor
@AllArgsConstructor
public class Konto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String isbn;
    private String comment;
    private LocalDate date;

//    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE,
//                CascadeType.DETACH, CascadeType.REFRESH})
//    @JoinColumn(name = "bank_id")
    @ManyToOne
    private Bank bank;

}
