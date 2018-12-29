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
 * Date: 29.12.2018
 *
 * @author Martin Matas
 */
public class CurrencyUtils {

    private static final Locale CZECH_LOCALE = new Locale("cs", "CZ");

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

    public static String format(BigDecimal amount, String currency) {
        MonetaryAmount monetaryAmount =
                Monetary.getDefaultAmountFactory()
                        .setCurrency(currency)
                        .setNumber(amount).create();

        MonetaryAmountFormat amountFormat = MonetaryFormats.getAmountFormat(CZECH_LOCALE);
        return amountFormat.format(monetaryAmount);
    }
}
