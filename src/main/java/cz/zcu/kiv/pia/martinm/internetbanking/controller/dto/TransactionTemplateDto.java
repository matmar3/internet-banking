package cz.zcu.kiv.pia.martinm.internetbanking.controller.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO used for mapping form output
 * to {@link cz.zcu.kiv.pia.martinm.internetbanking.domain.TransactionTemplate} entity.
 *
 * Date: 01.01.2019
 *
 * @author Martin Matas
 */
public class TransactionTemplateDto {

    /**
     * Hidden field used for template identification. May not be part of the form.
     */
    private Integer id;

    /**
     * Template name field. Must be filled.
     */
    @NotEmpty
    private String templateName;

    /**
     * Receiver's account number field. Must be filled and must match with pattern.
     */
    @NotEmpty
    @Pattern(regexp = "^(([0-9]{6})-)?([0-9]{10})(/[0-9]{4})$", message = "Invalid account number format")
    private String receiverAccountNumber;

    /**
     * Sent amount field. Must be filled and the value must be positive nonzero number.
     */
    @NotNull
    @Min(1)
    private BigDecimal sentAmount;

    /**
     * Sender's account number field. Must be filled.
     */
    @NotEmpty
    private String senderAccountNumber;

    /**
     * field for due date. Can be empty.
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dueDate;

    /**
     * Constant symbol field. Can be empty, but when is filled the value can be max 10 characters long.
     */
    @Size(max = 10)
    private String constantSymbol;

    /**
     * Variable symbol field. Can be empty, but when is filled the value can be max 10 characters long.
     */
    @Size(max = 10)
    private String variableSymbol;

    /**
     * Specific symbol field. Can be empty, but when is filled the value can be max 10 characters long.
     */
    @Size(max = 10)
    private String specificSymbol;

    /**
     * Message for recipient. Can be empty, but when is filled the value can be max 255 characters.
     */
    @Size(max = 255)
    private String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

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
