package com.example.samsungschoolproject.adapter;

import android.annotation.SuppressLint;
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
import java.util.Objects;


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
            addStationItemBinding.lineAdd.setText(station.getLine()+" линия");
            System.out.println(station.getNumLine());
            if (Objects.equals(station.getNumLine(), "1")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_1);}
            else if (Objects.equals(station.getNumLine(), "2")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_2); }
            else if (Objects.equals(station.getNumLine(), "3")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_3); }
            else if (Objects.equals(station.getNumLine(), "4")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_4); }
            else if (Objects.equals(station.getNumLine(), "5")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_5); }
            else if (Objects.equals(station.getNumLine(), "6")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_6); }
            else if (Objects.equals(station.getNumLine(), "7")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_7); }
            else if (Objects.equals(station.getNumLine(), "8")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_8); }
            else if (Objects.equals(station.getNumLine(), "9")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_9); }
            else if (Objects.equals(station.getNumLine(), "4a")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_4a); }
            else if (Objects.equals(station.getNumLine(), "8a")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_8a); }
            else if (Objects.equals(station.getNumLine(), "10")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_10); }
            else if (Objects.equals(station.getNumLine(), "11")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_11); }
            else if (Objects.equals(station.getNumLine(), "12")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_12); }
            else if (Objects.equals(station.getNumLine(), "13")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_13); }
            else if (Objects.equals(station.getNumLine(), "14")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_14); }
            else if (Objects.equals(station.getNumLine(), "15")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_15); }
            else if (Objects.equals(station.getNumLine(), "16")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_16); }
            else if (Objects.equals(station.getNumLine(), "17")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_17); }
            else if (Objects.equals(station.getNumLine(), "18")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_18); }
            else if (Objects.equals(station.getNumLine(), "МЦК")){ addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img_14); }
            else{
                addStationItemBinding.iconStationAdd.setImageResource(R.drawable.img);
            }
            

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
