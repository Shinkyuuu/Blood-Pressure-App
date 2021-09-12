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
import com.example.bloodpsrapp.models.RVReadings;

import java.util.ArrayList;

public class AllDataRVAdapter extends RecyclerView.Adapter<AllDataRVAdapter.ViewHolder> {
    private final ArrayList<RVReadings> dataReadings;
    private final Context context;

    //Constructor
    public AllDataRVAdapter(Context context, ArrayList<RVReadings> dataReadings) {
        this.dataReadings = dataReadings;
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
    public void onBindViewHolder(@NonNull AllDataRVAdapter.ViewHolder holder, int position) {
        holder.drDateData.setText(dataReadings.get(position).getDate());
        holder.drDayData.setText(dataReadings.get(position).getDay());
        holder.drTimeData.setText(dataReadings.get(position).getTime());
        holder.drAMPMData.setText(dataReadings.get(position).getAmpm());
        holder.drBPData.setText(dataReadings.get(position).getSys() + "/" + dataReadings.get(position).getDy() + "-" + dataReadings.get(position).getHr());

        if (holder.getItemViewType() == 0) {
            holder.dataLayoutBG.setBackground(ContextCompat.getDrawable(context, R.color.newDarkLightWhite));
        }
    }

    //Get item view type
    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return 0;
        }

        return 1;
    }

    //Get amount of data
    @Override
    public int getItemCount() {
        return this.dataReadings.size();
    }

    //ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView drDateData;
        private final TextView drDayData;
        private final TextView drTimeData;
        private final TextView drAMPMData;
        private final TextView drBPData;
        private final ConstraintLayout dataLayoutBG;

        //Constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.drDateData = itemView.findViewById(R.id.drDateData);
            this.drDayData = itemView.findViewById(R.id.drDayData);
            this.drTimeData = itemView.findViewById(R.id.drAMPMData);
            this.drAMPMData = itemView.findViewById(R.id.drSessionsData);
            this.drBPData = itemView.findViewById(R.id.drBPData);
            this.dataLayoutBG = itemView.findViewById(R.id.dataLayoutBG);
        }
    }
}
