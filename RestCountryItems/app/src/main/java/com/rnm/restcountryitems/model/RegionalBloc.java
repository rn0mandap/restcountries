package com.rnm.restcountryitems.model;

import java.util.List;

public class RegionalBloc{
    public String acronym;
    public String name;
    public List<Object> otherAcronyms;
    public List<String> otherNames;

    public RegionalBloc(String acronym, String name, List<Object> otherAcronyms, List<String> otherNames) {
        this.acronym = acronym;
        this.name = name;
        this.otherAcronyms = otherAcronyms;
        this.otherNames = otherNames;
    }

    public String getAcronym() {
        return acronym;
    }

    public String getName() {
        return name;
    }

    public List<Object> getOtherAcronyms() {
        return otherAcronyms;
    }

    public List<String> getOtherNames() {
        return otherNames;
    }
}
