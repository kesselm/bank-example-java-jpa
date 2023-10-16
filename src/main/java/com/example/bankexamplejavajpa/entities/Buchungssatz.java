package com.example.bankexamplejavajpa.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name="buchungssatz")
public class Buchungssatz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String buchungsart;
    private String buchungstext;
    private String auftraggeber;
    private Double betrag;
    private String waehrung;
    private LocalDate buchungsdatum;

    @ManyToOne(cascade=CascadeType.ALL)
    private Konto konto;
}
