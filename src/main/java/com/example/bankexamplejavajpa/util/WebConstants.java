package com.example.bankexamplejavajpa.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebConstants {


    public static final String BASE_URL = "/api/v1/";
    public static final String SAVE_BANK = "/banken";
    public static final String GET_ALL_BANKS = "/banken";
    public static final String GET_BANK_BY_ID = "/banken/{id}";
    public static final String DELETE_BANK = "/banken";
    public static final String DELETE_BANK_BY_ID = "/banken/{id}";
    public static final String UPDATE_BANK = "/banken";
    public static final String GET_KONTEN = "/banken/{id}/konten";

    public static final String SAVE_KONTO = "/konten";
    public static final String GET_ALL_KONTEN = "/konten";
    public static final String GET_KONTO_BY_ID = "/konten/{id}";
    public static final String DELETE_KONTO = "/konten";
    public static final String DELETE_KONTO_BY_ID = "/konten/{id}";
    public static final String UPDATE_KONTO = "/konten";
    public static final String GET_BANK_FOR_KONTO = "/konten/{id}/bank";
    public static final String GET_BUCHUNGSSAETZE = "/konten/{id}/buchungen";

    public static final String CUSTOM_ERROR_IDENTIFIER = "Custom Error Message: ";
}
