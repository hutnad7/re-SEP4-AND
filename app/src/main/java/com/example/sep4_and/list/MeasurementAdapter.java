package com.example.sep4_and.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4_and.R;
import com.example.sep4_and.model.Measurement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.MeasurementViewHolder> {

    private List<Measurement> measurements = new ArrayList<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public MeasurementAdapter() {
        // Default constructor
    }

    public MeasurementAdapter(List<Measurement> measurements) {
        this.measurements = measurements;
    }
    @NonNull
    @Override
    public MeasurementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_measurement, parent, false);
        return new MeasurementViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MeasurementViewHolder holder, int position) {
        Measurement currentMeasurement = measurements.get(position);
        holder.textViewType.setText(currentMeasurement.getType().toString());
        holder.textViewValue.setText(String.valueOf(currentMeasurement.getValue()));
        holder.textViewDate.setText(dateFormat.format(currentMeasurement.getTimestamp()));
        holder.textViewTime.setText(timeFormat.format(currentMeasurement.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return measurements != null ? measurements.size() : 0;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
        notifyDataSetChanged();
    }

    static class MeasurementViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewType;
        private TextView textViewValue;
        private TextView textViewDate;
        private TextView textViewTime;

        public MeasurementViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewType = itemView.findViewById(R.id.textViewMeasurementType);
            textViewValue = itemView.findViewById(R.id.textViewMeasurementValue);
            textViewDate = itemView.findViewById(R.id.textViewMeasurementDate);
            textViewTime = itemView.findViewById(R.id.textViewMeasurementTime);
        }
    }
}
