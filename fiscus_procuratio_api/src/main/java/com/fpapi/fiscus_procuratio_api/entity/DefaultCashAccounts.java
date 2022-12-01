package com.fpapi.fiscus_procuratio_api.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class DefaultCashAccounts {

    @Id
    private String accountFunction;

    @ManyToOne
    @JoinColumn(name = "defaultAccount", referencedColumnName = "accountNumber", nullable = false, foreignKey = @ForeignKey(name = "FK_CACC_DCA_defaultAccount"))
    private CashAccounts cashAccount;

    private Date setDate;



}
