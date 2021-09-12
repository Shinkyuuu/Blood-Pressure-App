package com.example.bloodpsrapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.bloodpsrapp.R;
import com.example.bloodpsrapp.adapters.DrDataRVAdapter;
import com.example.bloodpsrapp.models.RVSessionReadings;
import com.example.bloodpsrapp.viewmodels.DataTableVM;
import java.util.ArrayList;

public class DrDataScreen extends AppCompatActivity implements ArchiveDrDataDialog.ArchiveDrDataInterface {
    private static final String TAG = "TAG DrDataScreen ";
    private RecyclerView drDataRV;
    private Button copyBtn;
    private Button drDataBackBtn;
    private DrDataRVAdapter adapter;
    private DataTableVM dataTableVM;
    private ArrayList<RVSessionReadings> drListData;

    //On create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_data_screen);
        getWindow().setEnterTransition(null);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.newRed));
        initialize();
    }

    //Initialize
    public void initialize() {
        copyBtn = findViewById(R.id.copyBtn);
        drDataBackBtn = findViewById(R.id.drDataBackBtn);
        drDataRV = findViewById(R.id.drDataRV);
        dataTableVM = new ViewModelProvider(DrDataScreen.this).get(DataTableVM.class);
        drListData = new ArrayList<>();

        populateAllDataRV();
        initRecyclerView();
        copyBtnListener();
        DrDataBackBtnListener();

        Log.d(TAG, "initialize success");
    }

    //Initialize recyclerview
    public void initRecyclerView() {
        adapter = new DrDataRVAdapter(DrDataScreen.this, drListData);
        drDataRV.setLayoutManager(new LinearLayoutManager(DrDataScreen.this));
        drDataRV.setAdapter(adapter);

        Log.d(TAG, "initRecyclerView success");
    }

    //Populate recyclerview
    public void populateAllDataRV() {
        drListData = dataTableVM.loadDataFromDrDataTable();
        //dataTableVM.loadDataFromDrDataFile(drListData);

        Log.d(TAG, "populateAllDataRV success");
    }

    //copyBtn listener
    public void copyBtnListener() {
        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataTableVM.unsentDataEmpty()) {
                    Toast.makeText(DrDataScreen.this, "No data to copy", Toast.LENGTH_SHORT).show();
                } else {
                    copyDrDataToClipboard();
                    openArchiveDataDialog();
                }
            }
        });
    }

    //Load data from (DataTableVM) in copy form
    public void copyDrDataToClipboard() {
        ClipboardManager clipBoard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Blood Pressure Data", dataTableVM.getCopyDrData());
        clipBoard.setPrimaryClip(clip);

        Toast.makeText(DrDataScreen.this, "Saved to Clipboard", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "copyDrDataToClipboard success");
    }

    //Open ArchiveDataDialog
    public void openArchiveDataDialog() {
        ArchiveDrDataDialog archiveDrDataDialog = new ArchiveDrDataDialog(DrDataScreen.this);
        archiveDrDataDialog.show(getSupportFragmentManager(), "Data Sent Check?");
    }

    //drDataBackBtn listener
    public void DrDataBackBtnListener() {
        drDataBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrDataScreen.this.finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void update() {
        adapter.updateDrDataRV(dataTableVM.loadDataFromDrDataTable());
    }
}