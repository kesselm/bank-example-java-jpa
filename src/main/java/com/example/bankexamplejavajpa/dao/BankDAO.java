package com.example.bankexamplejavajpa.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Schema(name = "Bank")
public record BankDAO (
    Long id,
    @Schema(name = "name", example="Deutsche Bank", required = true)
    @NotBlank
    String name,
    @Schema(name = "address", example="Bahnhofstraße 47, 12305 Berlin")
    String address,
    @Schema(name = "comment", example="Eröffnet für das Familienkonto")
    String comment,
    @JsonFormat(pattern="yyyy-MM-dd")
    @Schema(name = "date", example="2023-10-14", required = true)
    LocalDate date) { }
