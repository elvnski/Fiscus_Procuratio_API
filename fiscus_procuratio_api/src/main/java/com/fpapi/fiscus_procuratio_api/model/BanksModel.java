package com.fpapi.fiscus_procuratio_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BanksModel {

    private String name;
    private String phone;
    private String email;
    private String address;

    private String newName;
    private String newPhone;
    private String newEmail;
    private String newAddress;




}
