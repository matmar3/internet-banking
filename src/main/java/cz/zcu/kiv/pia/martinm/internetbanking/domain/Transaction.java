package cz.zcu.kiv.pia.martinm.internetbanking.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Transaction entity.
 *
 * Date: 28.12.2018
 *
 * @author Martin Matas
 */
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Transaction")
public class Transaction implements DataTransferObject<Integer> {

    public Transaction() {

    }

    /**
     * Creates new transaction.
     *
     * @param receivedAmount - amount in receiver's currency
     * @param receiver - receiver's account
     * @param receiverAccountNumber - receiver's account number
     * @param sentAmount - amount in sender's currency
     * @param sender - sender's account
     * @param senderAccountNumber - sender's account number
     * @param createdDate - date of creation
     * @param dueDate - transaction's due date
     * @param constantSymbol - constant symbol
     * @param variableSymbol - variable symbol
     * @param specificSymbol - specific symbol
     * @param message - message
     */
    public Transaction(
            String receivedAmount, Account receiver, String receiverAccountNumber,
            String sentAmount, Account sender, String senderAccountNumber,
            Date createdDate, Date dueDate,
            String constantSymbol, String variableSymbol, String specificSymbol, String message) {
        this.receivedAmount = receivedAmount;
        this.receiver = receiver;
        this.receiverAccountNumber = receiverAccountNumber;
        this.sentAmount = sentAmount;
        this.sender = sender;
        this.senderAccountNumber = senderAccountNumber;
        this.createdDate = createdDate;
        this.dueDate = dueDate;
        this.constantSymbol = constantSymbol;
        this.variableSymbol = variableSymbol;
        this.specificSymbol = specificSymbol;
        this.message = message;
    }

    /**
     * Transaction's identifier.
     */
    @Id
    @Column(nullable = false, name = "ID")
    @ToString.Include
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private Integer id;

    /**
     * Received amount in receiver's currency.
     */
    @Column(nullable = false, name = "ReceivedAmount")
        private String receivedAmount;

    /**
     * Receiver's account.
     */
    @ManyToOne
    private Account receiver;

    /**
     * Receiver's account number.
     */
    @Column(nullable = false, name = "ReceiverAccountNumber")
    private String receiverAccountNumber;

    /**
     * Sent amount in sender's currency.
     */
    @Column(nullable = false, name = "SentAmount")
        private String sentAmount;

    /**
     * Sender's account.
     */
    @ManyToOne
    private Account sender;

    /**
     * Sender's account number
     */
    @Column(nullable = false, name = "SenderAccountNumber")
    private String senderAccountNumber;

    /**
     * Date of creation this transaction.
     */
    @Column(nullable = false, name = "CreatedDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private Date createdDate;

    /**
     * Due date
     */
    @Column(nullable = false, name = "DueDate")
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

    @Override
    public Integer getId() {
        return null;
    }

    public String getReceivedAmount() {
        return receivedAmount;
    }

    public Account getReceiver() {
        return receiver;
    }

    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public String getSentAmount() {
        return sentAmount;
    }

    public Account getSender() {
        return sender;
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public Date getCreatedDate() {
        return createdDate;
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
}
