package com.example.sep4_and.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4_and.R;
import com.example.sep4_and.model.DbCrossReference.GreenHouseWithUsers;
import com.example.sep4_and.model.GreenHouse;
import com.example.sep4_and.model.Threshold;

import java.util.ArrayList;
import java.util.List;



public class GreenHouseAdapter extends RecyclerView.Adapter<GreenHouseAdapter.GreenHouseViewHolder> {

    private List<GreenHouseWithUsers> greenHousesWithUsers = new ArrayList<>();
    private OnPairButtonClickListener onPairButtonClickListener;
    private OnViewThresholdsButtonClickListener onViewThresholdsButtonClickListener;
    private OnViewMeasurementsButtonClickListener onViewMeasurementsButtonClickListener;
    private OnDeleteButtonClickListener onDeleteButtonClickListener;

    public interface OnPairButtonClickListener {
        void onPairButtonClick(GreenHouse greenHouse);
    }

    public interface OnViewThresholdsButtonClickListener {
        void onViewThresholdsButtonClick(GreenHouse greenHouse);
    }

    public interface OnViewMeasurementsButtonClickListener {
        void onViewMeasurementsButtonClick(GreenHouse greenHouse);
    }

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(GreenHouse greenHouse);
    }

    public GreenHouseAdapter(OnPairButtonClickListener pairListener, OnViewThresholdsButtonClickListener viewThresholdsListener, OnViewMeasurementsButtonClickListener viewMeasurementsListener, OnDeleteButtonClickListener deleteButtonClickListener) {
        this.onPairButtonClickListener = pairListener;
        this.onViewThresholdsButtonClickListener = viewThresholdsListener;
        this.onViewMeasurementsButtonClickListener = viewMeasurementsListener;
        this.onDeleteButtonClickListener = deleteButtonClickListener;
    }

    @NonNull
    @Override
    public GreenHouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_greenhouse, parent, false);
        return new GreenHouseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GreenHouseViewHolder holder, int position) {
        GreenHouseWithUsers currentGreenHouseWithUsers = greenHousesWithUsers.get(position);
        GreenHouse currentGreenHouse = currentGreenHouseWithUsers.greenHouse;
        holder.textViewName.setText(currentGreenHouse.getName());
        holder.textViewLocation.setText(currentGreenHouse.getLocation());

        // Check if the greenhouse has an owner
        if (currentGreenHouseWithUsers.users.isEmpty()) {
            holder.textViewOwner.setText("Owner: None");
            holder.buttonPairWithUser.setVisibility(View.VISIBLE);
        } else {
            holder.textViewOwner.setText("Owner: " + currentGreenHouseWithUsers.users.get(0).getUserName());
            holder.buttonPairWithUser.setVisibility(View.GONE);
        }

        holder.buttonPairWithUser.setOnClickListener(v -> {
            if (onPairButtonClickListener != null) {
                onPairButtonClickListener.onPairButtonClick(currentGreenHouse);
            }
        });

        holder.buttonViewThresholds.setOnClickListener(v -> {
            if (onViewThresholdsButtonClickListener != null) {
                onViewThresholdsButtonClickListener.onViewThresholdsButtonClick(currentGreenHouse);
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
    }

    @Override
    public int getItemCount() {
        if (greenHousesWithUsers != null) {
            return greenHousesWithUsers.size();
        } else {
            return 0;
        }
    }

    public void setGreenHousesWithUsers(List<GreenHouseWithUsers> greenHousesWithUsers) {
        this.greenHousesWithUsers = greenHousesWithUsers;
        notifyDataSetChanged();
    }

    static class GreenHouseViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewLocation;
        private TextView textViewOwner;
        private Button buttonPairWithUser;
        private Button buttonViewThresholds;
        private Button buttonViewMeasurements;
        private Button buttonDeleteGreenHouse;

        public GreenHouseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewGreenHouseName);
            textViewLocation = itemView.findViewById(R.id.textViewGreenHouseLocation);
            textViewOwner = itemView.findViewById(R.id.textViewOwner);
            buttonPairWithUser = itemView.findViewById(R.id.buttonPairWithUser);
            buttonViewThresholds = itemView.findViewById(R.id.buttonViewThresholds);
            buttonViewMeasurements = itemView.findViewById(R.id.buttonViewMeasurements);
            buttonDeleteGreenHouse = itemView.findViewById(R.id.buttonDeleteGreenHouse);
        }
    }
}