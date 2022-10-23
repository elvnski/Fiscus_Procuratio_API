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
public class InventoryCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    @OneToMany(mappedBy = "inventoryCategory")
    @ToString.Exclude
    private List<Inventory> inventoryList;

    @OneToMany(mappedBy = "inventoryCategory")
    @ToString.Exclude
    private List<InventoryPurchase> inventoryPurchaseList;



}
