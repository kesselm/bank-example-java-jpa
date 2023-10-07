package com.example.bankexamplejavajpa.service;

import com.example.bankexamplejavajpa.dao.BankDAO;
import java.util.List;

public interface BankService {

    public BankDAO saveBank(BankDAO bankDAO);

    public List<BankDAO> getBankList();
}
