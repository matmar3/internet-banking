package cz.zcu.kiv.pia.martinm.internetbanking.controller.dto;

import javax.validation.constraints.NotNull;

/**
 * DTO used for mapping form output to {@link cz.zcu.kiv.pia.martinm.internetbanking.domain.Account} entity.
 *
 * Date: 29.12.2018
 *
 * @author Martin Matas
 */
public class AccountDto {

    /**
     * Account's currency field. Must be filled.
     */
    @NotNull
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
