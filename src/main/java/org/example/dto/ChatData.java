package org.example.dto;

import org.example.dto.Bank;
import org.example.dto.Currency;

import java.util.ArrayList;

class ChatData {
    protected ArrayList<Currency> currency;  /* На вибір, хто як захоче(валюта як цифра чи валюта як слово)
        повинно бути передано до сеттеру у вигляді однієї строки, записано через кому,
        далі повинна йти перевірка на те які валюти вибрані для того щоб змінювати кнопки. */
    protected int decimalPlaces;  //0-3
    protected Bank bankName; //1 - НБУ, 2 - ПриватБанк, 3 - МоноБанк

    ChatData(ArrayList<Currency> currency, int decimalPlaces, Bank bankName) {
        this.currency = currency;
        this.decimalPlaces = decimalPlaces;
        this.bankName = bankName;
    }
}