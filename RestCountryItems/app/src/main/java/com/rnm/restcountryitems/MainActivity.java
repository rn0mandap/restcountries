package com.rnm.restcountryitems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rnm.restcountryitems.adapter.CountryItemAdapter;
import com.rnm.restcountryitems.api.CountriesApi;
import com.rnm.restcountryitems.model.CountryItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://www.restcountries.eu/rest/v2/";
    private ArrayList<CountryItem> countryItemArrayList;
    private RecyclerView rvMain;
    private RecyclerView.LayoutManager layoutManager;
    private CountryItemAdapter countryItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryItemArrayList = new ArrayList<>();
        initRecyclerView();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CountriesApi countriesApi = retrofit.create(CountriesApi.class);

        Call<ArrayList<CountryItem>> call = countriesApi.getAllCountryItems();
        call.enqueue(new Callback<ArrayList<CountryItem>>() {
            @Override
            public void onResponse(Call<ArrayList<CountryItem>> call, Response<ArrayList<CountryItem>> response) {
                if(!response.isSuccessful()){
                    displayToast("Code: " + response.code());
                    return;
                }
                //displayToast(response.body().toString());
                countryItemArrayList = (ArrayList<CountryItem>) response.body();
                countryItemAdapter.setCountryItemArrayList(countryItemArrayList);
                countryItemAdapter.notifyDataSetChanged();
                //displayToast(countryItemArrayList.get(0).getName()); // works!
            }
            @Override
            public void onFailure(Call<ArrayList<CountryItem>> call, Throwable t) {
                //tvTest.setText(t.getMessage());
                displayToast("Error");
            }
        });
    }

    private void initRecyclerView(){
        rvMain = findViewById(R.id.rvMain);
        rvMain.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        countryItemAdapter = new CountryItemAdapter(countryItemArrayList, MainActivity.this);
        rvMain.setLayoutManager(layoutManager);
        rvMain.setAdapter(countryItemAdapter);
        countryItemAdapter.setOnItemClickListener(new CountryItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //displayToast(countryItemArrayList.get(position).getName()+" clicked!");
                Intent intent = new Intent(MainActivity.this, CountryViewActivity.class);
                Gson gson = new Gson();
                String cis = gson.toJson(countryItemArrayList.get(position));
                intent.putExtra("COUNTRY_ITEM_STRING", cis);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                countryItemAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void displayToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}