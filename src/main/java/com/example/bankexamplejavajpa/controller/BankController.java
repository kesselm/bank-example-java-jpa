package com.example.bankexamplejavajpa.controller;

import com.example.bankexamplejavajpa.dao.BankDAO;
import com.example.bankexamplejavajpa.dao.KontoDAO;
import com.example.bankexamplejavajpa.entities.Bank;
import com.example.bankexamplejavajpa.entities.Konto;
import com.example.bankexamplejavajpa.service.interfaces.BankService;
import com.example.bankexamplejavajpa.util.EntityUtils;
import com.example.bankexamplejavajpa.util.WebConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Slf4j
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @Operation(
            summary = "Create a new bank entity.",
            description = "Create a new bank entity.",
            tags = {"Bank"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = BankDAO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @PostMapping(WebConstants.SAVE_BANK)
    @ResponseStatus(HttpStatus.CREATED)
    public BankDAO saveBank(@Valid @RequestBody BankDAO bankDAO) {
        Bank bank = bankService.saveBank(EntityUtils.convertFromBankDAO(bankDAO));
        return EntityUtils.convertFromBank(bank);
    }

    @Operation(
            summary = "Get all bank accounts",
            description = "Get all bank accounts",
            tags = {"Bank"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(
                    schema = @Schema(implementation = BankDAO.class)), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    })
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

    @Operation(
            summary = "Get a bank account by identifier.",
            description = "Get a bank account by identifier.",
            tags = {"Bank"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BankDAO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    })
    @GetMapping(WebConstants.GET_BANK_BY_ID)
    public ResponseEntity<BankDAO> findBankById(@PathVariable Long id) {
        Optional<Bank> bank = bankService.findBankById(id);

        if (bank.isPresent()) {
            return new ResponseEntity<>(EntityUtils.convertFromBank(bank.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @Operation(
            summary = "Delete a bank account.",
            description = "Delete a bank account.",
            tags = {"Bank"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BankDAO.class), mediaType = "application/json")})
    })
    @DeleteMapping(WebConstants.DELETE_BANK)
    public void deleteBank(@RequestBody BankDAO bankDAO) {
        bankService.deleteBank(EntityUtils.convertFromBankDAO(bankDAO));
    }

    @Operation(
            summary = "Delete a bank account by identifier.",
            description = "Delete a bank account by identifier.",
            tags = {"Bank"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BankDAO.class), mediaType = "application/json")})
    })
    @DeleteMapping(WebConstants.DELETE_BANK_BY_ID)
    public void deleteBankById(@PathVariable Long id) {
        bankService.deleteBankById(id);
    }

    @Operation(
            summary = "Update a bank account.",
            description = "Update a bank account.",
            tags = {"Bank"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BankDAO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    })
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

    @Operation(
            summary = "Get konto entities related to the bank object.",
            description = "Get konto entites related to the bank object.",
            tags = {"Bank"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(
                    schema = @Schema(implementation = KontoDAO.class)), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    })
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
