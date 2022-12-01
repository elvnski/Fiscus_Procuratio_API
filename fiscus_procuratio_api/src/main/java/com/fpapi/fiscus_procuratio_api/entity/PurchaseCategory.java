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
public class PurchaseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String category;

    @OneToMany(mappedBy = "purchaseCategory")
    @ToString.Exclude
    private List<Purchases> purchasesList;

    @OneToMany(mappedBy = "purchaseCategory")
    @ToString.Exclude
    private List<CashInvoicesReceived> cashInvoicesReceivedList;




}
