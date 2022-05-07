package app.hiring.viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.LinkedHashMap;
import java.util.List;

import app.hiring.model.GroupDataModel;
import app.hiring.repo.HiringRepo;

/*
 * viewmodel/HiringItemViewModel.java - AndroidViewModel
 * Functions: get data from HiringRepo
 */

public class HiringItemViewModel extends AndroidViewModel {

    private HiringRepo hiringRepo;
    private MutableLiveData<LinkedHashMap<String, List<GroupDataModel>>> mutableLiveData;

    public HiringItemViewModel(@NonNull Application application) {
        super(application);
        hiringRepo = new HiringRepo();
    }

    public LiveData<LinkedHashMap<String, List<GroupDataModel>>> getResponse() {
        if(mutableLiveData == null){
            mutableLiveData = hiringRepo.requestResponse();
        }
        return mutableLiveData;
    }

}
