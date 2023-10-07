package com.example.bankexamplejavajpa.service;

import com.example.bankexamplejavajpa.dao.BankDAO;
import com.example.bankexamplejavajpa.entities.Bank;
import com.example.bankexamplejavajpa.repository.BankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class BankServerTest {

    @MockBean
    BankRepository bankRepository;

    BankService sut;

    @BeforeEach
    void setUp() {
        sut = new BankServiceImpl(bankRepository);
    }

    @Test
    void findAllTest() {
        Bank bank = new Bank();
        bank.setName("bank");
        bank.setAddress("address");
        bank.setComment("comment");
        bank.setDate(LocalDate.of(2023,10,07));

        when(bankRepository.findAll()).thenReturn(Arrays.asList(bank));

        List<BankDAO> result = sut.getBankList();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("bank");
    }

}
