package com.example.samsungschoolproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.databinding.AddStationItemBinding;
import com.example.samsungschoolproject.fragment.viewmodel.StationsViewModel;
import com.example.samsungschoolproject.model.Station;

import java.util.ArrayList;
import java.util.List;


public class AllStationAdapter extends ListAdapter<Station, AllStationAdapter.AllStationViewHolder> {
    private static final String DATABASE_NAME = "database/stations.db";
    private String url;

    public AllStationAdapter(@NonNull DiffUtil.ItemCallback<Station> diffCallback) {
        super(diffCallback);
    }


    @NonNull
    @Override
    public AllStationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.add_station_item,
                parent,
                false
        );
        return new AllStationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllStationViewHolder holder, int position) {
        holder.bind(getItem(position));

    }


    public class AllStationViewHolder extends RecyclerView.ViewHolder {
        private AddStationItemBinding addStationItemBinding;

        public AllStationViewHolder(@NonNull View itemView) {
            super(itemView);
            addStationItemBinding = AddStationItemBinding.bind(itemView);
        }

        public void bind(Station station) {
            addStationItemBinding.addStation.setText(station.getName());
            addStationItemBinding.lineAdd.setText(station.getLine());

        }
    }


    public static class StationDiff extends DiffUtil.ItemCallback<Station> {

        @Override
        public boolean areItemsTheSame(@NonNull Station oldItem, @NonNull Station newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Station oldItem, @NonNull Station newItem) {
            return (oldItem.getId() == newItem.getId()
                    && oldItem.getName().equals(newItem.getName())
                    && oldItem.getLatitude().equals(newItem.getLatitude())
                    && oldItem.getLongitude().equals(newItem.getLongitude()));
        }
    }


}
