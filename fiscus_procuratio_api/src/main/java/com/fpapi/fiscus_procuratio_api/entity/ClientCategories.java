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
public class ClientCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String category;
    private Long number;

    @OneToMany(mappedBy = "clientCategories")
    @ToString.Exclude
    private List<Clients> clientsList;


}
