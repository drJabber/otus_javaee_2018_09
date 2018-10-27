package rnk.l08.client.entities;

import lombok.Data;

@Data
public class CurrencyEntity{
    private String charCode;
    private String value;

    public CurrencyEntity(String charCode, String value){
        this.charCode=charCode;
        this.value=value;
    }

}