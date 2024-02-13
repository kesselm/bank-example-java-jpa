package com.example.bankexamplejavajpa.controller.doc;

import com.example.bankexamplejavajpa.dao.BankDAO;
import com.example.bankexamplejavajpa.dao.KontoDAO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BankControllerDoc {

    @Operation(
            summary = "Create a new bank entity.",
            description = "Create a new bank entity.",
            tags = {"Bank"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = BankDAO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    BankDAO saveBank(@Valid @RequestBody BankDAO bankDAO);

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
    ResponseEntity<List<BankDAO>> findAllBanks();

    @Operation(
            summary = "Get a bank account by identifier.",
            description = "Get a bank account by identifier.",
            tags = {"Bank"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BankDAO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    })
    ResponseEntity<BankDAO> findBankById(Long id);

    @Operation(
            summary = "Delete a bank account.",
            description = "Delete a bank account.",
            tags = {"Bank"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BankDAO.class), mediaType = "application/json")})
    })
    void deleteBank(BankDAO bankDAO);

    @Operation(
            summary = "Update a bank account.",
            description = "Update a bank account.",
            tags = {"Bank"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BankDAO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    })
    ResponseEntity<BankDAO> updateBank(BankDAO bankDAO);

    @Operation(
            summary = "Delete a bank account by identifier.",
            description = "Delete a bank account by identifier.",
            tags = {"Bank"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BankDAO.class), mediaType = "application/json")})
    })
    void deleteBankById(Long id);

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
    ResponseEntity<List<KontoDAO>> findKontenByBank(Long id);
}
