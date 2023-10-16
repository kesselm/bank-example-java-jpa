package com.example.bankexamplejavajpa.util;

import com.example.bankexamplejavajpa.entities.Bank;
import com.example.bankexamplejavajpa.entities.Konto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DummyEntitiesBuilder {

    public static Bank getExampleBankObject(){
        Bank bank = new Bank();
        bank.setId(1L);
        bank.setName("bank");
        bank.setAddress("address");
        bank.setComment("comment");
        bank.setDate(LocalDate.of(2023,10,7));
        bank.setKontos(List.of(getExampleKontoObject()));
        return bank;
    }

    public static Konto getExampleKontoObject() {
        Konto konto = new Konto();
        konto.setDate(LocalDate.of(2023,10,15));
        konto.setIsbn("DE 007");
        konto.setComment("Gemeinsames Haushaltskonto");
        return konto;
    }

}
