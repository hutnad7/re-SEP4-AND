package com.example.sep4_and.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4_and.R;
import com.example.sep4_and.model.Measurement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoricalDataAdapter extends RecyclerView.Adapter<HistoricalDataAdapter.HistoricalDataViewHolder> {

    private List<Measurement> measurements;

    public HistoricalDataAdapter(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    @NonNull
    @Override
    public HistoricalDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_measurement, parent, false);
        return new HistoricalDataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricalDataViewHolder holder, int position) {
        Measurement measurement = measurements.get(position);
        holder.bind(measurement);
    }

    @Override
    public int getItemCount() {
        return measurements.size();
    }

    static class HistoricalDataViewHolder extends RecyclerView.ViewHolder {

        private TextView measurementType;
        private TextView measurementValue;
        private TextView measurementDate;
        private TextView measurementTime;

        public HistoricalDataViewHolder(@NonNull View itemView) {
            super(itemView);
            measurementType = itemView.findViewById(R.id.textViewMeasurementType);
            measurementValue = itemView.findViewById(R.id.textViewMeasurementValue);
            measurementDate = itemView.findViewById(R.id.textViewMeasurementDate);
            measurementTime = itemView.findViewById(R.id.textViewMeasurementTime);
        }

        public void bind(Measurement measurement) {
            measurementType.setText(measurement.getType().toString());
            measurementValue.setText(String.valueOf(measurement.getValue()));

            Date date = measurement.getTimestamp();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            measurementDate.setText(dateFormat.format(date));
            measurementTime.setText(timeFormat.format(date));
        }
    }
}