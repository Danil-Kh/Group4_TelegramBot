package org.example.dto;

import lombok.Getter;
import java.net.URI;

    @Getter
    public enum Bank {
        NBU(URI.create("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchangenew?json")),
        PRIVATBANK(URI.create("https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5")),
        MONOBANK(URI.create("https://api.monobank.ua/bank/currency"));

        private final URI uri;

        Bank(URI uri) { this.uri = uri; }

    }

