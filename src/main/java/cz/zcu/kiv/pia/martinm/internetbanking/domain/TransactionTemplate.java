package cz.zcu.kiv.pia.martinm.internetbanking.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
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

    @Id
    @Column(nullable = false, name = "ID")
    @ToString.Include
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private Integer id;

    @Column(nullable = false, name = "TemplateName")
    private String templateName;

    @Column(nullable = false, name = "ReceiverAccountNumber")
        private String receiverAccountNumber;

    @Column(nullable = false, name = "SentAmount")
        private BigDecimal sentAmount;

    @Column(nullable = false, name = "SenderAccountNumber")
    private String senderAccountNumber;

    @Column(name = "DueDate")
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
}
