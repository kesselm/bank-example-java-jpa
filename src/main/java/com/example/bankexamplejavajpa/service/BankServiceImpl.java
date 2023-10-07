package com.example.bankexamplejavajpa.service;

import com.example.bankexamplejavajpa.dao.BankDAO;
import com.example.bankexamplejavajpa.entities.Bank;
import com.example.bankexamplejavajpa.repository.BankRepository;
import com.example.bankexamplejavajpa.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankServiceImpl implements BankService {

    private BankRepository bankRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }


    @Override
    public BankDAO saveBank(BankDAO bankDAO) {
        Bank bank = bankRepository.save(EntityUtils.convertFromBankDAO(bankDAO));
        return EntityUtils.convertFromBank(bank);
    }

    @Override
    public List<BankDAO> getBankList() {
        return bankRepository.findAll().stream().map(bank -> EntityUtils.convertFromBank(bank))
                .collect(Collectors.toList());
    }

}
