package com.example.bankexamplejavajpa.dao;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class KontoDAO {
    private Long id;
    private String isbn;
    private String comment;
    private LocalDate date;

}
