package com.example.bankexamplejavajpa.repository;

import com.example.bankexamplejavajpa.entities.Bank;
import com.example.bankexamplejavajpa.entities.Buchungssatz;
import com.example.bankexamplejavajpa.entities.Konto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Testcontainers
@ActiveProfiles("dev")
class BankRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:15.2-alpine");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private KontoRepository kontoRepository;

    @Test
    void testOnPostgresql() {
        assertThat(bankRepository.findAll()).hasSize(5);
    }

    @Test
    @Transactional
    void getKontenForBank() {
        Optional<Bank> bankOptional = bankRepository.findById(1L);
        Bank bank = bankOptional.get();
        List<Konto> konten = bank.getKontos();
        assertThat(konten).hasSize(4);
    }

    @Test
    @Transactional
    void getBuchungssaetzeForKonto() {
        Optional<Konto> kontoOptional = kontoRepository.findById(1L);
        Konto konto = kontoOptional.get();
        List<Buchungssatz> buchungssaetze = konto.getBuchungssaetze();
        assertThat(buchungssaetze).hasSize(10);
    }
}
