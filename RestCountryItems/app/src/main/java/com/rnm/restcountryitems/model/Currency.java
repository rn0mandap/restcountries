package com.rnm.restcountryitems.model;

public class Currency{
    public String code;
    public String name;
    public String symbol;

    public Currency(String code, String name, String symbol) {
        this.code = code;
        this.name = name;
        this.symbol = symbol;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
