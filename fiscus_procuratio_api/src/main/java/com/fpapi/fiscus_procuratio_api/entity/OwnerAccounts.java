package com.fpapi.fiscus_procuratio_api.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OwnerAccounts {

    @Id
    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private Date dateAdded;

    private Date dateActivated;

    private Date dateDeactivated;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false, unique = true)
    private String accountName;

    @ManyToOne
    @JoinColumn(name = "bankId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_BAN_OWACC_bankId"))
    private Banks bank;

    @ManyToOne
    @JoinColumn(name = "ownerId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_OW_OWACC_ownerId"))
    private Owners owner;



    @OneToMany(mappedBy = "debitedOwnerAccount")
    @ToString.Exclude
    private List<Cash> debitedOwnerAccountList;

    @OneToMany(mappedBy = "creditedOwnerAccount")
    @ToString.Exclude
    private List<Cash> creditedOwnerAccountList;



}