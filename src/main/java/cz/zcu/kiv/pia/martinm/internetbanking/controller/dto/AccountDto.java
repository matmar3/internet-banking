package cz.zcu.kiv.pia.martinm.internetbanking.controller.dto;

import javax.validation.constraints.NotNull;
import java.util.Currency;

/**
 * Date: 29.12.2018
 *
 * @author Martin Matas
 */
public class AccountDto {

    @NotNull
    private Currency currency;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

}
