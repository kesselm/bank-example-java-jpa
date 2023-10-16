package com.example.bankexamplejavajpa.service;

import com.example.bankexamplejavajpa.entities.Bank;
import com.example.bankexamplejavajpa.entities.Buchungssatz;
import com.example.bankexamplejavajpa.entities.Konto;
import com.example.bankexamplejavajpa.repository.KontoRepository;
import com.example.bankexamplejavajpa.service.interfaces.KontoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class KontoServiceImpl implements KontoService {

    private final KontoRepository kontoRepository;

    @Autowired
    public KontoServiceImpl(KontoRepository kontoRepository) {
        this.kontoRepository = kontoRepository;
    }

    @Override
    public Konto saveKonto(Konto konto) {
        return kontoRepository.save(konto);
    }

    @Override
    public List<Konto> getKontoList() {
        return kontoRepository.findAll();
    }

    @Override
    public Optional<Konto> getKontoById(Long id) {
        return kontoRepository.findById(id);
    }

    @Override
    public void deleteKonto(Konto konto) {
        kontoRepository.delete(konto);
        log.info("Konto object {} is deleted", konto.getId());
    }

    @Override
    public void deleteKontoById(Long id) {
        kontoRepository.deleteById(id);
        log.info("Konto object {} is deleted", id);
    }

    @Override
    public Optional<Bank> getBankFromKonto(Long id) {
        Optional<Konto> kontoOptional =  kontoRepository.findById(id);
        if(kontoOptional.isPresent()){
            return Optional.of(kontoOptional.get().getBank());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Buchungssatz> getBuchungsaetzeFromKonto(Long id) {
        Optional<Konto> kontoOptional =  kontoRepository.findById(id);
        if(kontoOptional.isPresent()){
            return kontoOptional.get().getBuchungssaetze();
        } else {
            return List.of();
        }
    }
}
