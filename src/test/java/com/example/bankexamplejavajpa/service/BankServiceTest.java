package com.example.bankexamplejavajpa.service;

import com.example.bankexamplejavajpa.entities.Bank;
import com.example.bankexamplejavajpa.repository.BankRepository;
import com.example.bankexamplejavajpa.service.interfaces.BankService;
import com.example.bankexamplejavajpa.util.DummyEntitiesBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, OutputCaptureExtension.class})
class BankServiceTest {

    @MockBean
    BankRepository bankRepositoryMock;

    BankService sut;

    @BeforeEach
    void setUp() {
        sut = new BankServiceImpl(bankRepositoryMock);
    }

    @Test
    void saveBankTest() {
        when(bankRepositoryMock.save(any())).thenReturn(DummyEntitiesBuilder.getExampleBankObject());

        Bank result = sut.saveBank(DummyEntitiesBuilder.getExampleBankObject());

        assertThat(result.getName()).isEqualTo("bank");
    }

    @Test
    void saverUserLoggingMessageTest(CapturedOutput output) {
        when(bankRepositoryMock.save(any())).thenReturn(DummyEntitiesBuilder.getExampleBankObject());

        sut.saveBank(DummyEntitiesBuilder.getExampleBankObject());

        assertThat(output.getOut()).contains("New bank");
    }

    @Test
    void findAllTest() {
        when(bankRepositoryMock.findAll()).thenReturn(Arrays.asList(DummyEntitiesBuilder.getExampleBankObject()));

        List<Bank> result = sut.findAllBanks();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("bank");
    }

    @Test
    void deleteBankTest() {
        sut.deleteBank(DummyEntitiesBuilder.getExampleBankObject());
        verify(bankRepositoryMock).delete(any());
    }

    @Test
    void deleteUserTest(CapturedOutput output) {
        sut.deleteBank(DummyEntitiesBuilder.getExampleBankObject());
        assertThat(output.getOut()).contains("Bank with id:");
        verify(bankRepositoryMock).delete(any());
    }

    @Test
    void getBankByIdTest() {
        when(bankRepositoryMock.findById(any())).thenReturn(Optional.ofNullable(DummyEntitiesBuilder.getExampleBankObject()));
        assertThat(sut.findBankById(1L).get().getName()).isEqualTo("bank");
    }

    @Test
    void getKontoListForBank() {
        Optional<Bank> bankOptional = Optional.of(DummyEntitiesBuilder.getExampleBankObject());
        when(bankRepositoryMock.findById(any())).thenReturn(bankOptional);
        assertThat(sut.findKonten(1L)).hasSize(1);
    }

}
