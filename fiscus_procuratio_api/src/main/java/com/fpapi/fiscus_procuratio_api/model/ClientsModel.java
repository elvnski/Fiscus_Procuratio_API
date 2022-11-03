package com.fpapi.fiscus_procuratio_api.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientsModel {

    private String name;
    private String clientCategory;
    private String phone;
    private String email;
    private String address;

    private String newName;
    private String newClientCategory;
    private String newPhone;
    private String newEmail;
    private String newAddress;



}
