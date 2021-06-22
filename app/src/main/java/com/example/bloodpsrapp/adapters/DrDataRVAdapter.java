package com.example.bloodpsrapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bloodpsrapp.R;
import com.example.bloodpsrapp.models.RVSessionReadings;

import java.util.ArrayList;

public class DrDataRVAdapter extends RecyclerView.Adapter<DrDataRVAdapter.ViewHolder>  {
    private final ArrayList<RVSessionReadings> dataReadingsF;
    private final Context context;

    //Constructor
    public DrDataRVAdapter(Context context, ArrayList<RVSessionReadings> dataReadings) {
        dataReadingsF = dataReadings;
        this.context = context;
    }

    //Create the ViewHolder for a single data view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_layout, parent, false);

        return new ViewHolder(view);
    }

    //Bind data to data ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.drDateData.setText(dataReadingsF.get(position).getDate());
        holder.drDayData.setText(dataReadingsF.get(position).getDay());
        holder.drAMPMData.setText(dataReadingsF.get(position).getAmpm());
        holder.drSessionsData.setText(dataReadingsF.get(position).getCounter());
        holder.drBPData.setText(dataReadingsF.get(position).getSysAvg() + "/" + dataReadingsF.get(position).getDyAvg() + "-" + dataReadingsF.get(position).getHrAvg());

        switch (holder.getItemViewType()) {
            case 0:
                holder.dataLayoutBG.setBackground(ContextCompat.getDrawable(context, R.color.newLightRed));
                break;
            case 1:
                holder.dataLayoutBG.setBackground(ContextCompat.getDrawable(context, R.color.newDarkLightWhite));
                break;
        }
    }

    //Get amount of data
    @Override
    public int getItemCount() {
        return this.dataReadingsF.size();
    }

    //Get item view type
    @Override
    public int getItemViewType(int position) {
        //0 = sent
        //1 = darken the background
        //2 = do nuthin
        if (dataReadingsF.get(position).getSent().equals("yes")) {
            return 0;
        } else if (position % 2 == 0){
            return 1;
        }

        return 2;
    }

    //Update recyclerview
    public void updateDrDataRV(ArrayList<RVSessionReadings> readings) {
        dataReadingsF.clear();
        dataReadingsF.addAll(readings);
        this.notifyDataSetChanged();
    }

    //ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView drDateData;
        private final TextView drDayData;
        private final TextView drAMPMData;
        private final TextView drSessionsData;
        private final TextView drBPData;
        private final ConstraintLayout dataLayoutBG;

        //Constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.drDateData = itemView.findViewById(R.id.drDateData);
            this.drDayData = itemView.findViewById(R.id.drDayData);
            this.drAMPMData = itemView.findViewById(R.id.drAMPMData);
            this.drSessionsData = itemView.findViewById(R.id.drSessionsData);
            this.drBPData = itemView.findViewById(R.id.drBPData);
            this.dataLayoutBG = itemView.findViewById(R.id.dataLayoutBG);
        }
    }
}
