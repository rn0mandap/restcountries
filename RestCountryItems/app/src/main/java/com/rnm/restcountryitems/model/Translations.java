package com.rnm.restcountryitems.model;

public class Translations{
    public String de;
    public String es;
    public String fr;
    public String ja;
    public String it;
    public String br;
    public String pt;
    public String nl;
    public String hr;
    public String fa;

    public Translations(String de, String es, String fr, String ja, String it, String br, String pt, String nl, String hr, String fa) {
        this.de = de;
        this.es = es;
        this.fr = fr;
        this.ja = ja;
        this.it = it;
        this.br = br;
        this.pt = pt;
        this.nl = nl;
        this.hr = hr;
        this.fa = fa;
    }

    public String getDe() {
        return de;
    }

    public String getEs() {
        return es;
    }

    public String getFr() {
        return fr;
    }

    public String getJa() {
        return ja;
    }

    public String getIt() {
        return it;
    }

    public String getBr() {
        return br;
    }

    public String getPt() {
        return pt;
    }

    public String getNl() {
        return nl;
    }

    public String getHr() {
        return hr;
    }

    public String getFa() {
        return fa;
    }
}
