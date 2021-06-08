package com.rnm.restcountryitems.adapter;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.rnm.restcountryitems.R;
import com.rnm.restcountryitems.SvgDecoder;
import com.rnm.restcountryitems.SvgDrawableTranscoder;
import com.rnm.restcountryitems.SvgSoftwareLayerSetter;
import com.rnm.restcountryitems.model.CountryItem;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CountryItemAdapter extends RecyclerView.Adapter<CountryItemAdapter.CountryItemViewHolder> implements Filterable {
    private ArrayList<CountryItem> mCountryItemList;
    private ArrayList<CountryItem> mCountryItemListFull;
    private OnItemClickListener mListener;
    private Context mContext;

    @Override
    public Filter getFilter() {
        return countryFilter;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public void setCountryItemArrayList(ArrayList<CountryItem> countryItemArrayList){
        mCountryItemList = countryItemArrayList;
        mCountryItemListFull = new ArrayList<>(countryItemArrayList);
    }

    public static class CountryItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivFlag;
        public TextView tvCountryName;

        public CountryItemViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            ivFlag = itemView.findViewById(R.id.ivFlag);
            tvCountryName = itemView.findViewById(R.id.tvCountryName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
    public CountryItemAdapter(ArrayList<CountryItem> countryItemList, Context context) {
        mCountryItemList = countryItemList;
        mCountryItemListFull = new ArrayList<>(countryItemList);
        mContext = context;
    }
    @Override
    public CountryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country_display, parent, false);
        CountryItemViewHolder evh = new CountryItemViewHolder(v, mListener);
        return evh;
    }
    @Override
    public void onBindViewHolder(CountryItemViewHolder holder, int position) {
        CountryItem currentItem = mCountryItemList.get(position);

        holder.tvCountryName.setText(currentItem.getName());
        GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder = Glide.with(mContext)
                .using(Glide.buildStreamModelLoader(Uri.class, mContext), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .listener(new SvgSoftwareLayerSetter<Uri>());
        requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE)
                .load(Uri.parse(currentItem.getFlag()))
                .error(R.drawable.ic_flag)
                .placeholder(R.drawable.ic_flag)
                .fallback(R.drawable.ic_flag)
                .into(holder.ivFlag);


    }
    @Override
    public int getItemCount() {
        if(mCountryItemList != null){
            return mCountryItemList.size();
        }
        return 0;
    }
    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CountryItem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mCountryItemListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CountryItem item : mCountryItemListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern) ||
                            convertToLowerCase(item.getAltSpellings()).contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mCountryItemList.clear();
            mCountryItemList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }

        private ArrayList<String> convertToLowerCase(List<String> sList){
            ArrayList<String> nsList = new ArrayList<>();
            for(String s : sList){
                nsList.add(s.toLowerCase());
            }
            return nsList;
        }
    };
}
