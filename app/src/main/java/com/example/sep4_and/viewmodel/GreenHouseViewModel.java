package com.example.sep4_and.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4_and.model.GreenHouseDetailed;
import com.example.sep4_and.repository.GreenHouseRepository;
import com.example.sep4_and.utils.TokenManager;

import java.util.List;

public class GreenHouseViewModel extends AndroidViewModel {
    private final GreenHouseRepository repository;
    private LiveData<List<GreenHouseDetailed>> greenhouses;

    public GreenHouseViewModel(@NonNull Application application) {
        super(application);
        repository = new GreenHouseRepository(application);
        greenhouses = repository.getUserGreenhouses(Integer.parseInt(TokenManager.getToken()));
    }

    public LiveData<List<GreenHouseDetailed>> getGreenhouses() {
        greenhouses = repository.getUserGreenhouses(Integer.parseInt(TokenManager.getToken()));

        return greenhouses;
    }

    public LiveData<GreenHouseDetailed> getGreenHouseById(int id) {
        return repository.getGreenHouseById(id);
    }
}