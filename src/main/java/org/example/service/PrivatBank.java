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

public class PrivatBank {
    public static String getExchangeRates(Map<Currency, Double> currencyMap, int decimalPlaces) throws IOException, InterruptedException {
        String result = "";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(Bank.PRIVATBANK.getUri())
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        if (statusCode >= 200 && statusCode < 300) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type currencyListType = new TypeToken<List<CurrencyPrivat>>() {}.getType();
            List<CurrencyPrivat> currencyList = gson.fromJson(response.body(), currencyListType);
            if (currencyList != null) {
                currencyList.stream()
                        .filter(currencyPrivat -> currencyMap.containsKey(Currency.fromCodeL(currencyPrivat.getCcy())))
                        .forEach(currencyPrivat -> {
                            Currency currency = Currency.fromCodeL(currencyPrivat.getCcy());
                            Double roundedRate = round(currencyPrivat.getBuy(), decimalPlaces);
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

    private static class CurrencyPrivat {
        private String ccy;
        private String base_ccy;
        private double buy;
        private double sale;

        public String getCcy() {
            return ccy;
        }

        public double getBuy() {
            return buy;
        }
    }
}
