package org.example.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.dto.Currency;
import org.example.dto.Bank;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class NBU {
    public static String getExchangeRates(Map<Currency, Double> currencyMap, int decimalPlaces) throws IOException, InterruptedException {
        String result = "";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(Bank.NBU.getUri())
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        if (statusCode >= 200 && statusCode < 300) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type currencyListType = new TypeToken<List<CurrencyNBU>>() {}.getType();
            List<CurrencyNBU> responseList = gson.fromJson(response.body(), currencyListType);
            if (responseList != null) {
                responseList.stream()
                        .filter(responseCurrency -> currencyMap.containsKey(Currency.fromCode(responseCurrency.getCod())))
                        .forEach(responseCurrency -> {
                            Currency currency = Currency.fromCode(responseCurrency.getCod());
                            Double roundedRate = round(responseCurrency.getRate(), decimalPlaces);
                            currencyMap.put(currency, roundedRate);
                        });
            } else {
                return "No currency data available";
            }
        } else {
            return "Failed to fetch exchange rates. HTTP status code: " + statusCode;
        }
        return result;
    }

    private static Double round(Double value, int decimalPlaces) {
        if (value == null) {
            return null;
        }
        return Math.round(value * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);
    }

    private static class CurrencyNBU {
        private int r030;
        private String txt;
        private double rate;
        private String cc;

        public int getCod() {
            return r030;
        }

        public double getRate() {
            return rate;
        }
    }
}
