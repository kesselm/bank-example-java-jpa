package com.example.bankexamplejavajpa.controller;

import com.example.bankexamplejavajpa.dao.BankDAO;
import com.example.bankexamplejavajpa.service.BankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BankController.class)
class BankControllerTest {

    @MockBean
    private BankService bankService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();
    }

    @Test
    void banksTest() throws Exception {
        BankDAO bank = new BankDAO();
        bank.setName("bank");
        bank.setAddress("address");
        bank.setComment("comment");
        bank.setDate(LocalDate.of(2023,10,7));

        when(bankService.getBankList()).thenReturn(Arrays.asList(bank));

        mockMvc.perform(get("/banks"))
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpectAll(
                                content().json("[{'name':'bank'}]")
                        );
    }



}
