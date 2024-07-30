package org.example.service;

import org.example.dto.Bank;
import org.example.dto.Currency;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class CurrencyInfo {

    public String getExchangeRates(Bank bank, ArrayList<Currency> arrayCurrency, int rounding){
        Map<Currency, Double> mapCurrency = arrayCurrency.stream()
                .collect(Collectors.toMap(currency -> currency, currency -> 0D));
        String result;
        try {
            result =  switch (bank) {
                case Bank.NBU -> NBU.getExchangeRates(mapCurrency, rounding);
                case Bank.PRIVATBANK -> PrivatBank.getExchangeRates(mapCurrency, rounding);
                case Bank.MONOBANK -> Monobank.getExchangeRates(mapCurrency, rounding);
            };
        } catch (IOException | InterruptedException e){
            return e.getMessage();
        }
        if (result.isBlank()) {
            return mapCurrency.entrySet().stream()
                    .map(entry -> entry.getKey().getCurrencyCodeL() + ": " + entry.getValue())
                    .collect(Collectors.joining("\n"));
        } else {
            return result;
        }
    }
}