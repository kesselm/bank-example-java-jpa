package com.example.bankexamplejavajpa.service;

import com.example.bankexamplejavajpa.entities.Bank;
import com.example.bankexamplejavajpa.entities.Konto;
import com.example.bankexamplejavajpa.repository.BankRepository;
import com.example.bankexamplejavajpa.service.interfaces.BankService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.bankexamplejavajpa.util.WebConstants.CUSTOM_ERROR_IDENTIFIER;

@Service
@Slf4j
public class BankServiceImpl implements BankService {

    private BankRepository bankRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }


    @Override
    public Bank saveBank(Bank bank) {
        Bank newBank = bankRepository.save(bank);
        log.info("{} New bank with id: {} is persisted.", CUSTOM_ERROR_IDENTIFIER, bank.getId());
        return newBank;
    }

    @Override
    public List<Bank> findAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public Optional<Bank> findBankById(Long id) {
        return bankRepository.findById(id);
    }

    @Override
    public void deleteBank(Bank bank) {
        bankRepository.delete(bank);
        log.info("{} Bank with id: {} was deleted.", CUSTOM_ERROR_IDENTIFIER, bank.getId());
    }

    @Override
    public void deleteBankById(Long id) {
        log.info("{} Bank with id: {} was deleted.", CUSTOM_ERROR_IDENTIFIER, id);
        bankRepository.deleteById(id);

    }

    public List<Konto> findKonten(Long id) {
        Optional<Bank> bank = bankRepository.findById(id);
        if(bank.isPresent()) {
            return bank.get().getKontos();
        } else {
            return Arrays.asList();
        }
    }

}
