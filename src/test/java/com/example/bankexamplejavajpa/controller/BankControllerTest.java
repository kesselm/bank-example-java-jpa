package com.example.bankexamplejavajpa.controller;

import com.example.bankexamplejavajpa.dao.BankDAO;
import com.example.bankexamplejavajpa.entities.Bank;
import com.example.bankexamplejavajpa.exception.DummyException;
import com.example.bankexamplejavajpa.service.interfaces.BankService;
import com.example.bankexamplejavajpa.util.DummyEntitiesBuilder;
import com.example.bankexamplejavajpa.util.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BankController.class)
class BankControllerTest {

    @MockBean
    private BankService bankServiceMock;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();
    }

    @Test
    @DisplayName("A bank entity is persisted and response should be 'ok'.")
    void saveBankTest() throws Exception {

        when(bankServiceMock.saveBank(any())).thenReturn(DummyEntitiesBuilder.getExampleBankObject());

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());

        mockMvc.perform(post("/bank")
                .content(mapper.writeValueAsString(DummyEntitiesBuilder.getExampleBankObject()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        content().json("{'name':'bank'}")
                );
    }

    @Test
    @DisplayName("Response should be 'not found'.")
    void saveBankThrowException() throws Exception {
        when(bankServiceMock.saveBank(any())).thenThrow(new DummyException());

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());

        mockMvc.perform(post("/bank")
                        .content(mapper.writeValueAsString(DummyEntitiesBuilder.getExampleBankObject()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Response should be a List of banks.")
    void getBanksTest() throws Exception {
        when(bankServiceMock.getBankList()).thenReturn(Arrays.asList(DummyEntitiesBuilder.getExampleBankObject()));

        mockMvc.perform(get("/banks"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        content().json("[{'name':'bank'}]")
                );
    }

    @Test
    @DisplayName("List ist empty and response should be 'no content'.")
    void getBanksEmptyList() throws Exception {
        when(bankServiceMock.getBankList()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/banks"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Response should be 'internal error'.")
    void getBanksThrowsException() throws Exception {
        when(bankServiceMock.getBankList()).thenThrow(new DummyException());

        mockMvc.perform(get("/banks"))
                .andExpect(status().isInternalServerError());
    }
    
    @Test
    @DisplayName("Response should return bank entity for a given id.")
    void getBankByIdTest() throws Exception{
        when(bankServiceMock.getBankById(any())).thenReturn(Optional.of(DummyEntitiesBuilder.getExampleBankObject()));

        mockMvc.perform(get("/bank/1"))
                .andExpect(status().isOk())
                .andExpectAll(
                        content().json("{'name':'bank'}")
                );
    }

    @Test
    @DisplayName("Response should return 'no content'.")
    void getBankByIdEmptyResultTest() throws Exception{
        when(bankServiceMock.getBankById(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/bank/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Response should be 'no content'.")
    void deleteBankTest() throws Exception{

        BankDAO bankDAO = EntityUtils.convertFromBank(DummyEntitiesBuilder.getExampleBankObject());

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());

        doNothing().when(bankServiceMock).deleteBank(any());

        mockMvc.perform(delete("/bank")
                .content(mapper.writeValueAsString(bankDAO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Response should be 'internal server error'.")
    void deleteBankThrowException() throws Exception {
        BankDAO bankDAO = EntityUtils.convertFromBank(DummyEntitiesBuilder.getExampleBankObject());

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());

        doThrow(new DummyException()).when(bankServiceMock).deleteBank(any());

        mockMvc.perform(delete("/bank")
                        .content(mapper.writeValueAsString(bankDAO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Response should be 'ok'.")
    void updateBankTest() throws Exception {
        BankDAO bankDAO = EntityUtils.convertFromBank(DummyEntitiesBuilder.getExampleBankObject());

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());

        when(bankServiceMock.getBankById(any())).thenReturn(Optional.of(DummyEntitiesBuilder.getExampleBankObject()));

        mockMvc.perform(put("/bank")
                        .content(mapper.writeValueAsString(bankDAO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Response should be 'no content'.")
    void updateBankWithEmptyObject() throws Exception {
        BankDAO bankDAO = EntityUtils.convertFromBank(DummyEntitiesBuilder.getExampleBankObject());

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());

        when(bankServiceMock.getBankById(any())).thenReturn(Optional.empty());

        mockMvc.perform(put("/bank")
                        .content(mapper.writeValueAsString(bankDAO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Response should be 'internal server error'.")
    void updateBankThrowException() throws Exception {
        BankDAO bankDAO = EntityUtils.convertFromBank(DummyEntitiesBuilder.getExampleBankObject());

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());

        when(bankServiceMock.getBankById(any())).thenThrow(new DummyException());

        mockMvc.perform(put("/bank")
                        .content(mapper.writeValueAsString(bankDAO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Response should be a list of konten.")
    void getKontoList() throws Exception {

        when(bankServiceMock.getKontoList(any())).thenReturn(List.of(DummyEntitiesBuilder.getExampleKontoObject()));

        mockMvc.perform(get("/bank/1/konten"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        content().json("[{'date':'2023-10-15'}]")
                );
    }

    @Test
    @DisplayName("Response should be a list of konten.")
    void getKontoListThrowException() throws Exception {

        doThrow(new DummyException()).when(bankServiceMock).getKontoList(any());

        mockMvc.perform(get("/bank/1/konten"))
                .andExpect(status().isNotFound());
    }

}
