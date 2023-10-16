package com.example.bankexamplejavajpa.service.interfaces;

import com.example.bankexamplejavajpa.entities.Bank;
import com.example.bankexamplejavajpa.entities.Buchungssatz;
import com.example.bankexamplejavajpa.entities.Konto;

import java.util.List;
import java.util.Optional;

public interface KontoService {

    Konto saveKonto(Konto konto);
    List<Konto> getKontoList();
    Optional<Konto> getKontoById(Long id);
    void deleteKonto(Konto konto);
    void deleteKontoById(Long id);
    Optional<Bank> getBankFromKonto(Long id);
    List<Buchungssatz> getBuchungsaetzeFromKonto(Long id);

}
