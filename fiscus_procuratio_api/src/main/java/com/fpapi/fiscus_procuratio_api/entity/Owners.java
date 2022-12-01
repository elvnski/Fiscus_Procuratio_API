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
public class Owners {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private BigDecimal shareHolding;

    @OneToMany(mappedBy = "owner")
    @ToString.Exclude
    private List<OwnerAccounts> ownerAccountsList;

    @OneToMany(mappedBy = "owner")
    @ToString.Exclude
    private List<CapitalContributions> capitalContributionsList;

}
