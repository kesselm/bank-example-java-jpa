package com.example.bankexamplejavajpa.dao;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BankDAO {

    private Long id;
    @Schema(name = "name", example="Deutsche Bank", required=true)
    private String name;
    @Schema(name = "address", example="Bahnhofstraße 47, 12305 Berlin", required=true)
    private String address;
    @Schema(name = "comment", example="Eröffnet für das Familienkonto")
    private String comment;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Schema(name = "date", example="2023-10-14", required=true)
    private LocalDate date;
}
