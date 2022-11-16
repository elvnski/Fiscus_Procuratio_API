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
public class SaleCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    @OneToMany(mappedBy = "saleCategory")
    @ToString.Exclude
    private List<Sales> salesList;

    @OneToMany(mappedBy = "saleCategory")
    @ToString.Exclude
    private List<CashInvoicesIssued> cashInvoicesIssuedList;

}
