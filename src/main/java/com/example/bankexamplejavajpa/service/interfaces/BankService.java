package com.example.bankexamplejavajpa.service.interfaces;

import com.example.bankexamplejavajpa.entities.Bank;
import com.example.bankexamplejavajpa.entities.Konto;

import java.util.List;
import java.util.Optional;

public interface BankService {

    Bank saveBank(Bank bank);
    List<Bank> getBankList();
    Optional<Bank> getBankById(Long id);
    void deleteBank(Bank bank);
    void deleteBankById(Long id);
    List<Konto> getKontoList(Long id);
}
