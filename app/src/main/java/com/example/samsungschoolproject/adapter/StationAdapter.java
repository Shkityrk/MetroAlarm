package com.example.samsungschoolproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.databinding.StantionItemBinding;
import com.example.samsungschoolproject.model.Station;

import java.util.ArrayList;
import java.util.List;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.StationViewHolder>{
    private List<Station> stations = new ArrayList<>();

    @NonNull
    @Override
    public StationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.stantion_item,
                parent,
                false
        );
        return new StationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StationViewHolder holder, int position) {
        holder.bind(stations.get(position));
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    public class StationViewHolder extends RecyclerView.ViewHolder{
        private StantionItemBinding stantionItemBinding;

        public StationViewHolder(@NonNull View itemView) {
            super(itemView);
            stantionItemBinding = StantionItemBinding.bind(itemView);
        }
        public void bind(Station station){
            stantionItemBinding.name.setText(station.name);
            stantionItemBinding.line.setText(station.line);
            stantionItemBinding.color.setText(station.color);
            //productItemBinding.price.setText(String.valueOf(product.price));
        }

    }
    public void Add(Station station){
        stations.add(station);
        notifyDataSetChanged();
    }




}
