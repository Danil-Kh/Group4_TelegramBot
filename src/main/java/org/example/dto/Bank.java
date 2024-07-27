package org.example.dto;


import java.net.URI;


    public enum Bank {
        NBU("NBU", URI.create("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchangenew?json")),
        PRIVATBANK("PrivatBank", URI.create("https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5")),
        MONOBANK("Monobank", URI.create("https://api.monobank.ua/bank/currency"));

        private final String name;
        private final URI uri;

        Bank(String name, URI uri) {
            this.name = name;
            this.uri = uri;
        }

        public String getName() {
            return name;
        }

        public URI getUri() {
            return uri;
        }
    }

