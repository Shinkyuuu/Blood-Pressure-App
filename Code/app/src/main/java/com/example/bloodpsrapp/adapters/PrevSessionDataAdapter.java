package com.example.bloodpsrapp.adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bloodpsrapp.R;
import com.example.bloodpsrapp.models.Readings;
import java.util.ArrayList;

public class PrevSessionDataAdapter extends RecyclerView.Adapter<PrevSessionDataAdapter.ViewHolder> {
    private final ArrayList<Readings> readings;
    private final PrevDataClickListener prevDataClickListener;

    //Constructor
    public PrevSessionDataAdapter(PrevDataClickListener prevDataClickListener, ArrayList<Readings> readings) {
        this.prevDataClickListener = prevDataClickListener;

        this.readings = readings;
    }

    //Create the ViewHolder for a single data view
    @NonNull
    @Override
    public PrevSessionDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prev_session_data_layout, parent, false);

        return new PrevSessionDataAdapter.ViewHolder(prevDataClickListener, view);
    }

    //Bind data to data ViewHolder
    @Override
    public void onBindViewHolder(@NonNull PrevSessionDataAdapter.ViewHolder holder, int position) {
        holder.prevSYSData.setText(String.valueOf(readings.get(position).getSYS()));
        holder.prevDYData.setText(String.valueOf(readings.get(position).getDY()));
        holder.prevHRData.setText(String.valueOf(readings.get(position).getHR()));
    }

    //Get amount of data
    @Override
    public int getItemCount() {
        return readings.size();
    }

    //Update recyclerview
    public void updatePrevDataRV(ArrayList<Readings> readings) {
        this.readings.clear();
        this.readings.addAll(readings);
        this.notifyDataSetChanged();
    }

    //ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView prevSYSData;
        private final TextView prevDYData;
        private final TextView prevHRData;
        private final Button editPrevDataBtn;
        private final PrevDataClickListener prevDataClickListener;

        //Constructor
        public ViewHolder(PrevDataClickListener prevDataClickListener, @NonNull View itemView) {
            super(itemView);
            this.prevDataClickListener = prevDataClickListener;
            this.prevSYSData = itemView.findViewById(R.id.prevSYSData);
            this.prevDYData = itemView.findViewById(R.id.prevDYData);
            this.prevHRData = itemView.findViewById(R.id.prevHRData);
            this.editPrevDataBtn = itemView.findViewById(R.id.editPrevDataBtn);

            editPrevDataBtnListener();
        }

        //editPrevDataBtn listener
        public void editPrevDataBtnListener () {
            editPrevDataBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prevDataClickListener.openEditSessionDialog(getAbsoluteAdapterPosition());
                }
            });
        }
    }

    public interface PrevDataClickListener {
        void openEditSessionDialog(int position);
    }
}
