package app.hiring.repo;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import app.hiring.HiringApplication;
import app.hiring.common.ApiInterface;
import app.hiring.common.Util;
import app.hiring.model.GroupDataModel;
import app.hiring.model.ResponseDataModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * repo/HiringRepo.java
 * Functions: calls ApiInterface and Retrofit to get data from json
 *            Util.convertJSONToHashMap() -> from json to LinkedHashMap
 *            Util.sortHashMapByKey() -> sort by key and value
 */

public class HiringRepo {

    private final String TAG = getClass().getSimpleName();
    LinkedHashMap<String, List<GroupDataModel>> hashMapData = new LinkedHashMap<>();
    LinkedHashMap<String, List<GroupDataModel>> hashMapSorted = new LinkedHashMap<>();

    public MutableLiveData<LinkedHashMap<String, List<GroupDataModel>>> requestResponse() {
        final MutableLiveData<LinkedHashMap<String, List<GroupDataModel>>> mutableLiveData = new MutableLiveData<>();

        ApiInterface apiService =
                HiringApplication.getRetrofitClient().create(ApiInterface.class);

        apiService.getJSONResponse().enqueue(new Callback<List<ResponseDataModel>>() {
            @Override
            public void onResponse(Call<List<ResponseDataModel>> call, Response<List<ResponseDataModel>> response) {
                Log.e(TAG, "response="+response );
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "response.size="+response.body().size());
                    String json = new Gson().toJson(response.body());
                    hashMapData = Util.convertJSONToHashMap(json);
                    hashMapSorted = Util.sortHashMapByKey(hashMapData);
                    mutableLiveData.setValue(hashMapSorted);
                }
            }

            @Override
            public void onFailure(Call<List<ResponseDataModel>> call, Throwable t) {
                Log.e(TAG, "onFailure" + call.toString());
            }
        });

        return mutableLiveData;
    }
}