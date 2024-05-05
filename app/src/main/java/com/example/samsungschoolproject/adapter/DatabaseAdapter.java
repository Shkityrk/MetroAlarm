package com.example.samsungschoolproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.model.DatabaseModel;

import java.util.List;

public class DatabaseAdapter extends RecyclerView.Adapter<DatabaseAdapter.ViewHolder> {
    private List<DatabaseModel> databaseList;

    public DatabaseAdapter(List<DatabaseModel> databaseList) {
        this.databaseList = databaseList;
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
            Toast.makeText(v.getContext(), "Database: " + database.getDatabase(), Toast.LENGTH_SHORT).show();
        });
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