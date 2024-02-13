package com.example.bankexamplejavajpa.controller;

import com.example.bankexamplejavajpa.controller.doc.KontoControllerDoc;
import com.example.bankexamplejavajpa.dao.BankDAO;
import com.example.bankexamplejavajpa.dao.BuchungssatzDAO;
import com.example.bankexamplejavajpa.dao.KontoDAO;
import com.example.bankexamplejavajpa.entities.Bank;
import com.example.bankexamplejavajpa.entities.Konto;
import com.example.bankexamplejavajpa.service.interfaces.KontoService;
import com.example.bankexamplejavajpa.util.EntityUtils;
import com.example.bankexamplejavajpa.util.WebConstants;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(WebConstants.BASE_URL)
@Slf4j
public class KontoController implements KontoControllerDoc {

    private final KontoService kontoService;

    public KontoController(KontoService kontoService) {
        this.kontoService = kontoService;
    }

    @PostMapping(WebConstants.SAVE_KONTO)
    public KontoDAO saveKonto(@Valid @RequestBody KontoDAO kontoDAO) {
        Konto konto = kontoService.saveKonto(EntityUtils.convertFromKontoDAO(kontoDAO));
        return EntityUtils.convertFromKonto(konto);
    }

    @GetMapping(WebConstants.GET_ALL_KONTEN)
    @ResponseBody
    public ResponseEntity<List<KontoDAO>> getKonten() {
        List<KontoDAO> konten = kontoService.getKontoList()
                .stream().map(EntityUtils::convertFromKonto)
                .toList();
        if (!konten.isEmpty()) {
            return new ResponseEntity<>(konten, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(konten, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(WebConstants.GET_KONTO_BY_ID)
    public ResponseEntity<KontoDAO> getKontoById(@PathVariable Long id) {
        Optional<Konto> konto = kontoService.getKontoById(id);

        if (konto.isPresent()) {
            return new ResponseEntity<>(EntityUtils.convertFromKonto(konto.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping(WebConstants.DELETE_KONTO)
    public void deleteKonto(@RequestBody KontoDAO kontoDAO) {
        kontoService.deleteKonto(EntityUtils.convertFromKontoDAO(kontoDAO));
    }

    @DeleteMapping(WebConstants.DELETE_KONTO_BY_ID)
    public void deleteBankById(@PathVariable Long id) {
        kontoService.deleteKontoById(id);
    }

    @PutMapping(WebConstants.UPDATE_KONTO)
    public ResponseEntity<KontoDAO> updateKonto(@RequestBody KontoDAO kontoDAO) {
        Optional<Konto> kontoOptional = kontoService.getKontoById(kontoDAO.id());
        if (kontoOptional.isPresent()) {
            Konto konto = new Konto();
            konto.setId(kontoDAO.id());
            konto.setIsbn(kontoDAO.isbn());
            konto.setComment(kontoDAO.comment());
            konto.setDate(kontoDAO.date());
            KontoDAO newKontoDAO = EntityUtils.convertFromKonto(konto);
            return new ResponseEntity<>(newKontoDAO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(WebConstants.GET_BANK_FOR_KONTO)
    public ResponseEntity<BankDAO> getBank(@PathVariable Long id) {
        Optional<Bank> bankOptional = kontoService.getBankFromKonto(id);
        if (bankOptional.isPresent()) {
            return new ResponseEntity<>(EntityUtils.convertFromBank(bankOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(WebConstants.GET_BUCHUNGSSAETZE)
    public ResponseEntity<List<BuchungssatzDAO>> getBuchungssaetze(@PathVariable Long id) {
        List<BuchungssatzDAO> buchungssatz = kontoService.getBuchungsaetzeFromKonto(id)
                .stream().map(buchungen -> EntityUtils.convertFromBuchungssatz(buchungen)).toList();
        if (!buchungssatz.isEmpty()) {
            return new ResponseEntity<>(buchungssatz, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
