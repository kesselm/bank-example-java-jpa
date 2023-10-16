package com.example.bankexamplejavajpa.service;

import com.example.bankexamplejavajpa.entities.Buchungssatz;
import com.example.bankexamplejavajpa.repository.BuchungssatzRepository;
import com.example.bankexamplejavajpa.service.interfaces.BuchungsatzService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class BuchungssatzServiceImpl implements BuchungsatzService {

    private BuchungssatzRepository buchungssatzRepository;

    public BuchungssatzServiceImpl(BuchungssatzRepository buchungssatzRepository){
        this.buchungssatzRepository = buchungssatzRepository;
    }

    @Override
    public Buchungssatz saveBuchungssatz(Buchungssatz buchungssatz) {
        return buchungssatzRepository.save(buchungssatz);
    }

    @Override
    public List<Buchungssatz> getBuchungssatzList() {
        return buchungssatzRepository.findAll();
    }

    @Override
    public Optional<Buchungssatz> getBuchungssatzById(Long id) {
        return buchungssatzRepository.findById(id);
    }

    @Override
    public void deleteBuchungssatz(Buchungssatz buchungssatzDAO) {

    }

    @Override
    public void deleteBuchungssatzById(Long id) {

    }
}
