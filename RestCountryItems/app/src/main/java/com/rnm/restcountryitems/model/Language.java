package com.rnm.restcountryitems.model;

public class Language{
    public String iso639_1;
    public String iso639_2;
    public String name;
    public String nativeName;

    public Language(String iso639_1, String iso639_2, String name, String nativeName) {
        this.iso639_1 = iso639_1;
        this.iso639_2 = iso639_2;
        this.name = name;
        this.nativeName = nativeName;
    }

    public String getIso639_1() {
        return iso639_1;
    }

    public String getIso639_2() {
        return iso639_2;
    }

    public String getName() {
        return name;
    }

    public String getNativeName() {
        return nativeName;
    }
}
