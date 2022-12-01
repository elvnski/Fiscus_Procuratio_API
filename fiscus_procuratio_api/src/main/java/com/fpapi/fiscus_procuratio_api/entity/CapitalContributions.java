package com.fpapi.fiscus_procuratio_api.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CapitalContributions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ownerId", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_OWN_CC_ownerId"))
    private Owners owner;

    private BigDecimal contribution;

    private Date date;

}
