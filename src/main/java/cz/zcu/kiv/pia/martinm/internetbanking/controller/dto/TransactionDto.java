package cz.zcu.kiv.pia.martinm.internetbanking.controller.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Date: 29.12.2018
 *
 * @author Martin Matas
 */
public class TransactionDto {

    @NotEmpty
    @Pattern(regexp = "^(([0-9]{6})-)?([0-9]{10})(/[0-9]{4})$", message = "Invalid account number format")
    private String receiverAccountNumber;

    @NotNull
    @Min(1)
    private BigDecimal sentAmount;

    @NotEmpty
    private String senderAccountNumber;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dueDate;

    @Size(max = 10)
    private String constantSymbol;

    @Size(max = 10)
    private String variableSymbol;

    @Size(max = 10)
    private String specificSymbol;

    @Size(max = 255)
    private String message;

    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public BigDecimal getSentAmount() {
        return sentAmount;
    }

    public void setSentAmount(BigDecimal sentAmount) {
        this.sentAmount = sentAmount;
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getConstantSymbol() {
        return constantSymbol;
    }

    public void setConstantSymbol(String constantSymbol) {
        this.constantSymbol = constantSymbol;
    }

    public String getVariableSymbol() {
        return variableSymbol;
    }

    public void setVariableSymbol(String variableSymbol) {
        this.variableSymbol = variableSymbol;
    }

    public String getSpecificSymbol() {
        return specificSymbol;
    }

    public void setSpecificSymbol(String specificSymbol) {
        this.specificSymbol = specificSymbol;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
