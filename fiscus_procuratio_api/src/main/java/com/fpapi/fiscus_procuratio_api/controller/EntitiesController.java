package com.fpapi.fiscus_procuratio_api.controller;

import com.fpapi.fiscus_procuratio_api.entity.Banks;
import com.fpapi.fiscus_procuratio_api.entity.Businesses;
import com.fpapi.fiscus_procuratio_api.entity.Clients;
import com.fpapi.fiscus_procuratio_api.entity.Owners;
import com.fpapi.fiscus_procuratio_api.model.BanksModel;
import com.fpapi.fiscus_procuratio_api.model.BusinessesModel;
import com.fpapi.fiscus_procuratio_api.model.ClientsModel;
import com.fpapi.fiscus_procuratio_api.model.OwnersModel;
import com.fpapi.fiscus_procuratio_api.service.EntitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EntitiesController {

    @Autowired
    private EntitiesService entitiesService;


    @PostMapping("/entities/owners/add")
    public String addOwnerEntity(@RequestBody OwnersModel ownersModel){

        Owners owner = entitiesService.addOwnerEntity(ownersModel);

        return "New Owner: '" + owner.getName() + "', Saved Successfully!";
    }





    /* SAVES A NEW BANK */
    @PostMapping("/entities/banks/add")
    public String addBankEntity(@RequestBody BanksModel banksModel) {

        Banks banks = entitiesService.addBankEntity(banksModel);
        String name =  banks.getName();

        return "New Bank: '" + name + "', Saved Successfully!";
    }

    /* ALTERS BANK */
    @PutMapping("/entities/banks/alter/{id}")
    public String alterBankEntity(@PathVariable("id") Long bankId, @RequestBody BanksModel banksModel) {

        Banks alteredBank = entitiesService.alterBankEntityById(bankId, banksModel);

        return "Bank details for bank ID '" + bankId + "' changed Successfully! " +
                "\nNew details are: "
                + "\nName: '" + alteredBank.getName() + "'"
                + "\nPhone: '" + alteredBank.getPhone() + "'"
                + "\nEmail: '" + alteredBank.getEmail() + "'"
                + "\nAddress: '" + alteredBank.getAddress() + "'";
    }


    /* SAVES A NEW BUSINESS */
    @PostMapping("/entities/businesses/add")
    public String addBusinessEntity(@RequestBody BusinessesModel businessesModel) {

        Businesses business = entitiesService.addBusinessEntity(businessesModel);
        String name = business.getName();

        return "New Business: '" + name + "', Saved Successfully!";
    }

    /* ALTERS BUSINESS */
    @PutMapping("/entities/businesses/alter/{id}")
    public String alterBusinessEntity(@PathVariable("id") Long businessId,  @RequestBody BusinessesModel businessesModel) {

        Businesses alteredBusiness = entitiesService.alterBusinessEntityById(businessId, businessesModel);

        return "Business details for business id '" + businessId + "' changed Successfully! " +
                "\nNew details are: "
                + "\nName: '" + alteredBusiness.getName() + "'"
                + "\nCategory: '" + alteredBusiness.getBusinessCategory() + "'"
                + "\nPhone: '" + alteredBusiness.getPhone() + "'"
                + "\nEmail: '" + alteredBusiness.getEmail() + "'"
                + "\nAddress: '" + alteredBusiness.getAddress() + "'";
    }


    /* SAVES A NEW CLIENT */
    @PostMapping("/entities/clients/add")
    public String addClientEntity(@RequestBody ClientsModel clientsModel) {

        Clients client = entitiesService.addClientEntity(clientsModel);
        String name = client.getName();

        return "New Client: '" + name + "', Saved Successfully!";
    }

    /* ALTERS CLIENT */
    @PutMapping("/entities/clients/alter/{id}")
    public String alterClientEntity(@PathVariable("id") Long clientId, @RequestBody ClientsModel clientsModel) {

        Clients client = entitiesService.alterClientEntityById(clientId, clientsModel);

        return "Client details for client ID '" + clientId + "' changed Successfully! " +
                "\nNew details are: "
                + "\nName: '" + client.getName() + "'"
                + "\nCategory: '" + client.getClientCategory() + "'"
                + "\nPhone: '" + client.getPhone() + "'"
                + "\nEmail: '" + client.getEmail() + "'"
                + "\nAddress: '" + client.getAddress() + "'";
    }





}
