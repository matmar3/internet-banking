package cz.zcu.kiv.pia.martinm.internetbanking.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Transaction template entity.
 *
 * Date: 30.12.2018
 *
 * @author Martin Matas
 */
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TransactionTemplate")
public class TransactionTemplate implements DataTransferObject<Integer> {

    public TransactionTemplate() {

    }

    /**
     * Creates new transaction template.
     *
     * @param templateName - name of template
     * @param receiverAccountNumber - receiver's account number
     * @param sentAmount - amount in sender's currency
     * @param senderAccountNumber - sender's account number
     * @param dueDate - transaction's due date
     * @param constantSymbol - constant symbol
     * @param variableSymbol - variable symbol
     * @param specificSymbol - specific symbol
     * @param message - message
     */
    public TransactionTemplate(
            String templateName,
            String receiverAccountNumber, BigDecimal sentAmount, String senderAccountNumber,
            Date dueDate,
            String constantSymbol, String variableSymbol, String specificSymbol,
            String message, User owner) {
        this.templateName = templateName;
        this.receiverAccountNumber = receiverAccountNumber;
        this.sentAmount = sentAmount;
        this.senderAccountNumber = senderAccountNumber;
        this.dueDate = dueDate;
        this.constantSymbol = constantSymbol;
        this.variableSymbol = variableSymbol;
        this.specificSymbol = specificSymbol;
        this.message = message;
        this.owner = owner;
    }

    /**
     * Templates's identifier.
     */
    @Id
    @Column(nullable = false, name = "ID")
    @ToString.Include
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private Integer id;

    /**
     * Template's name
     */
    @Column(nullable = false, name = "TemplateName")
    private String templateName;

    /**
     * Receiver's account number.
     */
    @Column(nullable = false, name = "ReceiverAccountNumber")
        private String receiverAccountNumber;

    /**
     * Sent amount in sender's currency.
     */
    @Column(nullable = false, name = "SentAmount")
        private BigDecimal sentAmount;

    /**
     * Sender's account number
     */
    @Column(nullable = false, name = "SenderAccountNumber")
    private String senderAccountNumber;

    /**
     * Due date
     */
    @Column(name = "DueDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date dueDate;

    /**
     * Constant symbol
     */
    @Column(name = "ConstantSymbol")
    private String constantSymbol;

    /**
     * Variable symbol
     */
    @Column(name = "VariableSymbol")
    private String variableSymbol;

    /**
     * Specific symbol
     */
    @Column(name = "SpecificSymbol")
    private String specificSymbol;

    /**
     * Message
     */
    @Column(name = "Message")
    private String message;

    /**
     * Template's owner
     */
    @ManyToOne
        private User owner;

    @Override
    public Integer getId() {
        return id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public BigDecimal getSentAmount() {
        return sentAmount;
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getConstantSymbol() {
        return constantSymbol;
    }

    public String getVariableSymbol() {
        return variableSymbol;
    }

    public String getSpecificSymbol() {
        return specificSymbol;
    }

    public String getMessage() {
        return message;
    }

    public User getOwner() {
        return owner;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public void setSentAmount(BigDecimal sentAmount) {
        this.sentAmount = sentAmount;
    }

    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setConstantSymbol(String constantSymbol) {
        this.constantSymbol = constantSymbol;
    }

    public void setVariableSymbol(String variableSymbol) {
        this.variableSymbol = variableSymbol;
    }

    public void setSpecificSymbol(String specificSymbol) {
        this.specificSymbol = specificSymbol;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
