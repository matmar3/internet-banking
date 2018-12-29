package cz.zcu.kiv.pia.martinm.internetbanking.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
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

    public Transaction(BigDecimal receivedAmount, BigDecimal sentAmount, Date createdDate, Date dueDate,
                       String constantSymbol, String variableSymbol, String specificSymbol, String message,
                       Account sourceAccount, Account targetAccount) {
        this.receivedAmount = receivedAmount;
        this.sentAmount = sentAmount;
        this.createdDate = createdDate;
        this.dueDate = dueDate;
        this.constantSymbol = constantSymbol;
        this.variableSymbol = variableSymbol;
        this.specificSymbol = specificSymbol;
        this.message = message;
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
    }

    @Id
    @Column(nullable = false, name = "ID")
    @ToString.Include
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private Integer id;

    @Column(nullable = false, name = "ReceivedAmount")
        private BigDecimal receivedAmount;

    @Column(nullable = false, name = "SentAmount")
        private BigDecimal sentAmount;

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

    @ManyToOne
    private Account sourceAccount;

    @ManyToOne
    private Account targetAccount;

    @Override
    public Integer getId() {
        return null;
    }

    public BigDecimal getReceivedAmount() {
        return receivedAmount;
    }

    public BigDecimal getSentAmount() {
        return sentAmount;
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

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public Account getTargetAccount() {
        return targetAccount;
    }
}
