package com.example.flighthome.flight;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flighthome.R;
import com.example.flighthome.home.FlightInfoActivity;
import com.google.gson.JsonObject;

import java.util.List;

public class saveFlightAdapter extends RecyclerView.Adapter<saveFlightAdapter.ViewHolder>{
    private List<JsonObject> list;
    public saveFlightAdapter(List<JsonObject> list){
        this.list = list;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView city_dep;
        private TextView city_arr;
        private TextView airline;
        private TextView date;
        private TextView dep_time;
        private TextView arr_time;
        private TextView from_iata;
        private TextView to_iata;
        private TextView dep_name;
        private TextView arr_name;
        private Button detail;
        private Button delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Context context = itemView.getContext();
            city_dep=itemView.findViewById(R.id.city_dep);
            city_arr = itemView.findViewById(R.id.city_arr);
            airline= itemView.findViewById(R.id.airNum2);
            date = itemView.findViewById(R.id.textView21);
            dep_time = itemView.findViewById(R.id.time_dep);
            arr_time = itemView.findViewById(R.id.time_arr);
            from_iata = itemView.findViewById(R.id.leave_iata);
            to_iata = itemView.findViewById(R.id.arrive_iata);
            dep_name = itemView.findViewById(R.id.dep_air_name);
            arr_name = itemView.findViewById(R.id.arr_air_name);
            detail = itemView.findViewById(R.id.detailButton);
            delete = itemView.findViewById(R.id.button_delete);

            dep_name.setVisibility(View.GONE);
            arr_name.setVisibility(View.GONE);

            detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] flight = new String[12];
                    flight[2] = date.getText().toString();
                    flight[3] = from_iata.getText().toString().trim();
                    flight[4] = dep_name.getText().toString().trim();
                    flight[5] = to_iata.getText().toString().trim();
                    flight[6] = arr_name.getText().toString().trim();
                    flight[7] = airline.getText().toString().trim();
                    flight[8] = dep_time.getText().toString().trim();
                    flight[9] = arr_time.getText().toString().trim();
                    Intent settingsIntent = new Intent(context, FlightInfoActivity.class);
                    settingsIntent.putExtra("flight",flight);
                    context.startActivity(settingsIntent);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
    @NonNull
    @Override
    public saveFlightAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.save_flights,parent,false);
        return new saveFlightAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JsonObject json = list.get(position);
        String from = "FROM: " +
                json.get("city_dep").getAsString();
        String to = "TO: " +
                json.get("city_arr").getAsString();
        holder.city_dep.setText(from);
        holder.city_arr.setText(to);
        holder.from_iata.setText(json.get("airportiata_dep").getAsString());
        holder.to_iata.setText(json.get("airportiata_arr").getAsString());
        holder.airline.setText(json.get("flight_num").getAsString());
        holder.dep_time.setText(json.get("dep_time").getAsString());
        holder.arr_time.setText(json.get("arr_time").getAsString());
        holder.date.setText(json.get("date").getAsString());
        holder.dep_name.setText(json.get("airportname_dep").getAsString());
        holder.arr_name.setText(json.get("airportname_arr").getAsString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

