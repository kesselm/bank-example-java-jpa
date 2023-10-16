package com.example.bankexamplejavajpa.controller;

import com.example.bankexamplejavajpa.dao.BankDAO;
import com.example.bankexamplejavajpa.dao.KontoDAO;
import com.example.bankexamplejavajpa.entities.Konto;
import com.example.bankexamplejavajpa.exception.DummyException;
import com.example.bankexamplejavajpa.service.interfaces.KontoService;
import com.example.bankexamplejavajpa.util.DummyEntitiesBuilder;
import com.example.bankexamplejavajpa.util.EntityUtils;
import com.example.bankexamplejavajpa.util.TestUtil;
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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(KontoController.class)
class KontoControllerTest {

    @MockBean
    private KontoService kontoServiceMock;

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
    @DisplayName("A konto entity is persisted and response should be 'ok'.")
    void saveKontoTest() throws Exception {

        Konto konto = DummyEntitiesBuilder.getExampleKontoObject();
        when(kontoServiceMock.saveKonto(any())).thenReturn(konto);
        KontoDAO kontoDAO = EntityUtils.convertFromKonto(konto);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());

        mockMvc.perform(post(WebConstants.SAVE_KONTO)
                        .content(mapper.writeValueAsString(kontoDAO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        content().json("{'isbn':'DE 007'}")
                );
    }

    @Test
    @DisplayName("Response should be 'bad request'.")
    void saveKontoValidationTestForIsbnField() throws Exception {

        JSONObject kontoDAO = new JSONObject();
        kontoDAO.put("comment", "Testcomment 1");

        mockMvc.perform(post(WebConstants.SAVE_KONTO)
                        .content(kontoDAO.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Response should be a List of banks.")
    void getKontenTest() throws Exception {

        when(kontoServiceMock.getKontoList()).thenReturn(Arrays.asList(DummyEntitiesBuilder.getExampleKontoObject()));

        mockMvc.perform(get(WebConstants.GET_ALL_KONTEN))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        content().json("[{'isbn':'DE 007'}]")
                );
    }

    @Test
    @DisplayName("List ist empty and response should be 'no content'.")
    void getEmptyKontenList() throws Exception {
        when(kontoServiceMock.getKontoList()).thenReturn(new ArrayList<>());

        mockMvc.perform(get(WebConstants.GET_ALL_KONTEN))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Response should return bank entity for a given id.")
    void getKontoByIdTest() throws Exception{
        when(kontoServiceMock.getKontoById(any())).thenReturn(Optional.of(DummyEntitiesBuilder.getExampleKontoObject()));

        mockMvc.perform(get(WebConstants.GET_KONTO_BY_ID, 1))
                .andExpect(status().isOk())
                .andExpectAll(
                        content().json("{'isbn':'DE 007'}")
                );
    }

    @Test
    @DisplayName("Response should return 'no content'.")
    void getKontoByIdEmptyResultTest() throws Exception{
        when(kontoServiceMock.getKontoById(any())).thenReturn(Optional.empty());

        mockMvc.perform(get(WebConstants.GET_KONTO_BY_ID,1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Response should be 'no content'.")
    void deleteKontoTest() throws Exception{

        KontoDAO kontoDAO = EntityUtils.convertFromKonto(DummyEntitiesBuilder.getExampleKontoObject());

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());

        doNothing().when(kontoServiceMock).deleteKonto(any());

        mockMvc.perform(delete(WebConstants.DELETE_KONTO)
                .content(mapper.writeValueAsString(kontoDAO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Response should be 'ok'.")
    void updateKontoTest() throws Exception {
        Konto konto = DummyEntitiesBuilder.getExampleKontoObject();
        KontoDAO kontoDAO = EntityUtils.convertFromKonto(konto);

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());

        when(kontoServiceMock.getKontoById(any())).thenReturn(Optional.of(konto));

        mockMvc.perform(put(WebConstants.UPDATE_KONTO)
                        .content(mapper.writeValueAsString(kontoDAO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Response should be 'no content'.")
    void updateKontoWithEmptyObject() throws Exception {
        KontoDAO kontoDAO = EntityUtils.convertFromKonto(DummyEntitiesBuilder.getExampleKontoObject());

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());

        when(kontoServiceMock.getKontoById(any())).thenReturn(Optional.empty());

        mockMvc.perform(put(WebConstants.UPDATE_KONTO)
                        .content(mapper.writeValueAsString(kontoDAO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Response should be a bank.")
    void getBankFromKonto() throws Exception {

        when(kontoServiceMock.getBankFromKonto(any())).thenReturn(Optional.of(DummyEntitiesBuilder.getExampleBankObject()));

        mockMvc.perform(get(WebConstants.GET_BANK_FOR_KONTO, 1))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        content().json("{'name':'bank'}")
                );
    }

}
