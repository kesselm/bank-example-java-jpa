
package com.example.bankexamplejavajpa.config;

import com.example.bankexamplejavajpa.entities.Buchungssatz;
import org.springframework.boot.ApplicationRunner;
import com.example.bankexamplejavajpa.entities.Bank;
import com.example.bankexamplejavajpa.entities.Konto;
import com.example.bankexamplejavajpa.repository.BankRepository;
import com.example.bankexamplejavajpa.repository.BuchungssatzRepository;
import com.example.bankexamplejavajpa.repository.KontoRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("dev")
public class DataLoad implements ApplicationRunner {

    private BankRepository bankRepository;

    private KontoRepository kontoRepository;

    private BuchungssatzRepository buchungssatzRepository;

    public DataLoad(BankRepository bankRepository, KontoRepository kontoRepository, BuchungssatzRepository buchungssatzRepository) {
        this.bankRepository = bankRepository;
        this.kontoRepository = kontoRepository;
        this.buchungssatzRepository = buchungssatzRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Bank bank1 = new Bank();
        Bank bank2 = new Bank();
        Bank bank3 = new Bank();
        Bank bank4 = new Bank();
        Bank bank5 = new Bank();

        bank1.setName("Bank 1");
        bank2.setName("Bank 2");
        bank3.setName("Bank 3");
        bank4.setName("Bank 4");
        bank5.setName("Bank 5");

        bank1.setAddress("Addresse 1");
        bank2.setAddress("Addresse 2");
        bank3.setAddress("Addresse 3");
        bank4.setAddress("Addresse 4");
        bank5.setAddress("Addresse 5");

        bank1.setComment("Comment 1");
        bank2.setComment("Comment 2");
        bank3.setComment("Comment 3");
        bank4.setComment("Comment 4");
        bank5.setComment("Comment 5");

        bank1.setDate(LocalDate.now());
        bank2.setDate(LocalDate.of(2023, 9, 17));
        bank3.setDate(LocalDate.of(2021, 10, 18));
        bank4.setDate(LocalDate.of(2020, 11, 19));
        bank5.setDate(LocalDate.of(2020, 11, 20));

        Konto konto1 = new Konto();
        Konto konto2 = new Konto();
        Konto konto3 = new Konto();
        Konto konto4 = new Konto();

        konto1.setComment("Konto 1");
        konto2.setComment("Konto 2");
        konto3.setComment("Konto 3");
        konto4.setComment("Konto 4");

        buchungssatzRepository.saveAll(createListOfBuchungssatz());
        konto1.getBuchungssaetze().addAll(createListOfBuchungssatz());

        kontoRepository.save(konto1);
        kontoRepository.save(konto2);
        kontoRepository.save(konto3);
        kontoRepository.save(konto4);

        bank1.setKontos(List.of(konto1, konto2, konto3, konto4));
        bankRepository.saveAndFlush(bank1);
        bankRepository.saveAndFlush(bank2);
        bankRepository.saveAndFlush(bank3);
        bankRepository.saveAndFlush(bank4);
        bankRepository.saveAndFlush(bank5);

}

    private List<Buchungssatz> createListOfBuchungssatz() {
        List<Buchungssatz> buchungssatzList = new ArrayList<Buchungssatz>();
        for (int i=1; i <= 10; i++){
            Buchungssatz buchungssatz = new Buchungssatz();
            String id = i + "L";
            buchungssatz.setId(Long.getLong(id));
            buchungssatz.setBuchungstext("Testbuchung " + i);
            buchungssatz.setBuchungsdatum(LocalDate.of(2023, 10, i));
            String betrag = i+"."+0;
            buchungssatz.setBetrag(Double.valueOf(betrag));
            buchungssatz.setAuftraggeber("Auftraggeber " + i);
            buchungssatzList.add(buchungssatz);
        }
        return buchungssatzList;
    }
}
