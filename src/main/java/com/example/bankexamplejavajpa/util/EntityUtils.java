package com.example.bankexamplejavajpa.util;

import com.example.bankexamplejavajpa.dao.BankDAO;
import com.example.bankexamplejavajpa.entities.Bank;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityUtils {

    public static Bank convertFromBankDAO(BankDAO bankDAO) {
        Bank bank = new Bank();
        bank.setName(bankDAO.getName());
        bank.setAddress(bankDAO.getAddress());
        bank.setComment(bank.getComment());
        bank.setDate(bank.getDate());
        return bank;
    }

    public static BankDAO convertFromBank(Bank bank){
        BankDAO bankDAO = new BankDAO();
        bankDAO.setName(bank.getName());
        bankDAO.setAddress(bank.getAddress());
        bankDAO.setComment(bank.getComment());
        return bankDAO;
    }

}
