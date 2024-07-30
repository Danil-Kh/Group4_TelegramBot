package org.example.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Setter;
import lombok.Getter;
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

public class Monobank {

    public static String getExchangeRates (Map<Currency, Double> currencyMap, int rounding) throws IOException, InterruptedException {
        String result = "";
        int codeUAH = Currency.UAH.getCurrencyCode();
        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(Bank.MONOBANK.getUri())
                    .timeout(Duration.ofMinutes(1))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }

        int statusCode = response.statusCode();
        if (statusCode >= 200 && statusCode < 300) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type currencyListType = new TypeToken<List<CurrencyMonobank>>() {
            }.getType();
            List<CurrencyMonobank> responseList = gson.fromJson(response.body(), currencyListType);
            if (responseList != null) {
                for (CurrencyMonobank currency : responseList) {
                    Currency currency1 = Currency.fromCode(currency.getCurrencyCodeA());
                    if (currencyMap.containsKey(currency1) && currency.currencyCodeB == codeUAH) {
                        Double roundedRate = Math.round(currency.getRateBuy() * Math.pow(10, rounding)) / Math.pow(10, rounding);
                        currencyMap.put(currency1, roundedRate);
                    }
                }

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
    private static class CurrencyMonobank {
        public int currencyCodeB;
        private int currencyCodeA;
        private Double rateBuy;
    }
}

