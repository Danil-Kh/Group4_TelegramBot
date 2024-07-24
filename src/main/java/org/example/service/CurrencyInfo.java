package org.example.service;

import org.example.dto.Bank;
import org.example.dto.Currency;

import java.io.IOException;
import java.util.Map;

public class CurrencyInfo {

    public String getExchangeRates(Bank bank, Map<Currency,Double> currencies, int rounding){
        try {
            return switch (bank) {
                case Bank.NBU -> NBU.getExchangeRates(currencies, rounding);
                case Bank.PRIVATBANK -> PrivatBank.getExchangeRates(currencies, rounding);
                case Bank.MONOBANK -> Monobank.getExchangeRates(currencies, rounding);
            };
        } catch (IOException | InterruptedException e){
            return e.getMessage();
        }
    }
}
