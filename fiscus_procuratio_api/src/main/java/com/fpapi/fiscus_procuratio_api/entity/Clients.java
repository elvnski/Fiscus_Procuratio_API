package com.fpapi.fiscus_procuratio_api.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Clients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_CC_C_category"))
    private ClientCategory clientCategory;

    private String phone;
    private String email;
    private String address;
    private BigDecimal noOfSales;

    @OneToMany(mappedBy = "clients")
    @ToString.Exclude
    private List<Sales> salesList;


    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    private List<AccountsReceivable> accountsReceivableList;


    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    private List <InvoicesIssued> invoicesIssuedList;

    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    private List<CashInvoicesIssued> cashInvoicesIssuedList;


    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    private List<ClientAccounts> clientAccountsList;




}
