package com.example.bankexamplejavajpa.controller;

import com.example.bankexamplejavajpa.dao.BankDAO;
import com.example.bankexamplejavajpa.dao.KontoDAO;
import com.example.bankexamplejavajpa.entities.Bank;
import com.example.bankexamplejavajpa.entities.Konto;
import com.example.bankexamplejavajpa.service.interfaces.BankService;
import com.example.bankexamplejavajpa.util.EntityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "Bank Application", description = "An Application to monitor your bank accounts.")
@RestController
@Slf4j
public class BankController {

    private BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @Operation(
            summary = "Create a new bank entity.",
            description = "Create a new bank entity."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BankDAO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema()) })
            })
    @PostMapping("/bank")
    public ResponseEntity<BankDAO> saveBank(BankDAO bankDAO) {
        try {
            Bank bank = bankService.saveBank(EntityUtils.convertFromBankDAO(bankDAO));
            BankDAO bankDao = EntityUtils.convertFromBank(bank);
            return new ResponseEntity<>(bankDao, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Get all bank accounts",
            description = "Get all bank accounts"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(
                    schema = @Schema(implementation = BankDAO.class)), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema()) })
    })
    @GetMapping("/banks")
    @ResponseBody
    public ResponseEntity<List<BankDAO>> getBanks() {
        try {
            List<BankDAO> banks = bankService.getBankList()
                    .stream().map(bank -> EntityUtils.convertFromBank(bank))
                    .collect(Collectors.toList());

            if (banks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(banks, HttpStatus.OK);
        } catch (Exception e) {
            log.info("Something went wrong. It could not be found any entities.");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get a bank account by identifier.",
            description = "Get a bank account by identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BankDAO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema()) })
    })
    @GetMapping("/bank/{id}")
    public ResponseEntity<BankDAO> getBankById(@PathVariable Long id) {
        Optional<Bank> bank = bankService.getBankById(id);

        if (bank.isPresent()) {
            return new ResponseEntity<>(EntityUtils.convertFromBank(bank.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Delete a bank account by identifier.",
            description = "Delete a bank account by identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BankDAO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema()) })
    })
    @DeleteMapping("/bank")
    public ResponseEntity<HttpStatus> deleteBank(@RequestBody BankDAO bankDAO) {
        try {
            bankService.deleteBank(EntityUtils.convertFromBankDAO(bankDAO));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Delete a bank account by identifier.",
            description = "Delete a bank account by identifier."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema(implementation = BankDAO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema()) })
    })
    @DeleteMapping("/bank/{id}")
    public ResponseEntity<HttpStatus> deleteBankById(@PathVariable Long id) {
        try {
            bankService.deleteBankById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Update a bank account.",
            description = "Update a bank account."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BankDAO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema()) })
    })
    @PutMapping("/bank")
    public ResponseEntity<BankDAO> updateBank(@RequestBody BankDAO bankDAO) {
        try {
            Optional<Bank> bankOptional = bankService.getBankById(bankDAO.getId());
            if (bankOptional.isPresent()) {
                Bank bank = new Bank();
                bank.setId(bankDAO.getId());
                bank.setAddress(bankDAO.getAddress());
                bank.setComment(bankDAO.getComment());
                bank.setName(bankDAO.getName());
                bank.setDate(bankDAO.getDate());
                BankDAO newBankDAO = EntityUtils.convertFromBank(bank);
                return new ResponseEntity<>(newBankDAO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Get konto entities related to the bank object.",
            description = "Get konto entites related to the bank object."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(
                    schema = @Schema(implementation = KontoDAO.class)), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema()) })
    })
    @GetMapping("/bank/{id}/konten")
    public ResponseEntity<List<KontoDAO>> getAllKonto(@PathVariable Long id){
        try {
            List<Konto> konten = bankService.getKontoList(id);
            List<KontoDAO> kontenDAO = konten.stream().map(konto -> EntityUtils.convertFromKonto(konto)).collect(Collectors.toList());
            return new ResponseEntity<>(kontenDAO, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
