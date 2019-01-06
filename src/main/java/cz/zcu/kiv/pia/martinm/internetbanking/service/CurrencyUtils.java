package cz.zcu.kiv.pia.martinm.internetbanking.service;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

/**
 * Provides methods to work with currencies.
 *
 * Date: 29.12.2018
 *
 * @author Martin Matas
 */
public class CurrencyUtils {

    /**
     * Defines locale for formatting currency.
     */
    private static final Locale CZECH_LOCALE = new Locale("cs", "CZ");

    /**
     * Converts given amount from source currency to given target currency.
     *
     * @param amount - amount of money needed to convert
     * @param sourceCurrency - currency type of given amount
     * @param targetCurrency - wanted currency type
     * @return converted amount in target currency
     */
    public static BigDecimal convert(BigDecimal amount, Currency sourceCurrency, Currency targetCurrency) {
        if (sourceCurrency == targetCurrency) return amount;

        MonetaryAmount sourceAmount =
                Monetary.getDefaultAmountFactory()
                .setCurrency(sourceCurrency.getCurrencyCode())
                .setNumber(amount).create();

        CurrencyConversion targetCurrencyConversion =
                MonetaryConversions.getConversion(targetCurrency.getCurrencyCode());

        MonetaryAmount targetAmount = sourceAmount.with(targetCurrencyConversion);
        return new BigDecimal(targetAmount.getNumber().toString());
    }

    /**
     * Creates from given amount and currency formatted string. Format of formatted amount depends on
     * defined locale.
     *
     * @param amount - amount of money
     * @param currency - currency type of given amount
     * @return formatted amount with currency
     */
    public static String format(BigDecimal amount, String currency) {
        MonetaryAmount monetaryAmount =
                Monetary.getDefaultAmountFactory()
                        .setCurrency(currency)
                        .setNumber(amount).create();

        MonetaryAmountFormat amountFormat = MonetaryFormats.getAmountFormat(CZECH_LOCALE);
        return amountFormat.format(monetaryAmount);
    }
}
