package com.fpapi.fiscus_procuratio_api.entity;

import lombok.*;

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
@Table(name = "cash_accounts")
public class CashAccounts {

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
    @JoinColumn(name = "bankId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_BAN_CA_bankId"))
    private Banks bank;

    private BigDecimal balance;

    @OneToMany(mappedBy = "debitedCashAccount")
    @ToString.Exclude
    private List<Cash> debitedCashList;

    @OneToMany(mappedBy = "creditedCashAccount")
    @ToString.Exclude
    private List<Cash> creditedCashList;

    @OneToMany(mappedBy = "cashAccount")
    @ToString.Exclude
    private List<DefaultCashAccounts> defaultCashAccountsList;



}