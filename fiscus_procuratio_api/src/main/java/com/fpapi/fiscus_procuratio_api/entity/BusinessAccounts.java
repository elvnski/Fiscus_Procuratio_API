package com.fpapi.fiscus_procuratio_api.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BusinessAccounts {

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
    @JoinColumn(name = "businessId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_BIZ_BACC_businessId"))
    private Businesses business;

    @ManyToOne
    @JoinColumn(name = "bankId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_BAN_BUSA_bankId"))
    private Banks bank;

    @OneToMany(mappedBy = "debitedBusinessAccount")
    @ToString.Exclude
    private List<Cash> debitedCashList;

    @OneToMany(mappedBy = "businessAccount")
    @ToString.Exclude
    private List<AccountsPayable> accountsPayableList;

    @OneToMany(mappedBy = "businessAccount")
    @ToString.Exclude
    private List<AccountsPayablePayments> accountsPayablePaymentsList;


}