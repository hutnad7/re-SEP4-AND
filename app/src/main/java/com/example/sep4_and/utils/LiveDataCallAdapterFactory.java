package com.example.sep4_and.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != LiveData.class) {
            return null;
        }
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        return new LiveDataCallAdapter<>(observableType);
    }

    private static final class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<R>> {
        private final Type responseType;

        LiveDataCallAdapter(Type responseType) {
            this.responseType = responseType;
        }

        @Override
        public Type responseType() {
            return responseType;
        }

        @Override
        public LiveData<R> adapt(Call<R> call) {
            MutableLiveData<R> liveDataResponse = new MutableLiveData<>();
            call.enqueue(new Callback<R>() {
                @Override
                public void onResponse(Call<R> call, Response<R> response) {
                    if (response.isSuccessful()) {
                        liveDataResponse.postValue(response.body());
                    } else {
                        liveDataResponse.postValue(null);
                    }
                }

                @Override
                public void onFailure(Call<R> call, Throwable t) {
                    liveDataResponse.postValue(null);
                }
            });
            return liveDataResponse;
        }
    }
}