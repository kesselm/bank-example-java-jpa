package com.example.bankexamplejavajpa.service.interfaces;

import com.example.bankexamplejavajpa.entities.Buchungssatz;

import java.util.List;
import java.util.Optional;

public interface BuchungsatzService {

    Buchungssatz saveBuchungssatz(Buchungssatz buchungssatz);
    List<Buchungssatz> getBuchungssatzList();
    Optional<Buchungssatz> getBuchungssatzById(Long id);
    void deleteBuchungssatz(Buchungssatz buchungssatzDAO);
    void deleteBuchungssatzById(Long id);

}
