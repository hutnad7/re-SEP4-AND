package com.example.sep4_and.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4_and.dao.AppDatabase;
import com.example.sep4_and.dao.GreenHouseDao;

import com.example.sep4_and.model.GreenHouse;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Different class not compatible with one created by Huti
public class GreenHouseRepository {
    private GreenHouseDao greenHouseDao;
    private ExecutorService executorService;

    public GreenHouseRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        greenHouseDao = db.greenHouseDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<Long> insert(GreenHouse greenHouse) {
        MutableLiveData<Long> result = new MutableLiveData<>();
        executorService.execute(() -> {
            long id = greenHouseDao.insert(greenHouse);
            result.postValue(id);
        });
        return result;
    }

    public LiveData<List<GreenHouse>> getGreenHousesByUserId(int userId) {
        return greenHouseDao.getGreenHousesByUserId(userId);
    }

    public void delete(GreenHouse greenHouse) {
        executorService.execute(() -> {
            greenHouseDao.delete(greenHouse);
        });
    }

    public LiveData<GreenHouse> getGreenHouseById(int greenHouseId) {
        return greenHouseDao.getGreenHouseById(greenHouseId);
    }
}