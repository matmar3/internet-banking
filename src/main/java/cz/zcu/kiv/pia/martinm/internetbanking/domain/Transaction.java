package cz.zcu.kiv.pia.martinm.internetbanking.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
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

    @Id
    @Column(nullable = false, name = "ID")
    @ToString.Include
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private Integer id;

    @Column(nullable = false, name = "ReceivedAmount")
        private String receivedAmount;

    @ManyToOne
    private Account receiver;

    @Column(nullable = false, name = "ReceiverAccountNumber")
    private String receiverAccountNumber;

    @Column(nullable = false, name = "SentAmount")
        private String sentAmount;

    @ManyToOne
    private Account sender;

    @Column(nullable = false, name = "SenderAccountNumber")
    private String senderAccountNumber;

    @Column(nullable = false, name = "CreatedDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private Date createdDate;

    @Column(nullable = false, name = "DueDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private Date dueDate;

    @Column(name = "ConstantSymbol")
    private String constantSymbol;

    @Column(name = "VariableSymbol")
    private String variableSymbol;

    @Column(name = "SpecificSymbol")
    private String specificSymbol;

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
