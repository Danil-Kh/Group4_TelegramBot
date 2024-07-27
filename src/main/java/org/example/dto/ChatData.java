package org.example.dto;

import java.util.ArrayList;

class ChatData {
    protected ArrayList<Currency> currency;  /* масив що приймає елементи типу currency, по дефолту - UAN */
    protected int decimalPlaces;  //від 0 до 3, по дефолту - 2
    protected Bank bankName; //змінна типу Bank, NBU/PRIVATBANK/MONOBANK, по дефолту - NBU

    ChatData(ArrayList<Currency> currency, int decimalPlaces, Bank bankName) {
        this.currency = currency;
        this.decimalPlaces = decimalPlaces;
        this.bankName = bankName;
    }
}