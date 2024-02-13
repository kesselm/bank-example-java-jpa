package com.example.bankexamplejavajpa.controller;

import com.example.bankexamplejavajpa.controller.doc.BankControllerDoc;
import com.example.bankexamplejavajpa.dao.BankDAO;
import com.example.bankexamplejavajpa.dao.KontoDAO;
import com.example.bankexamplejavajpa.entities.Bank;
import com.example.bankexamplejavajpa.entities.Konto;
import com.example.bankexamplejavajpa.service.interfaces.BankService;
import com.example.bankexamplejavajpa.util.EntityUtils;
import com.example.bankexamplejavajpa.util.WebConstants;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(WebConstants.BASE_URL)
@Slf4j
public class BankController implements BankControllerDoc {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }


    @PostMapping(WebConstants.SAVE_BANK)
    @ResponseStatus(HttpStatus.CREATED)
    public BankDAO saveBank(@Valid @RequestBody BankDAO bankDAO) {
        Bank bank = bankService.saveBank(EntityUtils.convertFromBankDAO(bankDAO));
        return EntityUtils.convertFromBank(bank);
    }


    @GetMapping(WebConstants.GET_ALL_BANKS)
    public ResponseEntity<List<BankDAO>> findAllBanks() {
        List<BankDAO> banks = bankService.findAllBanks()
                .stream().map(EntityUtils::convertFromBank)
                .toList();
        if (!banks.isEmpty()) {
            return new ResponseEntity<>(banks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(banks, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(WebConstants.GET_BANK_BY_ID)
    public ResponseEntity<BankDAO> findBankById(@PathVariable Long id) {
        Optional<Bank> bank = bankService.findBankById(id);

        if (bank.isPresent()) {
            return new ResponseEntity<>(EntityUtils.convertFromBank(bank.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping(WebConstants.DELETE_BANK)
    public void deleteBank(@RequestBody BankDAO bankDAO) {
        bankService.deleteBank(EntityUtils.convertFromBankDAO(bankDAO));
    }

    @DeleteMapping(WebConstants.DELETE_BANK_BY_ID)
    public void deleteBankById(@PathVariable Long id) {
        bankService.deleteBankById(id);
    }

    @PutMapping(WebConstants.UPDATE_BANK)
    public ResponseEntity<BankDAO> updateBank(@RequestBody BankDAO bankDAO) {
        Optional<Bank> bankOptional = bankService.findBankById(bankDAO.id());
        if (bankOptional.isPresent()) {
            var oldBank = Bank.builder()
                    .id(bankDAO.id())
                    .address(bankDAO.address())
                    .comment(bankDAO.comment())
                    .name(bankDAO.name())
                    .date(bankDAO.date())
                    .build();
            oldBank.setId(bankDAO.id());
            oldBank.setAddress(bankDAO.address());
            oldBank.setComment(bankDAO.comment());
            oldBank.setName(bankDAO.name());
            oldBank.setDate(bankDAO.date());
            Bank newBank = bankService.saveBank(oldBank);
            BankDAO newBankDAO = EntityUtils.convertFromBank(newBank);
            return new ResponseEntity<>(newBankDAO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(WebConstants.GET_KONTEN)
    public ResponseEntity<List<KontoDAO>> findKontenByBank(@PathVariable Long id) {
        List<Konto> konten = bankService.findKonten(id);
        if (!konten.isEmpty()) {
            List<KontoDAO> kontenDAO = konten.stream().map(EntityUtils::convertFromKonto)
                    .toList();
            return new ResponseEntity<>(kontenDAO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
