package com.example.sep4_and.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sep4_and.R;
import com.example.sep4_and.model.Threshold;

import java.util.ArrayList;
import java.util.List;
public class ThresholdAdapter extends RecyclerView.Adapter<ThresholdAdapter.ThresholdViewHolder> {

    private List<Threshold> thresholds = new ArrayList<>();
    private OnThresholdDeleteClickListener deleteClickListener;

    public interface OnThresholdDeleteClickListener {
        void onThresholdDeleteClick(Threshold threshold);
    }

    public ThresholdAdapter(OnThresholdDeleteClickListener deleteClickListener) {
        this.deleteClickListener = deleteClickListener;
    }

    @NonNull
    @Override
    public ThresholdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_threshold, parent, false);
        return new ThresholdViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ThresholdViewHolder holder, int position) {
        Threshold currentThreshold = thresholds.get(position);
        holder.textViewType.setText(currentThreshold.getType().name());
        holder.textViewMinValue.setText(String.valueOf(currentThreshold.getMinValue()));
        holder.textViewMaxValue.setText(String.valueOf(currentThreshold.getMaxValue()));
        holder.buttonDelete.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onThresholdDeleteClick(currentThreshold);
            }
        });
    }

    @Override
    public int getItemCount() {
        return thresholds.size();
    }

    public void setThresholds(List<Threshold> thresholds) {
        this.thresholds = thresholds;
        notifyDataSetChanged();
    }

    static class ThresholdViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewType;
        private TextView textViewMinValue;
        private TextView textViewMaxValue;
        private Button buttonDelete;

        public ThresholdViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewType = itemView.findViewById(R.id.textViewThresholdType);
            textViewMinValue = itemView.findViewById(R.id.textViewThresholdMinValue);
            textViewMaxValue = itemView.findViewById(R.id.textViewThresholdMaxValue);
            buttonDelete = itemView.findViewById(R.id.buttonDeleteThreshold);
        }
    }
}
