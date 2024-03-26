package com.example.samsungschoolproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.databinding.AddStationItemBinding;
import com.example.samsungschoolproject.model.Station;

import java.util.ArrayList;
import java.util.List;

public class AllStationAdapter extends RecyclerView.Adapter<AllStationAdapter.AllStationViewHolder>{
    private List<Station> stations = new ArrayList<>();

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
        holder.bind(stations.get(position));
    }

    @Override
    public int getItemCount() {
        return stations.size();
    }

    public class AllStationViewHolder extends RecyclerView.ViewHolder{
        private  AddStationItemBinding addStationItemBinding;

        public AllStationViewHolder(@NonNull View itemView) {
            super(itemView);
            addStationItemBinding = AddStationItemBinding.bind(itemView);
        }
        public void bind(Station station){
            addStationItemBinding.addStation.setText(station.name);
            addStationItemBinding.lineAdd.setText(station.line);

        }

    }
    public void Add(Station station){
        stations.add(station);
        notifyDataSetChanged();
    }




}
