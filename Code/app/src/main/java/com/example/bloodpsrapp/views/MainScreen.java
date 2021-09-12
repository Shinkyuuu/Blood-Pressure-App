package com.example.bloodpsrapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.example.bloodpsrapp.R;
import com.example.bloodpsrapp.models.SessionReadings;
import com.example.bloodpsrapp.viewmodels.DataTableVM;
import com.example.bloodpsrapp.viewmodels.IntegerValueFormatter;
import com.example.bloodpsrapp.viewmodels.SessionVM;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.io.File;
import java.util.ArrayList;

public class MainScreen extends AppCompatActivity implements ArchiveDrDataDialog.ArchiveDrDataInterface {
    private static final String TAG = "TAG MainScreen ";
    private ToggleButton ampmSwitch;
    private Button infoBtn;
    private Button startSessionBtn;
    private Button viewAllDataBtn;
    private Button viewDrDataBtn;
    private Button sendDrDataBtn;
    private LineChart dataChart;
    private  SessionVM sessionVM;
    private DataTableVM dataTableVM;
    private Animation fadeIn;
    private Animation fadeOut;

    //On create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        initialize();

    }

    //Initialize
    public void initialize() {
        ampmSwitch = findViewById(R.id.ampmSwitch);
        infoBtn = findViewById(R.id.infoBtn);
        startSessionBtn = findViewById(R.id.startSessionBtn);
        viewAllDataBtn = findViewById(R.id.viewAllDataBtn);
        viewDrDataBtn = findViewById(R.id.viewDrDataBtn);
        sendDrDataBtn = findViewById(R.id.sendDrDataBtn);
        dataChart = findViewById(R.id.dataChart);
        sessionVM = new ViewModelProvider(MainScreen.this).get(SessionVM.class);
        dataTableVM = new ViewModelProvider(MainScreen.this).get(DataTableVM.class);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fragment_fade_enter);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fragment_fade_exit);
        fadeIn.setDuration(250);
        fadeIn.setFillAfter(true);
        fadeOut.setDuration(250);
        fadeOut.setFillAfter(true);

        initChart();
        ampmSwitchListener();
        startSessionBtnListener();
        viewAllDataBtnListener();
        viewDrDataBtnListener();
        sendDrDataBtnListener();
        infoBtnListener();

        Log.d(TAG, "initialize success");
    }

    //Initialize dataChart
    public void initChart()  {
        dataChart.setTouchEnabled(false);
        dataChart.setDragEnabled(false);
        dataChart.setScaleEnabled(false);
        dataChart.setExtraTopOffset(30);
        dataChart.setData(getAMData());

        dataChart.getDescription().setEnabled(false);
        dataChart.getLegend().setEnabled(false);
        dataChart.getAxisRight().setDrawLabels(false);
        dataChart.getAxisLeft().setDrawLabels(false);
        dataChart.getXAxis().setDrawLabels(false);
        dataChart.getXAxis().setDrawGridLines(false);
        dataChart.getXAxis().setDrawAxisLine(false);
        dataChart.getAxisRight().setDrawGridLines(false);
        dataChart.getAxisLeft().setGridColor(ContextCompat.getColor(this, R.color.newDarkLightWhite));
        dataChart.getAxisLeft().setGridLineWidth(3f);
        dataChart.getAxisLeft().setDrawLimitLinesBehindData(true);

        LimitLine limitLine = new LimitLine(200f, "200");
        limitLine.setLineWidth(4);
        limitLine.setLineColor(ContextCompat.getColor(this, R.color.limitLineRed));
        limitLine.setTextColor(ContextCompat.getColor(this, R.color.mainBG));
        dataChart.getAxisLeft().addLimitLine(limitLine);

        Log.d(TAG, "initChart success");
    }

    //Get AM Data for dataChart
    public LineData getAMData() {
        boolean drawValue = false;
        ArrayList<Entry> data = new ArrayList<>();
        ArrayList<Entry> lastPoint = new ArrayList<>();
        ArrayList<Entry> dummyData = new ArrayList<>();

        dataTableVM.getAMBPDataSQL(data, lastPoint, dummyData);
        //dataTableVM.getAMBPData(data, lastPoint, dummyData);

        LineDataSet lineDataSet = new LineDataSet(data, "Data Set");
        LineDataSet lastPointLine = new LineDataSet(lastPoint, "Last point");
        LineDataSet dummyDataSet = new LineDataSet(dummyData, "Last point");
        if (!lastPoint.isEmpty()) {
            drawValue = true;
        }

        Log.d(TAG, "getAMData success");
        return setLineProperties(lineDataSet, lastPointLine, dummyDataSet, drawValue);
    }

    //Get PM Data for dataChart
    public LineData getPMData() {
        boolean drawValue = false;
        ArrayList<Entry> data = new ArrayList<>();
        ArrayList<Entry> lastPoint = new ArrayList<>();
        ArrayList<Entry> dummyData = new ArrayList<>();

        dataTableVM.getPMBPDataSQL(data, lastPoint, dummyData);
        //dataTableVM.getPMBPData(data, lastPoint, dummyData);

        LineDataSet lineDataSet = new LineDataSet(data, "Data Set");
        LineDataSet lastPointLine = new LineDataSet(lastPoint, "Last point");
        LineDataSet dummyDataSet = new LineDataSet(dummyData, "Last point");

        if (!lastPoint.isEmpty()) {
            drawValue = true;
        }

        Log.d(TAG, "getPMData success");
        return setLineProperties(lineDataSet, lastPointLine, dummyDataSet, drawValue);
    }

    //Draw dataSet lines
    public LineData setLineProperties(LineDataSet lineDataSet, LineDataSet lastPointLine, LineDataSet dummyDataSet, Boolean drawValue) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        lineDataSet.setLineWidth(7);
        lineDataSet.setDrawValues(false);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCubicIntensity(0.1f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setCircleRadius(3.5f);
        lineDataSet.setFillAlpha(110);

        if (drawValue && (lastPointLine.getEntryForIndex(0).getY() > 199)) {
            lineDataSet.setColor(ContextCompat.getColor(this, R.color.graphRed));
            lineDataSet.setCircleColor(ContextCompat.getColor(this, R.color.graphRed));
            lastPointLine.setCircleColor(ContextCompat.getColor(this, R.color.graphRed));
        } else {
            lineDataSet.setColor(ContextCompat.getColor(this, R.color.graphBlue));
            lineDataSet.setCircleColor(ContextCompat.getColor(this, R.color.graphBlue));
            lastPointLine.setCircleColor(ContextCompat.getColor(this, R.color.graphBlue));
        }

        lastPointLine.setFillAlpha(110);
        lastPointLine.setDrawCircleHole(false);
        lastPointLine.setCircleRadius(9);
        lastPointLine.setValueTextColor(ContextCompat.getColor(this, R.color.newLightBlack));
        lastPointLine.setValueTypeface(ResourcesCompat.getFont(this, R.font.poppins_medium));
        lastPointLine.setValueFormatter(new IntegerValueFormatter());
        lastPointLine.setValueTextSize(28);
        lastPointLine.setDrawValues(drawValue);
        //lastPointLine.setValueFormatter(new DefaultValueFormatter(0));

        dummyDataSet.enableDashedLine(0, 1, 0);
        dummyDataSet.setDrawCircles(false);
        dummyDataSet.setDrawValues(false);

        dataSets.add(lineDataSet);
        dataSets.add(lastPointLine);
        dataSets.add(dummyDataSet);

        Log.d(TAG, "setLineProperties success");
        return new LineData(dataSets);
    }

    //ampmSwitch listener
    public void ampmSwitchListener() {
        ampmSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataChart.startAnimation(fadeOut);

                if (ampmSwitch.isChecked()) {
                    ampmSwitch.setTextColor(ContextCompat.getColor(MainScreen.this, R.color.newDarkBlue));
                    ampmSwitch.setBackground(ContextCompat.getDrawable(MainScreen.this, R.drawable.main_button_night_bg));
                    infoBtn.setTextColor(ContextCompat.getColor(MainScreen.this, R.color.newDarkBlue));
                    ampmSwitch.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(MainScreen.this, R.drawable.image_circle), null);
                    infoBtn.setBackground(ContextCompat.getDrawable(MainScreen.this, R.drawable.main_button_night_bg));
                    startSessionBtn.setBackground(ContextCompat.getDrawable(MainScreen.this, R.drawable.main_button_night_bg));
                    dataChart.setData(getPMData());
                } else {
                    ampmSwitch.setTextColor(ContextCompat.getColor(MainScreen.this, R.color.newDarkRed));
                    ampmSwitch.setBackground(ContextCompat.getDrawable(MainScreen.this, R.drawable.main_button_morning_bg));
                    ampmSwitch.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(MainScreen.this, R.drawable.image_circle), null, null, null);
                    infoBtn.setTextColor(ContextCompat.getColor(MainScreen.this, R.color.newDarkRed));
                    infoBtn.setBackground(ContextCompat.getDrawable(MainScreen.this, R.drawable.main_button_morning_bg));
                    startSessionBtn.setBackground(ContextCompat.getDrawable(MainScreen.this, R.drawable.main_button_morning_bg));
                    dataChart.setData(getAMData());
                }

                dataChart.startAnimation(fadeIn);
                dataChart.invalidate();
            }
        });
    }

    //startSessionBtn listener
    public void startSessionBtnListener () {
        startSessionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionVM.storeSessionReadings(MainScreen.this, new SessionReadings());
                finishActivityFade(SessionScreen.class);
                MainScreen.this.finish();
            }
        });
    }

    //ViewAllDataBtn listener
    public void viewAllDataBtnListener() {
        viewAllDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivitySlide(AllDataScreen.class);
            }
        });
    }

    //ViewDrDataBtn listener
    public void viewDrDataBtnListener() {
        viewDrDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivitySlide(DrDataScreen.class);
            }
        });
    }

    //SendDrDataBtn listener
    public void sendDrDataBtnListener() {
        sendDrDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataTableVM.loadUnsentDataToFile();
                sendEmail();
            }
        });
    }

    //Start image_email intent
    public void sendEmail() {
        Intent sendEmailIntent = new Intent(android.content.Intent.ACTION_SEND);
        String fileName = "drData.txt";
        File file = new File(getExternalFilesDir("drData"), fileName);
        if (!file.exists() || !file.canRead()) {
            Log.e(TAG, "sendEmail failure");
            return;
        } else if (dataTableVM.unsentDataEmpty()) {
            Toast.makeText(this, "No data to load", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "sendEmail success");
            return;
        } else {
            openArchiveDataDialog();
        }

        Uri path = FileProvider.getUriForFile(MainScreen.this, getPackageName() + ".provider", file);
        sendEmailIntent.putExtra(Intent.EXTRA_STREAM, path);
        sendEmailIntent.setType("plaint/text");
        startActivity(sendEmailIntent);

        Log.d(TAG, file.toString());
        Log.d(TAG, "sendEmail success");
    }

    //Open ArchiveDataDialog
    public void openArchiveDataDialog() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArchiveDrDataDialog archiveDrDataDialog = new ArchiveDrDataDialog(MainScreen.this);
                archiveDrDataDialog.show(getSupportFragmentManager(), "Data Sent Check?");
            }
        }, 1000);
    }

    public void infoBtnListener() {
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GraphInfoDialog graphInfoDialog = new GraphInfoDialog();
                graphInfoDialog.show(getSupportFragmentManager(), "Graph Info Dialog");
            }
        });
    }

    public void finishActivityFade(Class toActivity) {
        Intent startActivity = new Intent(this, toActivity);
        startActivity(startActivity);
        overridePendingTransition(0, R.anim.fragment_fade_exit);
    }

    public void finishActivitySlide(Class toActivity) {
        Intent startActivity = new Intent(this, toActivity);
        startActivity(startActivity);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void update() {
        //Nothing to Update
    }
}