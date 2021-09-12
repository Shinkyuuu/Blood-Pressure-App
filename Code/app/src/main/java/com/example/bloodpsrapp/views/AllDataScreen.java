package com.example.bloodpsrapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.bloodpsrapp.R;
import com.example.bloodpsrapp.adapters.AllDataRVAdapter;
import com.example.bloodpsrapp.models.RVReadings;
import com.example.bloodpsrapp.viewmodels.DataTableVM;

import java.util.ArrayList;

public class AllDataScreen extends AppCompatActivity {
    private static final String TAG = "TAG AllDataScreen ";
    private Button allDataBackBtn;
    private RecyclerView allDataRV;
    private DataTableVM dataTableVM;
    private ArrayList<RVReadings> listData;

    //On create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_data_screen);
        getWindow().setEnterTransition(null);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.newPurple));
        initialize();
    }

    //Initialize
    public void initialize() {
        allDataBackBtn = findViewById(R.id.drDataBackBtn);
        allDataRV = findViewById(R.id.allDataRV);
        dataTableVM = new ViewModelProvider(AllDataScreen.this).get(DataTableVM.class);
        listData = new ArrayList<>();
        populateAllDataRV();
        initRecyclerView();
        allDataBackBtnListener();

        Log.d(TAG, "initialize success");
    }

    //Initialize recyclerview
    public void initRecyclerView() {
        AllDataRVAdapter adapter = new AllDataRVAdapter(AllDataScreen.this, listData);
        allDataRV.setLayoutManager(new LinearLayoutManager(AllDataScreen.this));
        allDataRV.setAdapter(adapter);

        Log.d(TAG, "initRecyclerView success");
    }

    //Populate recyclerview
    public void populateAllDataRV() {
        listData = dataTableVM.loadDataFromAllDataTable();
        //dataTableVM.loadDataFromAllDataFile(listData);

        Log.d(TAG, "populateAllDataRV success");
    }

    public void allDataBackBtnListener() {
        allDataBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllDataScreen.this.finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}