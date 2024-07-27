package org.example.service;

import org.example.dto.Bank;
import org.example.dto.Currency;

import java.io.IOException;
import java.util.Map;

public class CurrencyInfo {
    private int decimalPlaces;

    public CurrencyInfo(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public String getExchangeRates(Bank bank, Map<Currency, Double> currencies) {
        try {
            return switch (bank) {
                case NBU -> NBU.getExchangeRates(currencies, decimalPlaces);
                case PRIVATBANK -> PrivatBank.getExchangeRates(currencies, decimalPlaces);
                case MONOBANK -> Monobank.getExchangeRates(currencies, decimalPlaces);
            };
        } catch (IOException | InterruptedException e) {
            return e.getMessage();
        }
    }
}
