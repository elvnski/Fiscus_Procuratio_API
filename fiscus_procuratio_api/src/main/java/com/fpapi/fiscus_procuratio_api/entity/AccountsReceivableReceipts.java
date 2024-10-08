package com.fpapi.fiscus_procuratio_api.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AccountsReceivableReceipts {

    @Id
    private String receiptNumber;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "accounts_receivable_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_AR_ARR_accountsReceivableId"))
    private AccountsReceivable accountsReceivable;

    private BigDecimal paymentReceived;

    @ManyToOne
    @JoinColumn(name = "clientAccountNumber", referencedColumnName = "accountNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_CACC_ARR_clientAccountNumber"))
    private ClientAccounts clientAccount;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_GL_ARR_transactionNumber"), name = "transactionNumber", referencedColumnName = "transactionNumber", nullable = false)
    private GeneralLedger generalLedger;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cashTransactionNumber", referencedColumnName = "cashTransactionNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_C_ARR_cashTransactionNumber"))
    private Cash cash;

}
