package com.example.bankexamplejavajpa.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="bank")
@NoArgsConstructor
@AllArgsConstructor
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String address;
    private String comment;
    private LocalDate date;

//    @OneToMany(mappedBy = "bank", cascade={CascadeType.PERSIST, CascadeType.MERGE,
//                                CascadeType.DETACH, CascadeType.REFRESH})

    @OneToMany(fetch = FetchType.LAZY)
    public List<Konto> kontos;

}
