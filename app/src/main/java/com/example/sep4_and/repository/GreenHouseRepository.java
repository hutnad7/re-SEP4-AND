package com.example.sep4_and.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.sep4_and.dao.AppDatabase;
import com.example.sep4_and.dao.GreenHouseDao;
import com.example.sep4_and.model.DbCrossReference.GreenHouseUserCrossRef;
import com.example.sep4_and.model.DbCrossReference.GreenHouseWithUsers;
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

    public void insert(GreenHouse greenHouse) {
        executorService.execute(() -> greenHouseDao.insert(greenHouse));
    }

    public void insertGreenHouseUserCrossRef(GreenHouseUserCrossRef crossRef) {
        executorService.execute(() -> greenHouseDao.insertGreenHouseUserCrossRef(crossRef));
    }
    public LiveData<List<GreenHouse>> getAllGreenHouses() {
        return greenHouseDao.getAllGreenHouses();
    }
    public LiveData<List<GreenHouseWithUsers>> getGreenHousesWithUsers() {
        return greenHouseDao.getGreenHousesWithUsers();
    }
}