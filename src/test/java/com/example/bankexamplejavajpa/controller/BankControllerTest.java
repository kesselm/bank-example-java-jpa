package com.example.bankexamplejavajpa.controller;

import com.example.bankexamplejavajpa.dao.BankDAO;
import com.example.bankexamplejavajpa.service.interfaces.BankService;
import com.example.bankexamplejavajpa.util.DummyEntitiesBuilder;
import com.example.bankexamplejavajpa.util.EntityUtils;
import com.example.bankexamplejavajpa.util.WebConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONObject;
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

    private final ObjectMapper mapper = new ObjectMapper();

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
        BankDAO bankDAO = EntityUtils.convertFromBank(DummyEntitiesBuilder.getExampleBankObject());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());

        mockMvc.perform(post(WebConstants.SAVE_BANK)
                .content(mapper.writeValueAsString(bankDAO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpectAll(
                        content().json("{'name':'bank'}")
                );
    }

    @Test
    @DisplayName("The field 'name' is missing and the response should be 'bad request'.")
    void saveBankValidationTestForNameField() throws Exception {

        var bankDAO = new JSONObject();
        bankDAO.put("id", "1");
        bankDAO.put("comment","Test Comment");

        mockMvc.perform(post(WebConstants.SAVE_BANK)
                        .content(bankDAO.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Response should be a List of banks and the status 'ok'.")
    void getBanksTest() throws Exception {
        when(bankServiceMock.findAllBanks()).thenReturn(Arrays.asList(DummyEntitiesBuilder.getExampleBankObject()));

        mockMvc.perform(get(WebConstants.GET_ALL_BANKS))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        content().json("[{'name':'bank'}]")
                );
    }

    @Test
    @DisplayName("Response should be an empty list and the status 'no content'.")
    void getEmptyBankListTest() throws Exception {
        when(bankServiceMock.findAllBanks()).thenReturn(Arrays.asList());

        mockMvc.perform(get(WebConstants.GET_ALL_BANKS))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    
    @Test
    @DisplayName("Response should return bank entity for a given id.")
    void getBankByIdTest() throws Exception{
        when(bankServiceMock.findBankById(any())).thenReturn(Optional.of(DummyEntitiesBuilder.getExampleBankObject()));

        mockMvc.perform(get(WebConstants.GET_BANK_BY_ID,1))
                .andExpect(status().isOk())
                .andExpectAll(
                        content().json("{'name':'bank'}")
                );
    }

    @Test
    @DisplayName("Response should return an empty result set entity for a given id.")
    void getBankByIdEmptyResultTest() throws Exception{
        when(bankServiceMock.findBankById(any())).thenReturn(Optional.empty());

        mockMvc.perform(get(WebConstants.GET_BANK_BY_ID,1))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Response should be 'no content'.")
    void deleteBankTest() throws Exception{
        BankDAO bankDAO = EntityUtils.convertFromBank(DummyEntitiesBuilder.getExampleBankObject());

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());

        doNothing().when(bankServiceMock).deleteBank(any());

        mockMvc.perform(delete(WebConstants.DELETE_BANK)
                .content(mapper.writeValueAsString(bankDAO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Response should be 'ok'.")
    void updateBankTest() throws Exception {
        BankDAO bankDAO = EntityUtils.convertFromBank(DummyEntitiesBuilder.getExampleBankObject());

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());

        when(bankServiceMock.findBankById(any())).thenReturn(Optional.of(DummyEntitiesBuilder.getExampleBankObject()));
        when(bankServiceMock.saveBank(any())).thenReturn(DummyEntitiesBuilder.getExampleBankObject());

        mockMvc.perform(put(WebConstants.UPDATE_BANK)
                        .content(mapper.writeValueAsString(bankDAO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Response should be 'no content'.")
    void updateBankWithEmptyObject() throws Exception {
        BankDAO bankDAO = EntityUtils.convertFromBank(DummyEntitiesBuilder.getExampleBankObject());

        when(bankServiceMock.findBankById(any())).thenReturn(Optional.empty());
        when(bankServiceMock.saveBank(any())).thenReturn(DummyEntitiesBuilder.getExampleBankObject());

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());

        mockMvc.perform(put(WebConstants.UPDATE_BANK)
                        .content(mapper.writeValueAsString(bankDAO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Response should be a list of konten.")
    void getKontoList() throws Exception {

        when(bankServiceMock.findKonten(any())).thenReturn(List.of(DummyEntitiesBuilder.getExampleKontoObject()));

        mockMvc.perform(get(WebConstants.GET_KONTEN,1))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        content().json("[{'date':'2023-10-15'}]")
                );
    }

}
