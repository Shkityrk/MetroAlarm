package com.example.samsungschoolproject.adapter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.activity.IntroErrorActivity;
import com.example.samsungschoolproject.activity.IntroSuccessActivity;
import com.example.samsungschoolproject.activity.MainActivity;
import com.example.samsungschoolproject.model.DatabaseModel;
import com.example.samsungschoolproject.utils.NetworkUtils;
import com.example.samsungschoolproject.utils.SharedPreferencesUtils;

import java.util.List;

public class DatabaseAdapter extends RecyclerView.Adapter<DatabaseAdapter.ViewHolder> {
    private List<DatabaseModel> databaseList;
    private Application application;
    private Context context;



    public DatabaseAdapter(List<DatabaseModel> databaseList, Application application, Context context) {
        this.databaseList = databaseList;
        this.application = application;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_database, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DatabaseModel database = databaseList.get(position);
        holder.nameTextView.setText(database.getName());

        holder.itemView.setOnClickListener(v -> {
//            Toast.makeText(v.getContext(), "Database: " + database.getDatabase(), Toast.LENGTH_SHORT).show();
            SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(v.getContext());
            sharedPreferencesUtils.setDataName(database.getName());
            sharedPreferencesUtils.setDatabaseMap(database.getNameGET());
            try {
                NetworkUtils networkUtils = new NetworkUtils();
                networkUtils.updateDataFromJSON("https://79.137.197.216/get_station_data/?databaseApplication=StationModel&db_name="+database.getNameGET(), application, context);

//                Toast.makeText(v.getContext(), "Setup: " + database.getDatabase(), Toast.LENGTH_SHORT).show();
                Log.d("DatabaseAdapter", "Database: " + database.getDatabase()+ " done");

                startSuccessActivity(v);


            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void startSuccessActivity(View v) {
        Intent intent = new Intent(v.getContext(), IntroSuccessActivity.class);
        v.getContext().startActivity(intent);
    }



    @Override
    public int getItemCount() {
        return databaseList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.databaseNameTextView);
        }
    }
}