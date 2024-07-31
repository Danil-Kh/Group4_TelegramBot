package org.example.dto;

import java.util.List;

public class ChatData {
    public List<Currency> currency;  /* масив що приймає елементи типу currency, по дефолту - EUR */
    public int decimalPlaces;  //від 0 до 3, по дефолту - 2
    public Bank bankName; //змінна типу Bank, NBU/PRIVATBANK/MONOBANK, по дефолту - NBU
    public int notificationTime;

     public ChatData(List<Currency> currency, int decimalPlaces, Bank bankName, int notificationTime) {
         this.currency = currency;
         this.decimalPlaces = decimalPlaces;
         this.bankName = bankName;
         this.notificationTime = notificationTime;
    }
}