package com.example.sep4_and.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4_and.R;

import com.example.sep4_and.model.GreenHouse;
import com.example.sep4_and.model.MeasurementType;
import com.example.sep4_and.model.Threshold;
import com.example.sep4_and.viewmodel.GreenHouseViewModel;

import java.util.ArrayList;
import java.util.List;




public class GreenHouseAdapter extends RecyclerView.Adapter<GreenHouseAdapter.GreenHouseViewHolder> {

    private List<GreenHouse> greenHouses = new ArrayList<>();
    private GreenHouseViewModel greenHouseViewModel;

    private OnViewMeasurementsButtonClickListener onViewMeasurementsButtonClickListener;
    private OnDeleteButtonClickListener onDeleteButtonClickListener;
    private OnViewDetailsButtonClickListener onViewDetailsButtonClickListener;



    public interface OnViewMeasurementsButtonClickListener {
        void onViewMeasurementsButtonClick(GreenHouse greenHouse);
    }

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(GreenHouse greenHouse);
    }

    public interface OnViewDetailsButtonClickListener {
        void onViewDetailsButtonClick(GreenHouse greenHouse);
    }

    public GreenHouseAdapter(GreenHouseViewModel viewModel,

                             OnViewMeasurementsButtonClickListener viewMeasurementsListener,
                             OnDeleteButtonClickListener deleteButtonClickListener,
                             OnViewDetailsButtonClickListener viewDetailsListener) {
        this.greenHouseViewModel = viewModel;

        this.onViewMeasurementsButtonClickListener = viewMeasurementsListener;
        this.onDeleteButtonClickListener = deleteButtonClickListener;
        this.onViewDetailsButtonClickListener = viewDetailsListener;
    }

    @NonNull
    @Override
    public GreenHouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_greenhouse, parent, false);
        return new GreenHouseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GreenHouseViewHolder holder, int position) {
        GreenHouse currentGreenHouse = greenHouses.get(position);
        holder.textViewName.setText(currentGreenHouse.getName());
        holder.textViewLocation.setText(currentGreenHouse.getLocation());

        // Load the latest measurements for each type
        greenHouseViewModel.getLatestMeasurementForType(currentGreenHouse.getId(), MeasurementType.CO2).observe((LifecycleOwner) holder.itemView.getContext(), measurement -> {
            if (measurement != null) {
                holder.textViewCO2.setText(String.format("%.2f", measurement.getValue()));
            }
        });

        greenHouseViewModel.getLatestMeasurementForType(currentGreenHouse.getId(), MeasurementType.HUMIDITY).observe((LifecycleOwner) holder.itemView.getContext(), measurement -> {
            if (measurement != null) {
                holder.textViewHumidity.setText(String.format("%.2f", measurement.getValue()));
            }
        });

        greenHouseViewModel.getLatestMeasurementForType(currentGreenHouse.getId(), MeasurementType.TEMPERATURE).observe((LifecycleOwner) holder.itemView.getContext(), measurement -> {
            if (measurement != null) {
                holder.textViewTemperature.setText(String.format("%.2f", measurement.getValue()));
            }
        });

        greenHouseViewModel.getLatestMeasurementForType(currentGreenHouse.getId(), MeasurementType.LIGHT).observe((LifecycleOwner) holder.itemView.getContext(), measurement -> {
            if (measurement != null) {
                holder.textViewLight.setText(String.format("%.2f", measurement.getValue()));
            }
        });



        holder.buttonViewMeasurements.setOnClickListener(v -> {
            if (onViewMeasurementsButtonClickListener != null) {
                onViewMeasurementsButtonClickListener.onViewMeasurementsButtonClick(currentGreenHouse);
            }
        });

        holder.buttonDeleteGreenHouse.setOnClickListener(v -> {
            if (onDeleteButtonClickListener != null) {
                onDeleteButtonClickListener.onDeleteButtonClick(currentGreenHouse);
            }
        });

        holder.buttonViewDetails.setOnClickListener(v -> {
            if (onViewDetailsButtonClickListener != null) {
                onViewDetailsButtonClickListener.onViewDetailsButtonClick(currentGreenHouse);
            }
        });
    }

    @Override
    public int getItemCount() {
        return greenHouses != null ? greenHouses.size() : 0;
    }

    public void setGreenHouses(List<GreenHouse> greenHouses) {
        this.greenHouses = greenHouses;
        notifyDataSetChanged();
    }

    static class GreenHouseViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewLocation;

        private TextView textViewCO2;
        private TextView textViewHumidity;
        private TextView textViewTemperature;
        private TextView textViewLight;
        private Button buttonViewMeasurements;
        private Button buttonDeleteGreenHouse;
        private Button buttonViewDetails;

        public GreenHouseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.nameTextView);
            textViewLocation = itemView.findViewById(R.id.locationTextView);

            textViewCO2 = itemView.findViewById(R.id.co2TextView);
            textViewHumidity = itemView.findViewById(R.id.humidityTextView);
            textViewTemperature = itemView.findViewById(R.id.temperatureTextView);
            textViewLight = itemView.findViewById(R.id.lightTextView);
            buttonViewMeasurements = itemView.findViewById(R.id.buttonViewMeasurements);
            buttonDeleteGreenHouse = itemView.findViewById(R.id.buttonDeleteGreenHouse);
            buttonViewDetails = itemView.findViewById(R.id.buttonViewDetails);
        }
    }
}