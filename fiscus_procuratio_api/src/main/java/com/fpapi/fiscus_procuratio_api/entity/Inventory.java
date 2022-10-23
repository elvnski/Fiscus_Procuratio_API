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
public class Inventory {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    private String inventoryNumber;

    private Date date;
    private String itemName;

    /* Item Category ID */
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_IC_I_categoryId"))
    private InventoryCategory inventoryCategory;

    private BigDecimal currentQuantity;
    private BigDecimal reorderLevel;
    private BigDecimal stockingPrice;
    private BigDecimal sellingPrice;
    private BigDecimal allowedDiscountPercentage;
    private BigDecimal totalItemStockValue;

    @OneToMany(mappedBy = "inventory")
    private List<Sales> salesList;


}
