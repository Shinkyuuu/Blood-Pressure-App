package com.example.bloodpsrapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.bloodpsrapp.R;
import com.example.bloodpsrapp.viewmodels.SessionVM;

public class LoadingScreen extends AppCompatActivity {
    private ConstraintLayout loadingBG;
    private SessionVM sessionVM;

    //On create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                , WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_loading_screen);
        loadingBG = findViewById(R.id.loadingBG);

        backgroundAnimation();
        loadStartup();
    }

    //Set background animation
    public void backgroundAnimation() {
        AnimationDrawable animationDrawable = (AnimationDrawable) loadingBG.getBackground();
        animationDrawable.setEnterFadeDuration(300);
        animationDrawable.setExitFadeDuration(300);
        animationDrawable.start();
    }

    //Loads delayed loginActivity (Also checks to see if session is still in session)
    public void loadStartup() {
        sessionVM = new ViewModelProvider(LoadingScreen.this).get(SessionVM.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sessionVM.loadSessionReadings(LoadingScreen.this) == null) {
                    finishActivity(MainScreen.class);
                    LoadingScreen.this.finish();
                     return;
                }

                finishActivity(SessionScreen.class);
                LoadingScreen.this.finish();
            }
        }, 700);
    }

    //Finish activity
    public void finishActivity(Class toActivity) {
        Intent startActivity = new Intent(this, toActivity);
        startActivity(startActivity);
        overridePendingTransition(0, R.anim.fragment_fade_exit);
    }
}