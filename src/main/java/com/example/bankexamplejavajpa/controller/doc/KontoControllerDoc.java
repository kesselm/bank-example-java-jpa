package com.example.bankexamplejavajpa.controller.doc;

import com.example.bankexamplejavajpa.dao.BankDAO;
import com.example.bankexamplejavajpa.dao.BuchungssatzDAO;
import com.example.bankexamplejavajpa.dao.KontoDAO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface KontoControllerDoc {

    @Operation(
            summary = "Create a new konto entity.",
            description = "Create a new konto entity.",
            tags = {"Konto"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = KontoDAO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    KontoDAO saveKonto(KontoDAO kontoDAO);

    @Operation(
            summary = "Get all Konten.",
            description = "Get all Konten.",
            tags = {"Konto"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(
                    schema = @Schema(implementation = KontoDAO.class)), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    })
    ResponseEntity<List<KontoDAO>> getKonten();

    @Operation(
            summary = "Get a konto by identifier.",
            description = "Get a konto by identifier.",
            tags = {"Konto"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = KontoDAO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    })
    ResponseEntity<KontoDAO> getKontoById(Long id);

    @Operation(
            summary = "Delete a konto by identifier.",
            description = "Delete a konto by identifier.",
            tags = {"Konto"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = KontoDAO.class), mediaType = "application/json")}),
    })
    void deleteKonto(KontoDAO kontoDAO);

    @Operation(
            summary = "Delete a konto by identifier.",
            description = "Delete a konto by identifier.",
            tags = {"Konto"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = KontoDAO.class), mediaType = "application/json")})
    })
    void deleteBankById(Long id);

    @Operation(
            summary = "Update a konto.",
            description = "Update a konto.",
            tags = {"Konto"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BankDAO.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    })
    ResponseEntity<KontoDAO> updateKonto(KontoDAO kontoDAO);

    @Operation(
            summary = "Get bank entity related to the konto object.",
            description = "Get bank entity related to the konto object.",
            tags = {"Konto"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(
                    schema = @Schema(implementation = KontoDAO.class)), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    })
    ResponseEntity<BankDAO> getBank(Long id);

    @Operation(
            summary = "Get buchungssatz entities related to the konto object.",
            description = "Get buchungssatz entities related to the konto object.",
            tags = {"Konto"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(
                    schema = @Schema(implementation = KontoDAO.class)), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    })
    ResponseEntity<List<BuchungssatzDAO>> getBuchungssaetze(Long id);
}
