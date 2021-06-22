package com.example.bloodpsrapp.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.example.bloodpsrapp.R;
import com.example.bloodpsrapp.models.Readings;
import com.example.bloodpsrapp.models.SessionReadings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Objects;

public class EditSessionReadingDialog extends AppCompatDialogFragment {
    private TextInputLayout editSysLayout;
    private TextInputLayout editDyLayout;
    private TextInputLayout editHrLayout;
    private TextInputEditText editSYS;
    private TextInputEditText editDY;
    private TextInputEditText editHR;
    private Button editDoneBtn;
    private Button editCancelBtn;
    private AlertDialog dialog;
    private final SessionReadings sessionReadings;
    private final Readings reading;
    private EditSessionInterface editSessionInterface;

    public EditSessionReadingDialog(SessionReadings sessionReadings, int position) {
        this.sessionReadings = sessionReadings;
        this.reading = sessionReadings.getReadings().get(position);
    }

    //On create
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_session_reading, null);
        builder.setView(view);

        editSysLayout = view.findViewById(R.id.editSysLayout);
        editDyLayout = view.findViewById(R.id.editDyLayout);
        editHrLayout = view.findViewById(R.id.editHrLayout);
        editSYS = view.findViewById(R.id.editSYS);
        editDY = view.findViewById(R.id.editDY);
        editHR = view.findViewById(R.id.editHR);
        editDoneBtn = view.findViewById(R.id.editDoneBtn);
        editCancelBtn = view.findViewById(R.id.editCancelBtn);
        editSYS.setText(String.valueOf(reading.getSYS()));
        editDY.setText(String.valueOf(reading.getDY()));
        editHR.setText(String.valueOf(reading.getHR()));


        dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editDoneBtnListener();
        editCancelBtnListener();

        return dialog;
    }

    //Check for empty TextInputs
    public boolean validateDataEntryText() {
        boolean allGood = true;
        if (Objects.requireNonNull(editSYS.getText()).toString().trim().isEmpty()) {
            editSysLayout.setError("Enter Systolic Pressure");
            allGood = false;
        }

        if (Objects.requireNonNull(editDY.getText()).toString().trim().isEmpty()) {
            editDyLayout.setError("Enter Diastolic Pressure");
            allGood = false;
        }

        if (Objects.requireNonNull(editHR.getText()).toString().trim().isEmpty()) {
            editHrLayout.setError("Enter Pulse");
            allGood = false;
        }

        return allGood;
    }

    //editDoneBtn listener
    public void editDoneBtnListener() {
        editDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateDataEntryText()) {
                    reading.setSYS(Integer.parseInt(Objects.requireNonNull(editSYS.getText()).toString().trim()));
                    reading.setDY(Integer.parseInt(Objects.requireNonNull(editDY.getText()).toString().trim()));
                    reading.setHR(Integer.parseInt(Objects.requireNonNull(editHR.getText()).toString().trim()));

                    editSessionInterface.updateReading(sessionReadings);
                    dialog.dismiss();
                }
            }
        });
    }

    //editCancelBtn listener
    public void editCancelBtnListener() {
        editCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //On attach
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            editSessionInterface = (EditSessionInterface) context;
        } catch (Exception e) {
            Log.e("TAG EditSessionReadingDialog ", "Exception: " + e);
        }
    }

    //EditSessionInterface (Interface)
    public interface EditSessionInterface {
        void updateReading(SessionReadings sessionReadings);
    }
}
