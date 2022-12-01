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
public class Banks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
    private String phone;
    private String email;
    private String address;
    private BigDecimal noOfLoans;

    @OneToMany(mappedBy = "bank")
    @ToString.Exclude
    private List<Loans> loansList;

    @OneToMany(mappedBy = "bank")
    @ToString.Exclude
    private List<CashAccounts> cashAccountsList;

    @OneToMany(mappedBy = "bank")
    @ToString.Exclude
    private List<ClientAccounts> clientAccountsList;

    @OneToMany(mappedBy = "bank")
    @ToString.Exclude
    private List<BusinessAccounts> businessAccountsList;

    @OneToMany(mappedBy = "bank")
    @ToString.Exclude
    private List<OwnerAccounts> ownerAccountsList;


}
