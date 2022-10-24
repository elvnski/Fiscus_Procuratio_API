package com.fpapi.fiscus_procuratio_api.entity;

import lombok.*;

import javax.persistence.*;
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


    private String name;

    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_CC_category"))
    private ClientCategory clientCategory;

    private String phone;
    private String email;
    private String address;
    private String noOfSales;

    @OneToMany(mappedBy = "clients")
    @ToString.Exclude
    private List<Sales> salesList;


    @OneToMany(mappedBy = "clients")
    @ToString.Exclude
    private List<AccountsReceivable> accountsReceivableList;

    @OneToMany(mappedBy = "clients")
    @ToString.Exclude
    private List<CashReceipts> cashReceiptsList;

}
