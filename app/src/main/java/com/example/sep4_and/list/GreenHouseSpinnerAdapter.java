package com.example.sep4_and.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sep4_and.R;
import com.example.sep4_and.model.GreenHouse;

import java.util.List;

public class GreenHouseSpinnerAdapter extends ArrayAdapter<GreenHouse> {

    public GreenHouseSpinnerAdapter(Context context, List<GreenHouse> greenHouses) {
        super(context, R.layout.spinner_item, greenHouses);
        setDropDownViewResource(R.layout.spinner_dropdown_item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, R.layout.spinner_item);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, R.layout.spinner_dropdown_item);
    }

    private View createViewFromResource(int position, @Nullable View convertView, @NonNull ViewGroup parent, int resource) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        TextView textView = view.findViewById(resource == R.layout.spinner_item ? R.id.spinnerItemTextView : R.id.spinnerDropdownItemTextView);
        GreenHouse greenHouse = getItem(position);
        if (greenHouse != null) {
            textView.setText(greenHouse.getName());
        }

        return view;
    }
}