package org.example.dto;

import java.util.ArrayList;

class ChatData {
    protected ArrayList<Currency> currency;  /* масив що приймає елементи типу currency, по дефолту - EUR */
    protected int decimalPlaces;  //від 0 до 3, по дефолту - 2
    protected Bank bankName; //змінна типу Bank, NBU/PRIVATBANK/MONOBANK, по дефолту - NBU
    protected int notificationTime;

    ChatData(ArrayList<Currency> currency, int decimalPlaces, Bank bankName, int notificationTime) {
        this.currency = currency;
        this.decimalPlaces = decimalPlaces;
        this.bankName = bankName;
        this.notificationTime = notificationTime;
    }
}