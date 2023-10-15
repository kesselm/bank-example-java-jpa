package com.example.bankexamplejavajpa.service;

import com.example.bankexamplejavajpa.entities.Bank;
import com.example.bankexamplejavajpa.repository.BankRepository;
import com.example.bankexamplejavajpa.service.interfaces.BankService;
import com.example.bankexamplejavajpa.util.DummyEntitiesBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class BankServiceTest {

    @MockBean
    BankRepository bankRepositoryMock;

    BankService sut;

    @BeforeEach
    void setUp() {
        sut = new BankServiceImpl(bankRepositoryMock);
    }

    @Test
    void findAllTest() {
        when(bankRepositoryMock.findAll()).thenReturn(Arrays.asList(DummyEntitiesBuilder.getExampleBankObject()));

        List<Bank> result = sut.getBankList();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("bank");
    }

    @Test
    void deleteBankTest() throws Exception {
        sut.deleteBank(DummyEntitiesBuilder.getExampleBankObject());
        verify(bankRepositoryMock).delete(any());
    }

    @Test
    void getBankByIdTest() throws Exception {
        when(bankRepositoryMock.findById(any())).thenReturn(Optional.ofNullable(DummyEntitiesBuilder.getExampleBankObject()));
        assertThat(sut.getBankById(1L).get().getName()).isEqualTo("bank");
    }

    @Test
    void getBankByIdThrowExceptionTest() throws Exception {
        when(bankRepositoryMock.findById(any())).thenThrow(EntityNotFoundException.class);
        assertThatThrownBy(() -> {
            sut.getBankById(1L);
        }).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getKontoListForBank() throws Exception {
        when(bankRepositoryMock.findById(any())).thenReturn(Optional.of(DummyEntitiesBuilder.getExampleBankObject()));
        assertThat(sut.getKontoList(1L)).hasSize(1);
    }

}
