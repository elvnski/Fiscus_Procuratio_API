package com.fpapi.fiscus_procuratio_api.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanCategoryModel {

    private String category;
    private String newCategoryName;

}
