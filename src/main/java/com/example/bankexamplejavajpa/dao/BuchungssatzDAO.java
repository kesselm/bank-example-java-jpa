package com.example.bankexamplejavajpa.dao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BuchungssatzDAO {
    private Long id;
    @Schema(name = "buchungsart", example="Lastschrift")
    private String buchungsart;
    @Schema(name = "buchungstext", example="NR XXXX 5041")
    private String buchungstext;
    @Schema(name = "auftraggeber", example="VISA Udemy", required = true)
    @NotBlank
    private String auftraggeber;
    @Schema(name = "betrag", example="15.99", required = true)
    @NotBlank
    private Double betrag;
    @Schema(name = "waehrung", example="EUR", required = true)
    @NotBlank
    private String waehrung;
    @Schema(name = "buchungsdatum", example="25.09.2023", required = true)
    @NotBlank
    private LocalDate buchungsdatum;
}
