package com.rnm.restcountryitems;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.rnm.restcountryitems.model.CountryItem;
import com.rnm.restcountryitems.model.Currency;
import com.rnm.restcountryitems.model.Language;

import java.io.InputStream;

public class CountryViewActivity extends AppCompatActivity implements OnMapReadyCallback {
    MapView mapView;
    ImageView ivFlag;
    TextView tvCountryName;
    TextView tvNativeName;
    TextView tvCapital;
    TextView tvRegion;
    TextView tvSubregion;
    TextView tvAbbv;
    TextView tvCallingCodes;
    TextView tvTopLvlDomain;
    TextView tvPopulation;
    TextView tvDemonym;
    TextView tvCurrencies;
    TextView tvLngLat;
    TextView tvLanguages;
    TextView tvBorders;

    CountryItem cI;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_view);

        mapView = findViewById(R.id.mapView);
        ivFlag = findViewById(R.id.ivFlag);
        tvCountryName = findViewById(R.id.tvCountryName);
        tvNativeName = findViewById(R.id.tvNativeName);
        tvCapital = findViewById(R.id.tvCapital);
        tvRegion = findViewById(R.id.tvRegion);
        tvSubregion = findViewById(R.id.tvSubregion);
        tvAbbv = findViewById(R.id.tvAbbv);
        tvCallingCodes = findViewById(R.id.tvCallingCodes);
        tvTopLvlDomain = findViewById(R.id.tvTopLvlDomain);
        tvPopulation = findViewById(R.id.tvPopulation);
        tvDemonym = findViewById(R.id.tvDemonym);
        tvCurrencies = findViewById(R.id.tvCurrencies);
        tvLngLat = findViewById(R.id.tvLngLat);
        tvLanguages = findViewById(R.id.tvLanguages);
        tvBorders = findViewById(R.id.tvBorders);

        Intent intent = getIntent();
        Gson gson = new Gson();
        cI = gson.fromJson(intent.getStringExtra("COUNTRY_ITEM_STRING"), CountryItem.class);

        // min
        GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder = Glide.with(this)
                .using(Glide.buildStreamModelLoader(Uri.class, this), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .listener(new SvgSoftwareLayerSetter<Uri>());
        requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE)
                .load(Uri.parse(cI.getFlag()))
                .error(R.drawable.ic_flag)
                .placeholder(R.drawable.ic_flag)
                .fallback(R.drawable.ic_flag)
                .into(ivFlag);
        tvCountryName.setText(cI.getName());
        tvNativeName.setText(cI.getNativeName());
        tvCapital.setText(emptyCheck((cI.getCapital())));
        tvRegion.setText(emptyCheck(cI.getRegion()));
        tvSubregion.setText(emptyCheck(cI.getSubregion()));
        tvAbbv.setText(emptyCheck(String.join(", ",cI.getAltSpellings())));
        tvCallingCodes.setText(emptyCheck((String.join(", ",cI.getCallingCodes()))));
        tvTopLvlDomain.setText(emptyCheck((String.join(", ",cI.getTopLevelDomain()))));
        tvPopulation.setText(emptyCheck(""+cI.getPopulation()));
        tvDemonym.setText(emptyCheck(cI.getDemonym()));
        tvCurrencies.setText(countryCurrencies(cI));
        tvLngLat.setText(cI.getLatlng().get(1)+", "+cI.getLatlng().get(0));
        tvLanguages.setText(countryLanguages(cI));
        tvBorders.setText(emptyCheck(String.join(", ",cI.getBorders())));

    }

    private String countryCurrencies(CountryItem cI){
        String curs = "";
        for(Currency cur : cI.getCurrencies()){
            curs += cur.name + ", ";
        }
        if(curs.equals("")){
            return emptyCheck(curs);
        }else{
            return curs.substring(0,curs.length()-2);
        }
    }
    private String countryLanguages(CountryItem cI){
        String langs = "";
        for(Language lang : cI.getLanguages()){
            langs += lang.name + ", ";
        }
        if(langs.equals("")){
            return emptyCheck(langs);
        }else{
            return langs.substring(0,langs.length()-2);
        }
    }
    private String emptyCheck(String text){
        if(text.equals("")){
            return "n/a";
        }
        return text;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(cI.getLatlng().get(0),cI.getLatlng().get(1))));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
