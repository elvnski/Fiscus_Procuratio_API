package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.Banks;
import com.fpapi.fiscus_procuratio_api.entity.Businesses;
import com.fpapi.fiscus_procuratio_api.entity.Clients;
import com.fpapi.fiscus_procuratio_api.entity.Owners;
import com.fpapi.fiscus_procuratio_api.model.BanksModel;
import com.fpapi.fiscus_procuratio_api.model.BusinessesModel;
import com.fpapi.fiscus_procuratio_api.model.ClientsModel;
import com.fpapi.fiscus_procuratio_api.model.OwnersModel;

public interface EntitiesService {
    Banks addBankEntity(BanksModel banksModel);

    Banks alterBankEntityById(Long bankId, BanksModel banksModel);

    Businesses addBusinessEntity(BusinessesModel businessesModel);

    Businesses alterBusinessEntityById(Long businessId, BusinessesModel businessesModel);

    Clients addClientEntity(ClientsModel clientsModel);

    Clients alterClientEntityById(Long clientId, ClientsModel clientsModel);

    Owners addOwnerEntity(OwnersModel ownersModel);

}
