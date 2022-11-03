package com.fpapi.fiscus_procuratio_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessesModel {

    private String name;
    private String businessCategory;
    private String phone;
    private String email;
    private String address;

    private String newName;
    private String newBusinessCategory;
    private String newPhone;
    private String newEmail;
    private String newAddress;



}
