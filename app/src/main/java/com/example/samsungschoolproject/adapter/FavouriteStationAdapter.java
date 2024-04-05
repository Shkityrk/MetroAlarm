package com.example.samsungschoolproject.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.databinding.AddStationItemBinding;
import com.example.samsungschoolproject.databinding.StantionItemBinding;
import com.example.samsungschoolproject.model.Station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FavouriteStationAdapter extends ListAdapter<Station, FavouriteStationAdapter.AllStationViewHolder> {
    private static final String DATABASE_NAME = "database/stations.db";
    private String url;
    private OnSwitchChangeListener switchChangeListener;

    public interface OnSwitchChangeListener {
        void onSwitchChanged(int position, boolean isChecked);
    }

    public void setSwitchChangeListener(OnSwitchChangeListener listener) {
        this.switchChangeListener = listener;
    }

    public FavouriteStationAdapter(@NonNull DiffUtil.ItemCallback<Station> diffCallback) {
        super(diffCallback);
    }

    public void setSwitchState(boolean state, int position) {
        getItem(position).setAlarm(state);
        notifyItemChanged(position);
    }
    public void setStations(List<Station> stations) {
        submitList(stations);
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

        private Switch switchAlarm;



        public AllStationViewHolder(@NonNull View itemView) {
            super(itemView);
            addStationItemBinding = AddStationItemBinding.bind(itemView);

            switchAlarm = itemView.findViewById(R.id.switch1);
            switchAlarm.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (switchChangeListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        switchChangeListener.onSwitchChanged(position, isChecked);
                    }
                }
            });



        }

        public String setNameItem(String numLine){
            HashMap<String, String> line_names = new HashMap<>();
            line_names.put("1", "Сокольническая"+" линия");
            line_names.put("2", "Замоскворецкая"+" линия");
            line_names.put("3", "Арбатско-Покровская"+" линия");
            line_names.put("4", "Филевская"+" линия");
            line_names.put("5", "Кольцевая"+" линия");
            line_names.put("6", "Калужско-Рижская"+" линия");
            line_names.put("7", "Таганско-Краснопресненская"+" линия");
            line_names.put("8", "Калининско-Солнцевская"+" линия");
            line_names.put("9", "Серпуховско-Тимирязевская"+" линия");
            line_names.put("10", "Люблинско-Дмитровская"+" линия");
            line_names.put("11", "Большая кольцевая"+" линия");
            line_names.put("12", "Бутовская"+" линия");
            line_names.put("13", "Монорельс");
            line_names.put("МЦК", "Московское центральное кольцо");
            line_names.put("15", "Некрасовская"+" линия");
            line_names.put("МЦД", "Московские центральные диаметры");

            return(line_names.get(numLine));
        }

        public String setIconItem(String numLine){
            HashMap<String, String> line_names = new HashMap<>();
            line_names.put("1", "img_1");
            line_names.put("2", "Замоскворецкая"+" линия");
            line_names.put("3", "Арбатско-Покровская"+" линия");
            line_names.put("4", "Филевская"+" линия");
            line_names.put("5", "Кольцевая"+" линия");
            line_names.put("6", "Калужско-Рижская"+" линия");
            line_names.put("7", "Таганско-Краснопресненская"+" линия");
            line_names.put("8", "Калининско-Солнцевская"+" линия");
            line_names.put("9", "Серпуховско-Тимирязевская"+" линия");
            line_names.put("10", "Люблинско-Дмитровская"+" линия");
            line_names.put("11", "Большая кольцевая"+" линия");
            line_names.put("12", "Бутовская"+" линия");
            line_names.put("13", "Монорельс");
            line_names.put("МЦК", "Московское центральное кольцо");
            line_names.put("15", "Некрасовская"+" линия");
            line_names.put("МЦД", "Московские центральные диаметры");

            return(line_names.get(numLine));
        }

        @SuppressLint("SetTextI18n")
        public void bind(Station station) {
            addStationItemBinding.addStation.setText(station.getName());  // добавление названия станции
            addStationItemBinding.lineAdd.setText(setNameItem(station.getNumLine()));  // добавление названия линии
            addStationItemBinding.iconStationAdd.setImageResource(IconManager.getIconResource(station.getNumLine()));  // добавление иконки линии
            switchAlarm.setChecked(station.getBoolAlarm());  // добавление состояния переключателя
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
                    && Objects.equals(oldItem.getName(), newItem.getName()) // Используем Objects.equals() для безопасного сравнения строк
                    && Objects.equals(oldItem.getLatitude(), newItem.getLatitude())
                    && Objects.equals(oldItem.getLongitude(), newItem.getLongitude()));
        }
    }

    public void filterById(String text, List<Station> stations) {
        List<Station> filteredList = new ArrayList<>();

        if (text.isEmpty()) {
            filteredList.addAll(stations);
        } else {
            String searchText = text.toLowerCase().trim(); // Приводим текст к нижнему регистру и удаляем пробелы в начале и конце
            for (Station station : getCurrentList()) {
                if (String.valueOf(station.getId()).contains(searchText)) {
                    filteredList.add(station);
                } else if (station.getName().toLowerCase().startsWith(searchText)) {
                    filteredList.add(station);
                }
            }
        }
        submitList(filteredList);
    }



    public void updateStationsList(List<Station> stationsList) {
        submitList(stationsList);
    }



}
