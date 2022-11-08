package com.fpapi.fiscus_procuratio_api.service;

import com.fpapi.fiscus_procuratio_api.entity.Banks;
import com.fpapi.fiscus_procuratio_api.entity.Businesses;
import com.fpapi.fiscus_procuratio_api.entity.Clients;
import com.fpapi.fiscus_procuratio_api.model.BanksModel;
import com.fpapi.fiscus_procuratio_api.model.BusinessesModel;
import com.fpapi.fiscus_procuratio_api.model.ClientsModel;
import com.fpapi.fiscus_procuratio_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class EntitiesServiceImpl implements EntitiesService{

    @Autowired
    private BanksRepository banksRepository;

    @Autowired
    private BusinessesRepository businessesRepository;

    @Autowired
    private BusinessCategoryRepository businessCategoryRepository;

    @Autowired
    private ClientsRepository clientsRepository;

    @Autowired
    private ClientCategoryRepository clientCategoryRepository;

    @Override
    public Banks addBankEntity(BanksModel banksModel) {

        Banks banks = new Banks();
        banks.setName(banksModel.getName());
        banks.setPhone(banksModel.getPhone());
        banks.setEmail(banksModel.getEmail());
        banks.setAddress(banksModel.getAddress());
        banks.setNoOfLoans(BigDecimal.valueOf(0.0));

        banksRepository.save(banks);

        return banks;
    }


    @Override
    public Banks alterBankEntityById(Long bankId, BanksModel banksModel) {

        Banks bank = null;

        if (banksRepository.findById(bankId).isPresent()) {
            bank = banksRepository.findById(bankId).get();

            if (Objects.nonNull(banksModel.getNewName())) {
                bank.setName(banksModel.getNewName());
            }
            if (Objects.nonNull(banksModel.getNewPhone())) {
                bank.setPhone(banksModel.getNewPhone());
            }
            if (Objects.nonNull(banksModel.getNewEmail())) {
                bank.setEmail(banksModel.getNewEmail());
            }
            if (Objects.nonNull(banksModel.getNewAddress())) {
                bank.setAddress(banksModel.getNewAddress());
            }

            banksRepository.save(bank);
        }

        return bank;
    }

    @Override
    public Businesses addBusinessEntity(BusinessesModel businessesModel) {

        Businesses business = new Businesses();
        business.setName(businessesModel.getName());
        business.setBusinessCategory(businessCategoryRepository.findByCategory(businessesModel.getBusinessCategory()));
        business.setPhone(businessesModel.getPhone());
        business.setEmail(businessesModel.getEmail());
        business.setAddress(businessesModel.getAddress());
        business.setNoOfSales(BigDecimal.valueOf(0.0));

        businessesRepository.save(business);

        return business;
    }


    @Override
    public Businesses alterBusinessEntityById(Long businessId, BusinessesModel businessesModel) {

        Businesses business = null;

        if(businessesRepository.findById(businessId).isPresent()) {
            business = businessesRepository.findById(businessId).get();

            if (Objects.nonNull(businessesModel.getNewName())) {
                business.setName(businessesModel.getNewName());
            }
            if (Objects.nonNull(businessesModel.getNewBusinessCategory())) {
                business.setBusinessCategory(businessCategoryRepository.findByCategory(businessesModel.getNewBusinessCategory()));
            }
            if (Objects.nonNull(businessesModel.getNewPhone())) {
                business.setPhone(businessesModel.getNewPhone());
            }
            if (Objects.nonNull(businessesModel.getNewEmail())) {
                business.setEmail(businessesModel.getNewEmail());
            }
            if (Objects.nonNull(businessesModel.getNewAddress())) {
                business.setAddress(businessesModel.getNewAddress());
            }

            businessesRepository.save(business);
        }

        return business;
    }


    @Override
    public Clients addClientEntity(ClientsModel clientsModel) {

        Clients client = new Clients();

        client.setName(clientsModel.getName());
        client.setClientCategory(clientCategoryRepository.findByCategory(clientsModel.getClientCategory()));
        client.setPhone(clientsModel.getPhone());
        client.setEmail(clientsModel.getEmail());
        client.setAddress(clientsModel.getAddress());
        client.setNoOfSales(BigDecimal.valueOf(0.0));

        clientsRepository.save(client);

        return client;
    }

    @Override
    public Clients alterClientEntityById(Long clientId, ClientsModel clientsModel) {

        Clients client = null;

        if (clientsRepository.findById(clientId).isPresent()) {
            client = clientsRepository.findById(clientId).get();

            if (Objects.nonNull(clientsModel.getNewName())) {
                client.setName(clientsModel.getNewName());
            }
            if (Objects.nonNull(clientsModel.getClientCategory())) {
                client.setClientCategory(clientCategoryRepository.findByCategory(clientsModel.getClientCategory()));
            }
            if (Objects.nonNull(clientsModel.getNewPhone())) {
                client.setPhone(clientsModel.getNewPhone());
            }
            if (Objects.nonNull(clientsModel.getNewEmail())) {
                client.setEmail(clientsModel.getNewEmail());
            }
            if (Objects.nonNull(clientsModel.getNewAddress())) {
                client.setAddress(clientsModel.getNewAddress());
            }

            clientsRepository.save(client);
        }


        return client;
    }


}
