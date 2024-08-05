package com.example.sep4_and.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4_and.R;
import com.example.sep4_and.model.GreenHouseDetailed;

import java.util.List;

public class GreenHouseAdapter extends RecyclerView.Adapter<GreenHouseAdapter.GreenhouseViewHolder> {
    private List<GreenHouseDetailed> greenhouses;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int greenhouseId);
    }

    public GreenHouseAdapter(List<GreenHouseDetailed> greenhouses, OnItemClickListener listener) {
        this.greenhouses = greenhouses;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GreenhouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_greenhouse, parent, false);
        return new GreenhouseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GreenhouseViewHolder holder, int position) {
        GreenHouseDetailed greenhouse = greenhouses.get(position);
        holder.locationTextView.setText(greenhouse.getLocation());
        holder.nameTextView.setText(greenhouse.getName());
        holder.co2TextView.setText(String.format("%dppm", (int) greenhouse.getCO2()));
        holder.humidityTextView.setText(String.format("%d%%", (int) greenhouse.getHumidity()));
        holder.temperatureTextView.setText(String.format("%.1f°C", greenhouse.getTemperature()));
        holder.lightTextView.setText(String.format("%d%%", (int) greenhouse.getLight()));
        holder.itemView.setOnClickListener(v -> listener.onItemClick(greenhouse.getId()));
    }

    @Override
    public int getItemCount() {
        return greenhouses.size();
    }

    public static class GreenhouseViewHolder extends RecyclerView.ViewHolder {
        TextView locationTextView;
        TextView nameTextView;
        TextView co2TextView;
        TextView humidityTextView;
        TextView temperatureTextView;
        TextView lightTextView;

        public GreenhouseViewHolder(@NonNull View itemView) {
            super(itemView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            co2TextView = itemView.findViewById(R.id.co2TextView);
            humidityTextView = itemView.findViewById(R.id.humidityTextView);
            temperatureTextView = itemView.findViewById(R.id.temperatureTextView);
            lightTextView = itemView.findViewById(R.id.lightTextView);
        }
    }
}
