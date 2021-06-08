package com.rnm.restcountryitems.api;

import com.rnm.restcountryitems.model.CountryItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CountriesApi {
    @GET("all")
    Call<ArrayList<CountryItem>> getAllCountryItems();
}
