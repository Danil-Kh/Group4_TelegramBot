package org.example.dto;

//public class CurrencyBank {

import java.util.Arrays;

public enum Currency {
        USD(840, "USD", "United States Dollar", false),
        EUR(978, "EUR", "Euro", false),
        UAH(980, "UAH", "Ukrainian Hryvnia", true);

        private final int currencyCode;
        private final String currencyCodeL;
        private final String currencyName;
        private final boolean isChosen;

        Currency(int currencyCode, String currencyCodeL, String currencyName, boolean isChosen) {
            this.currencyCode = currencyCode;
            this.currencyCodeL = currencyCodeL;
            this.currencyName = currencyName;
            this.isChosen = isChosen;
        }

        public int getCurrencyCode() {
            return currencyCode;
        }

        public String getCurrencyCodeL() {
            return currencyCodeL;
        }

        public String getCurrencyName() {
            return currencyName;
        }

        public boolean getChosen() { return isChosen; }

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
