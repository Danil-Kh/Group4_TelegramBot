package org.example.dto;

import lombok.Getter;
import java.util.Arrays;

@Getter
public enum Currency {
    USD(840, "USD", "United States Dollar"),
    EUR(978, "EUR", "Euro"),
    UAH(980, "UAH", "Ukrainian Hryvnia");

    private final int currencyCode;
    private final String currencyCodeL;
    private final String currencyName;

    Currency(int currencyCode, String currencyCodeL, String currencyName) {
        this.currencyCode = currencyCode;
        this.currencyCodeL = currencyCodeL;
        this.currencyName = currencyName;
    }

    public static Currency fromCode(int code) {
        return Arrays.stream(Currency.values())
                .filter(currency -> currency.getCurrencyCode() == code)
                .findFirst()
                .orElse(null);
    }

    public static Currency fromCodeL(String code) {
        return Arrays.stream(Currency.values())
                .filter(currency -> currency.getCurrencyCodeL().equals(code))
                .findFirst()
                .orElse(null);
    }

}
