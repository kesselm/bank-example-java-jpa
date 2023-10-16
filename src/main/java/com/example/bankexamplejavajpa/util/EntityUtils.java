package com.example.bankexamplejavajpa.util;

import com.example.bankexamplejavajpa.dao.BankDAO;
import com.example.bankexamplejavajpa.dao.BuchungssatzDAO;
import com.example.bankexamplejavajpa.dao.KontoDAO;
import com.example.bankexamplejavajpa.entities.Bank;
import com.example.bankexamplejavajpa.entities.Buchungssatz;
import com.example.bankexamplejavajpa.entities.Konto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityUtils {

    public static Bank convertFromBankDAO(BankDAO bankDAO) {
        return Bank.builder()
                .id(bankDAO.id())
                .name(bankDAO.name())
                .address(bankDAO.address())
                .comment(bankDAO.comment())
                .date(bankDAO.date())
                .build();
    }

    public static BankDAO convertFromBank(Bank bank){
        return new BankDAO(bank.getId(), bank.getName(), bank.getAddress(), bank.getComment(), bank.getDate());
    }

    public static Konto convertFromKontoDAO(KontoDAO kontoDAO){
        Konto konto = new Konto();
        konto.setId(kontoDAO.id());
        konto.setIsbn(kontoDAO.isbn());
        konto.setComment(kontoDAO.comment());
        konto.setDate(kontoDAO.date());
        return konto;
    }

    public static KontoDAO convertFromKonto(Konto konto){
        return new KontoDAO(konto.getId(), konto.getIsbn(), konto.getComment(), konto.getDate());
    }

    public static BuchungssatzDAO convertFromBuchungssatz(Buchungssatz buchungssatz){
        BuchungssatzDAO buchungssatzDao = new BuchungssatzDAO();
        buchungssatzDao.setId(buchungssatz.getId());
        buchungssatzDao.setBuchungsart(buchungssatz.getBuchungsart());
        buchungssatzDao.setBuchungstext(buchungssatz.getBuchungstext());
        buchungssatzDao.setBetrag(buchungssatz.getBetrag());
        buchungssatzDao.setWaehrung(buchungssatz.getWaehrung());
        buchungssatzDao.setBuchungsdatum(buchungssatz.getBuchungsdatum());
        return buchungssatzDao;
    }

    public static Buchungssatz convertFromBuchungssatzDAO(BuchungssatzDAO buchungssatzDAO){
        Buchungssatz buchungssatz = new Buchungssatz();
        buchungssatz.setId(buchungssatzDAO.getId());
        buchungssatz.setBuchungsart(buchungssatzDAO.getBuchungsart());
        buchungssatz.setBuchungstext(buchungssatzDAO.getBuchungstext());
        buchungssatz.setBetrag(buchungssatzDAO.getBetrag());
        buchungssatz.setWaehrung(buchungssatzDAO.getWaehrung());
        buchungssatz.setBuchungsdatum(buchungssatzDAO.getBuchungsdatum());
        return buchungssatz;
    }

}
