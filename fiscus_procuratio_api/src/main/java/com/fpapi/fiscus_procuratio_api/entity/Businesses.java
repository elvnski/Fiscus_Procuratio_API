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
    private String noOfSales;

    @OneToMany(mappedBy = "businesses")
    private List<Purchases> purchasesList;



}
