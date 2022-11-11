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
public class Businesses {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    /* BIZ CATEGORY */
    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_BC_B_category"))
    private BusinessCategory businessCategory;

    private String phone;
    private String email;
    private String address;
    private BigDecimal noOfSales;

    @OneToMany(mappedBy = "businesses")
    @ToString.Exclude
    private List<Purchases> purchasesList;

    @OneToOne(mappedBy = "business", fetch = FetchType.EAGER)
    private InvoicesOwed invoicesOwed;


    @OneToMany(mappedBy = "business")
    @ToString.Exclude
    private List<AccountsPayable> accountsPayableList;


    @OneToMany(mappedBy = "business")
    @ToString.Exclude
    private List<InventoryPurchase> inventoryPurchaseList;

}
