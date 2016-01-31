package com.cayman.util;

import com.cayman.entity.Currency;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;


public class CurrencyConverter {
    private static Properties property = new Properties();

    private CurrencyConverter(){
    }

    public static BigDecimal buyEuro(Currency senderCurrency, BigDecimal amount) {
        return amount.divide(new BigDecimal(getExchangeRate(senderCurrency)), 2, BigDecimal.ROUND_DOWN);
    }

    public static BigDecimal sellEuro(Currency recipientCurrency, BigDecimal amount){
        return amount.multiply(new BigDecimal(getExchangeRate(recipientCurrency)));
    }

    public static double getExchangeRate(Currency currency) {
        double result = 0;
        try (FileInputStream fis = new FileInputStream("src/main/resources/exchange_rates/rates.properties")){
            property.load(fis);
            String currentCurrency = currency.toString().toLowerCase();
            result = Double.parseDouble(property.getProperty(currentCurrency));
        } catch (IOException ignored){}
        return result;
    }
}