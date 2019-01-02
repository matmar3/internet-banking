package cz.zcu.kiv.pia.martinm.internetbanking.controller.dto;

import javax.validation.constraints.NotNull;

/**
 * Date: 29.12.2018
 *
 * @author Martin Matas
 */
public class AccountDto {

    @NotNull
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
