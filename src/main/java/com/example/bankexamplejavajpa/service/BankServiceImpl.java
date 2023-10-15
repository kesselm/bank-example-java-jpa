package com.example.bankexamplejavajpa.service;

import com.example.bankexamplejavajpa.entities.Bank;
import com.example.bankexamplejavajpa.entities.Konto;
import com.example.bankexamplejavajpa.repository.BankRepository;
import com.example.bankexamplejavajpa.service.interfaces.BankService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class BankServiceImpl implements BankService {

    private BankRepository bankRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }


    @Override
    public Bank saveBank(Bank bank) {
        return bankRepository.save(bank);
    }

    @Override
    public List<Bank> getBankList() {
        return bankRepository.findAll();
    }

    @Override
    public Optional<Bank> getBankById(Long id) {
        return bankRepository.findById(id);
    }

    @Override
    public void deleteBank(Bank bank) {
        bankRepository.delete(bank);
        log.info("Bank object {} is deleted", bank.getId());
    }

    @Override
    public void deleteBankById(Long id) {
        bankRepository.deleteById(id);
    }

    @Override
    public List<Konto> getKontoList(Long id) {
        Optional<Bank> bank = bankRepository.findById(id);
        if(bank.isPresent()) {
            return bank.get().getKontos();
        } else {
            return Arrays.asList();
        }
    }

}
