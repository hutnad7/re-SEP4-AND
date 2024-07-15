package com.example.sep4_and.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4_and.R;
import com.example.sep4_and.model.Measurement;

import java.util.List;
public class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.MeasurementViewHolder> {

    private List<Measurement> measurements;

    public MeasurementAdapter(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    @NonNull
    @Override
    public MeasurementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_measurement, parent, false);
        return new MeasurementViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MeasurementViewHolder holder, int position) {
        Measurement measurement = measurements.get(position);
        holder.textViewType.setText(measurement.getType().toString());
        holder.textViewValue.setText(String.valueOf(measurement.getValue()));
        holder.textViewTimestamp.setText(measurement.getTimestamp().toString());
    }

    @Override
    public int getItemCount() {
        if(measurements!=null)
            return measurements.size();
        else
            return 0;
    }

    static class MeasurementViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewType;
        private TextView textViewValue;
        private TextView textViewTimestamp;

        public MeasurementViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewType = itemView.findViewById(R.id.textViewType);
            textViewValue = itemView.findViewById(R.id.textViewValue);
            textViewTimestamp = itemView.findViewById(R.id.textViewTimestamp);
        }
    }
}
