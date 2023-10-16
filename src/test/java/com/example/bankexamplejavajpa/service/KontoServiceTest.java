package com.example.bankexamplejavajpa.service;

import com.example.bankexamplejavajpa.entities.Bank;
import com.example.bankexamplejavajpa.entities.Konto;
import com.example.bankexamplejavajpa.repository.KontoRepository;
import com.example.bankexamplejavajpa.service.interfaces.KontoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class KontoServiceTest {

    @MockBean
    KontoRepository kontoRepositoryMock;

    KontoService sut;

    @BeforeEach
    void setUp() {
        sut = new KontoServiceImpl(kontoRepositoryMock);
    }

    @Test
    void findAllTest() {
        when(kontoRepositoryMock.findAll()).thenReturn(Arrays.asList(getExampleKontoObject()));

        List<Konto> result = sut.getKontoList();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getDate()).isEqualTo("2023-10-07");
    }

    @Test
    void deleteKontoTest() {
        sut.deleteKonto(getExampleKontoObject());
        verify(kontoRepositoryMock).delete(any());
    }

    @Test
    void getKontoByIdTest() {
        when(kontoRepositoryMock.findById(any())).thenReturn(Optional.of(getExampleKontoObject()));

        assertThat(sut.getKontoById(1L).get().getDate()).isEqualTo("2023-10-07");
    }

    @Test
    void getKontoByIdThrowExceptionTest() {
        when(kontoRepositoryMock.findById(any())).thenThrow(EntityNotFoundException.class);

        assertThatThrownBy(() -> {
            sut.getKontoById(1L);
        }).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void getBankFromKontoTest() {
        when(kontoRepositoryMock.findById(any())).thenReturn(Optional.of(getExampleKontoObject()));
        Bank result = sut.getBankFromKonto(1L).orElse(new Bank());

        assertThat(result.getName()).isEqualTo("Deutsche Bank");
    }

    private Konto getExampleKontoObject(){
        Bank bank = new Bank();
        bank.setName("Deutsche Bank");

        Konto konto = new Konto();
        konto.setIsbn("007");
        konto.setDate(LocalDate.of(2023,10,7));
        konto.setBank(bank);
        return konto;
    }

}
