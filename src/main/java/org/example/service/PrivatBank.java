package org.example.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;
import org.example.dto.Bank;
import org.example.dto.Currency;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class PrivatBank {
    public static String getExchangeRates (Map<Currency, Double> currencyMap, int rounding) throws IOException, InterruptedException {
        String result = "";
        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(Bank.PRIVATBANK.getUri())
                    .timeout(Duration.ofMinutes(1))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }

        int statusCode = response.statusCode();
        if (statusCode >= 200 && statusCode < 300) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type currencyListType = new TypeToken<List<CurrencyPrivat>>() {
            }.getType();
            List<CurrencyPrivat> currencyList = gson.fromJson(response.body(), currencyListType);
            if (currencyList != null) {
                    currencyList.stream()
                            .filter(currencyPrivat -> currencyMap.containsKey(Currency.fromCodeL(currencyPrivat.getCcy())))
                            .forEach(currencyPrivat -> {
                               Currency currency = Currency.fromCodeL(currencyPrivat.getCcy());
                                Double roundedRate = Math.round(currencyPrivat.getBuy() * Math.pow(10, rounding)) / Math.pow(10, rounding);
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

    @Setter
    @Getter
    private static class CurrencyPrivat {
        private String ccy;
        private double buy;

    }
}
