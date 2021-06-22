package com.example.bloodpsrapp.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.bloodpsrapp.R;

import java.util.Objects;

public class GraphInfoDialog extends AppCompatDialogFragment {
    private Button okInfoBtn;
    private AlertDialog dialog;

    //On create
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_graph_info, null);
        okInfoBtn = view.findViewById(R.id.okInfoBtn);

        builder.setView(view);


        dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        okInfoBtnListener();
        return dialog;
    }

    //okInfoBtnListener
    public void okInfoBtnListener() {
        okInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
