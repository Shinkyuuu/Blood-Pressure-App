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
import java.util.Objects;

public class AreYouSureDialog extends AppCompatDialogFragment {
    AreYouSureInterface finishSessionInterface;
    private Button yesSessionEndBtn;
    private Button noSessionEndBtn;
    private AlertDialog dialog;

    //On create
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_finish_session_check, null);

        builder.setView(view);

        yesSessionEndBtn = view.findViewById(R.id.yesSessionEndBtn);
        noSessionEndBtn = view.findViewById(R.id.noSessionEndBtn);

        dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        yesSessionEndBtnListener();
        noSessionEndBtnListener();

        return dialog;

    }

    //yesSessionEndBtn listener
    public void  yesSessionEndBtnListener() {
        yesSessionEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishSessionInterface.endActivity();
            }
        });
    }

    //noSessionEndBtn listener
    public void noSessionEndBtnListener() {
        noSessionEndBtn.setOnClickListener(new View.OnClickListener() {
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
            finishSessionInterface = (AreYouSureInterface) context;
        } catch (Exception e) {
            Log.e("TAG FinnishSessionCheckDialog ", "Exception: " + e);
        }
    }

    //FinishSessionInterface (Interface)
    public interface AreYouSureInterface {
        void endActivity();
    }
}
