package com.example.bankexamplejavajpa.controller;

import com.example.bankexamplejavajpa.dao.BankDAO;
import com.example.bankexamplejavajpa.service.BankService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BankController {

    private BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/banks")
    @ResponseBody
    public List<BankDAO> getBanks() {
        return bankService.getBankList();
    }

}
