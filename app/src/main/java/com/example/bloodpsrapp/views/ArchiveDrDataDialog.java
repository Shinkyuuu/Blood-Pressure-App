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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.bloodpsrapp.R;
import com.example.bloodpsrapp.viewmodels.DataTableVM;

import java.util.Objects;

public class ArchiveDrDataDialog extends AppCompatDialogFragment {
    private final DataTableVM dataTableVM;
    private Button yesArchiveBtn;
    private Button noArchiveBtn;
    private AlertDialog dialog;
    ArchiveDrDataInterface archiveDrDataInterface;

    //Constructor
    public ArchiveDrDataDialog(ViewModelStoreOwner context) {
        dataTableVM = new ViewModelProvider(context).get(DataTableVM.class);
    }

    //On create
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_archive_dr_data, null);
        builder.setView(view);

        yesArchiveBtn = view.findViewById(R.id.yesArchiveBtn);
        noArchiveBtn = view.findViewById(R.id.noArchiveBtn);
        dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        yesArchiveBtnListener();
        noArchiveBtnListener();

        return dialog;
    }

    //yesArchiveBtn listener
    public void yesArchiveBtnListener() {
        yesArchiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataTableVM.updateSentData();
                archiveDrDataInterface.update();
                dialog.dismiss();
            }
        });
    }

    //noArchiveBtn listener
    public void noArchiveBtnListener() {
        noArchiveBtn.setOnClickListener(new View.OnClickListener() {
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
            archiveDrDataInterface = (ArchiveDrDataInterface) context;
        } catch (Exception e) {
            Log.e("TAG ArchiveDrDataDialog", "Exception: " + e);
        }
    }

    //FinishSessionInterface (Interface)
    public interface ArchiveDrDataInterface {
        void update();
    }
}
