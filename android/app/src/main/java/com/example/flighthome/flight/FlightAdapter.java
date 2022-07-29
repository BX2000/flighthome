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

import com.example.flighthome.api.API;
import com.example.flighthome.GlobalVariable;
import com.example.flighthome.R;
import com.example.flighthome.home.FlightInfoActivity;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.util.List;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.ViewHolder>{
    private List<Flight> list;
    private String[] flightArr;
    public FlightAdapter(List<Flight> list,String[] flightArr){
        this.list = list;
        this.flightArr = flightArr;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView leave_text;
        private TextView arrive_text;
        private TextView airline_text;
        private TextView leave_iata;
        private TextView arrive_iata;
        private TextView air_num;
        private Button detail;
        private Button save;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Context context = itemView.getContext();
            leave_text=itemView.findViewById(R.id.leave_time);
            arrive_text = itemView.findViewById(R.id.arrive_time);
            airline_text= itemView.findViewById(R.id.airline);
            leave_iata = itemView.findViewById(R.id.leave_text);
            arrive_iata = itemView.findViewById(R.id.arrive_text);
            air_num = itemView.findViewById(R.id.airNum);
            detail = itemView.findViewById(R.id.button_detail);
            save = itemView.findViewById(R.id.button_save);

            detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flightArr[7] = air_num.getText().toString();
                    flightArr[8] =leave_text.getText().toString();
                    flightArr[9] =arrive_text.getText().toString();
                    Intent settingsIntent = new Intent(context, FlightInfoActivity.class);
                    settingsIntent.putExtra("flight",flightArr);
                    context.startActivity(settingsIntent);
                }
            });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!GlobalVariable.userid.equals("0")) {
                        JsonObject json = new JsonObject();
                        json.addProperty("fromIata", flightArr[3]);
                        json.addProperty("toIata", flightArr[5]);
                        json.addProperty("date", flightArr[2]);
                        json.addProperty("dep_city", flightArr[0]);
                        json.addProperty("arr_city", flightArr[1]);
                        json.addProperty("airport_dep_name", flightArr[4]);
                        json.addProperty("airport_arr_name", flightArr[6]);
                        json.addProperty("flightNum", air_num.getText().toString());
                        json.addProperty("leaveTime", leave_text.getText().toString());
                        json.addProperty("arriveTime", arrive_text.getText().toString());
                        json.addProperty("city_dep",flightArr[10]);
                        json.addProperty("city_arr",flightArr[11]);
                        try {
                            API.postSave(json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
    @NonNull
    @Override
    public FlightAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_item,parent,false);
        return new FlightAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Flight flight = list.get(position);
        holder.leave_text.setText(flight.getLeaveTime());
        holder.arrive_text.setText(flight.getArriveTime());
        holder.air_num.setText(flight.getFlightNum());
        holder.airline_text.setText(flight.getAirline());
        holder.leave_iata.setText(flight.getFromIata());
        holder.arrive_iata.setText(flight.getToIata());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
