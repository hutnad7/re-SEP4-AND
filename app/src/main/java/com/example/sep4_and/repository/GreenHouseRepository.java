package com.example.sep4_and.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.sep4_and.dao.AppDatabase;
import com.example.sep4_and.dao.GreenHouseDao;
import com.example.sep4_and.model.DbCrossReference.GreenHouseUserCrossRef;
import com.example.sep4_and.model.DbCrossReference.GreenHouseWithUsers;
import com.example.sep4_and.model.GreenHouse;
import com.example.sep4_and.model.GreenHouseDetailed;
import com.example.sep4_and.network.RetrofitInstance;
import com.example.sep4_and.network.api.GreenHouseApi;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Different class not compatible with one created by Huti
public class GreenHouseRepository {
    private GreenHouseDao greenHouseDao;
    private ExecutorService executorService;
    private GreenHouseApi greenHouseApi;

    private MutableLiveData<List<GreenHouseDetailed>> greenhouseList;

    public GreenHouseRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        greenHouseDao = db.greenHouseDao();
        executorService = Executors.newSingleThreadExecutor();
        greenHouseApi = RetrofitInstance.getClient("").create(GreenHouseApi.class);

        greenhouseList = new MutableLiveData<>();
    }

    public LiveData<List<GreenHouseDetailed>> getUserGreenhouses(int userId) {
        fetchGreenhouses(userId);
        return greenhouseList;
    }

    private void fetchGreenhouses(int userId) {
        Call<List<GreenHouseDetailed>> call = greenHouseApi.getUserGreenhouses(userId);

        call.enqueue(new Callback<List<GreenHouseDetailed>>() {
            @Override
            public void onResponse(Call<List<GreenHouseDetailed>> call, Response<List<GreenHouseDetailed>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    greenhouseList.setValue(response.body());
                } else {
                    greenhouseList.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<GreenHouseDetailed>> call, Throwable t) {
                // Handle failure case
            }
        });
    }

    public LiveData<GreenHouseDetailed> getGreenHouseById(int id) {
        MutableLiveData<GreenHouseDetailed> greenHouseLiveData = new MutableLiveData<>();

        greenHouseApi.getGreenhouseById(id).enqueue(new Callback<GreenHouseDetailed>() {
            @Override
            public void onResponse(Call<GreenHouseDetailed> call, Response<GreenHouseDetailed> response) {
                if (response.isSuccessful()) {
                    greenHouseLiveData.setValue(response.body());
                } else {
                    greenHouseLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<GreenHouseDetailed> call, Throwable throwable) {
                greenHouseLiveData.setValue(null);
            }
        });

        return greenHouseLiveData;
    }

    public void insert(GreenHouse greenHouse) {
        executorService.execute(() -> greenHouseDao.insert(greenHouse));
    }

    public void delete(GreenHouse greenHouse) {
        executorService.execute(() -> greenHouseDao.delete(greenHouse));
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