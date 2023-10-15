package com.example.bankexamplejavajpa.util;

import com.example.bankexamplejavajpa.dao.BankDAO;
import com.example.bankexamplejavajpa.dao.KontoDAO;
import com.example.bankexamplejavajpa.entities.Bank;
import com.example.bankexamplejavajpa.entities.Konto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityUtils {

    public static Bank convertFromBankDAO(BankDAO bankDAO) {
        Bank bank = new Bank();
        bank.setId(bankDAO.getId());
        bank.setName(bankDAO.getName());
        bank.setAddress(bankDAO.getAddress());
        bank.setComment(bank.getComment());
        bank.setDate(bank.getDate());
        return bank;
    }

    public static BankDAO convertFromBank(Bank bank){
        BankDAO bankDAO = new BankDAO();
        bankDAO.setId(bank.getId());
        bankDAO.setName(bank.getName());
        bankDAO.setAddress(bank.getAddress());
        bankDAO.setComment(bank.getComment());
        bankDAO.setDate(bank.getDate());
        return bankDAO;
    }

    public static Konto convertFromKontoDAO(KontoDAO kontoDAO){
        Konto konto = new Konto();
        konto.setIsbn(kontoDAO.getIsbn());
        konto.setComment(kontoDAO.getComment());
        konto.setDate(kontoDAO.getDate());
        return konto;
    }

    public static KontoDAO convertFromKonto(Konto konto){
        KontoDAO kontoDAO = new KontoDAO();
        kontoDAO.setIsbn(konto.getIsbn());
        kontoDAO.setComment(konto.getComment());
        kontoDAO.setDate(konto.getDate());
        return kontoDAO;
    }



}
