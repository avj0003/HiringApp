package app.hiring.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;
import java.util.LinkedHashMap;
import java.util.List;
import app.hiring.HiringApplication;
import app.hiring.R;
import app.hiring.adapter.HiringExpandableListAdapter;
import app.hiring.model.GroupDataModel;
import app.hiring.viewmodels.HiringItemViewModel;

/*
 * activity/MainActivity.java - Launcher Activity
 * Functions: Checks if internet available, observe data from ViewModel, pass it to adapter
 */

public class MainActivity extends AppCompatActivity {

    final String TAG = getClass().getSimpleName();
    private ExpandableListView mExpandableListView;
    private ExpandableListAdapter mExpandableListViewAdapter;
    private HiringItemViewModel mExpandableListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mExpandableListView = findViewById(R.id.lvExp);
        mExpandableListViewModel = new ViewModelProvider(this).get(HiringItemViewModel.class);
        if(HiringApplication.getInstance().isNetworkAvailable()) {
            mExpandableListViewModel.getResponse().observe(this, new Observer<LinkedHashMap<String, List<GroupDataModel>>>() {
                @Override
                public void onChanged(LinkedHashMap<String, List<GroupDataModel>> listItems) {
                    if (listItems != null && !listItems.isEmpty()) {
                        Log.e(TAG, "observe onChanged()=" + listItems.size());
                        LiveData<LinkedHashMap<String, List<GroupDataModel>>> groupListData = mExpandableListViewModel.getResponse();
                        mExpandableListViewAdapter = new HiringExpandableListAdapter(getApplicationContext(), groupListData);
                        mExpandableListView.setAdapter(mExpandableListViewAdapter);
                    }
                }
            });

        } else {
            Toast.makeText(this, "Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
        }
    }
}