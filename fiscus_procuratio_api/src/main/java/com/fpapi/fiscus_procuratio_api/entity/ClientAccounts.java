package com.fpapi.fiscus_procuratio_api.entity;

import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ClientAccounts {

    @Id
    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private Date dateAdded;

    private Date dateActivated;

    private Date dateDeactivated;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false, unique = true)
    private String accountName;

    @ManyToOne
    @JoinColumn(name = "clientId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_CLI_CLIA_clientId"))
    private Clients client;

    @ManyToOne
    @JoinColumn(name = "bankId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_BAN_CLIA_bankId"))
    private Banks bank;

    @OneToMany(mappedBy = "creditedClientAccount")
    @ToString.Exclude
    private List<Cash> creditedCashList;

    @OneToMany(mappedBy = "clientAccount")
    @ToString.Exclude
    private List<AccountsReceivable> accountsReceivableList;

    @OneToMany(mappedBy = "clientAccount")
    @ToString.Exclude
    private List<AccountsReceivableReceipts> accountsReceivableReceiptsList;



}