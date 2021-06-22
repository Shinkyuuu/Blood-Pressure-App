package com.example.bloodpsrapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import com.example.bloodpsrapp.R;
import com.example.bloodpsrapp.adapters.PrevSessionDataAdapter;
import com.example.bloodpsrapp.models.Readings;
import com.example.bloodpsrapp.models.SessionReadings;
import com.example.bloodpsrapp.viewmodels.DataTableVM;
import com.example.bloodpsrapp.viewmodels.SessionVM;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class SessionScreen extends AppCompatActivity
                                                implements AreYouSureDialog.AreYouSureInterface
                                                , PrevSessionDataAdapter.PrevDataClickListener
                                                , EditSessionReadingDialog.EditSessionInterface {

    private static final String TAG = "TAG SessionScreen ";
    private ConstraintLayout sessionBG;
    private SessionVM sessionVM;
    private DataTableVM dataTableVM;
    private TextInputLayout enterSysLayout;
    private TextInputLayout enterDyLayout;
    private TextInputLayout enterHrLayout;
    private TextInputEditText enterSYS;
    private TextInputEditText enterDY;
    private TextInputEditText enterHR;
    private Button okBtn;
    private Button endSessionBtn;
    private RecyclerView prevSessionDataRV;
    private PrevSessionDataAdapter adapter;
    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback;
    private SessionReadings sessionReadings;

    //On create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_screen);
        getWindow().setEnterTransition(null);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        initialize();
    }

    //Initialize
    public void initialize() {
        sessionBG = findViewById(R.id.sessionBG);
        enterSysLayout = findViewById(R.id.enterSysLayout);
        enterDyLayout = findViewById(R.id.enterDyLayout);
        enterHrLayout = findViewById(R.id.enterHrLayout);
        enterSYS = findViewById(R.id.enterSYS);
        enterDY = findViewById(R.id.enterDY);
        enterHR = findViewById(R.id.enterHR);
        okBtn = findViewById(R.id.okBtn);
        endSessionBtn = findViewById(R.id.endSessionBtn);
        prevSessionDataRV = findViewById(R.id.prevSessionDataRV);
        sessionVM = new ViewModelProvider(SessionScreen.this).get(SessionVM.class);
        dataTableVM = new ViewModelProvider(SessionScreen.this).get(DataTableVM.class);
        View bottomSheet = findViewById(R.id.bottomSheet);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        sessionReadings = sessionVM.loadSessionReadings(SessionScreen.this);

        backgroundAnimation();
        okBtnListener();
        endSessionBtnListener();
        initItemTouchHelperCallback();
        initRecyclerView();

        Log.d(TAG, "initialize success");
    }

    //Set background animation
    public void backgroundAnimation() {
        AnimationDrawable animationDrawable = (AnimationDrawable) sessionBG.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(12000);
        animationDrawable.start();
    }

    //Initialize recyclerview
    public void initRecyclerView() {
        adapter = new PrevSessionDataAdapter(SessionScreen.this, sessionReadings.getReadings());
        prevSessionDataRV.setLayoutManager(new LinearLayoutManager(SessionScreen.this));
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(prevSessionDataRV);
        prevSessionDataRV.setAdapter(adapter);

        Log.d(TAG, "initRecyclerView success");
    }

    //Check for empty TextInputs
    public boolean validateDataEntryText() {
        boolean allGood = true;
        if (Objects.requireNonNull(enterSYS.getText()).toString().trim().isEmpty()) {
            enterSysLayout.setError("Enter Systolic Pressure");
            allGood = false;
        }

        if (Objects.requireNonNull(enterDY.getText()).toString().trim().isEmpty()) {
            enterDyLayout.setError("Enter Diastolic Pressure");
            allGood = false;
        }

        if (Objects.requireNonNull(enterHR.getText()).toString().trim().isEmpty()) {
            enterHrLayout.setError("Enter Pulse");
            allGood = false;
        }

        Log.d(TAG, "validateDataEntryText success");
        return allGood;
    }

    //okBtn listener
    public void okBtnListener() {
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateDataEntryText()) {
                    addReading();
                    adapter.updatePrevDataRV(sessionVM.loadSessionReadings(SessionScreen.this).getReadings());
                    Toast.makeText(SessionScreen.this, "Added Readings!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //add reading to VM
    public void addReading() {
        Readings reading = new Readings(Integer.parseInt(Objects.requireNonNull(enterSYS.getText()).toString().trim())
                                                                    , Integer.parseInt(Objects.requireNonNull(enterDY.getText()).toString().trim())
                                                                    , Integer.parseInt(Objects.requireNonNull(enterHR.getText()).toString().trim()));

        sessionReadings.getReadings().add(reading);
        sessionVM.storeSessionReadings(SessionScreen.this, sessionReadings);

        enterSysLayout.setErrorEnabled(false);
        enterDyLayout.setErrorEnabled(false);
        enterHrLayout.setErrorEnabled(false);
        enterSYS.setText("");
        enterDY.setText("");
        enterHR.setText("");

        Log.d(TAG, "addReading success");
    }

    //Open EditSessionDialog
    @Override
    public void openEditSessionDialog(int position) {
        EditSessionReadingDialog editSessionReadingDialog = new EditSessionReadingDialog(sessionVM.loadSessionReadings(SessionScreen.this), position);
        editSessionReadingDialog.show(getSupportFragmentManager(), "Edit Session Reading!");

        Log.d(TAG, "openEditSessionDialog success");
    }

    public void initItemTouchHelperCallback() {
        itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                sessionReadings.getReadings().remove(viewHolder.getAbsoluteAdapterPosition());
                updateReading(sessionReadings);
            }
        };
    }

    //Update editReading recyclerview
    @Override
    public void updateReading(SessionReadings sessionReadings) {
        sessionVM.storeSessionReadings(SessionScreen.this, sessionReadings);
        adapter.updatePrevDataRV(sessionVM.loadSessionReadings(SessionScreen.this).getReadings());

        Log.d(TAG, "updateReading success");
    }

    //endSessionBtn listener
    public void endSessionBtnListener() {
        endSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAreYouSureDialog();
            }
        });
    }

    //Open openAreYouSureDialog
    public void openAreYouSureDialog() {
        AreYouSureDialog finishSessionCheckDialog = new AreYouSureDialog();
        finishSessionCheckDialog.show(getSupportFragmentManager(), "Finish Session Check?");

        Log.d(TAG, "openFinishSessionDialog success");
    }

    //Add All and Dr data to VM
    public void addDataToDB() {
        //SessionReadings sessionReadings = sessionVM.loadSessionReadings(SessionScreen.this);

        if (sessionReadings.getEntries() != 0) {
            //dataTableVM.addDataToAllDataFile(sessionReadings);
            //dataTableVM.addDataToDrDataFile(sessionReadings);
            dataTableVM.addDataToAllDataTable(sessionReadings);
            dataTableVM.addDataToDrDataTable(sessionReadings);
        } else {
            Log.d(TAG, "Left empty session");
        }

        Log.d(TAG, "addDataToDB success");
    }

    //Clear session when finished
    public void clearSessionReadings() {
        sessionVM.clearSessionReadings(SessionScreen.this);

        Log.d(TAG, "clearSessionReadings success");
    }

    //endSession
    @Override
    public void endActivity() {
        addDataToDB();
        clearSessionReadings();
        finishActivity(MainScreen.class);

        Log.d(TAG, "endSession success");
    }

    //Finish activity
    public void finishActivity(Class toActivity) {
        Intent startActivity = new Intent(this, toActivity);
        startActivity(startActivity);
        overridePendingTransition(0, R.anim.fragment_fade_exit);
        SessionScreen.this.finish();
        Log.d(TAG, "finishActivity success");
    }
}