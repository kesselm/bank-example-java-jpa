package com.example.bankexamplejavajpa.dao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Schema(name = "Konto")
public record KontoDAO (
    Long id,
    @Schema(name = "isbn", example="DE 007", required = true)
    @NotBlank
    String isbn,
    @Schema(name = "comment", example="Eröffnet für das Familienkonto")
    String comment,
    @Schema(name = "date", example="2023-10-14", required = true)
    LocalDate date) {

}
