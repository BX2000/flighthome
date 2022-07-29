package com.example.flighthome.home;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.flighthome.api.API;
import com.example.flighthome.BasicActivity;
import com.example.flighthome.R;

import java.util.Locale;

public class FlightInfoActivity extends BasicActivity {
    private TextView flight;
    private TextView leave_time;
    private TextView arr_time;
    private TextView dep_name;
    private TextView arr_name;
    private TextView dep_ter;
    private TextView arr_ter;
    private TextView leave_info;
    private TextView arr_info;

    //recent flights in this route
    private TextView sch_dep;
    private TextView sch_arr;
    private TextView status;
    private TextView est_dep;
    private TextView act_dep;
    private TextView est_arr;
    private TextView act_arr;
    private TextView dep_ter_1;
    private TextView arr_ter_1;

    private LinearLayout recent_view;
    private LinearLayout no_recent_view;

    private String[] flightArr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_info);

        setBar();

        flight = findViewById(R.id.textView6);
        leave_time = findViewById(R.id.leave_time_2);
        arr_time = findViewById(R.id.arrive_time_2);
        dep_name = findViewById(R.id.dep_air);
        arr_name = findViewById(R.id.arr_air);
        dep_ter = findViewById(R.id.dep_ter);
        arr_ter = findViewById(R.id.arr_ter);
        sch_dep = findViewById(R.id.leave_time_3);
        sch_arr = findViewById(R.id.arrive_time_3);
        status = findViewById(R.id.status);
        est_dep = findViewById(R.id.est_dep);
        act_dep = findViewById(R.id.act_dep);
        est_arr = findViewById(R.id.est_arr);
        act_arr = findViewById(R.id.act_arr);
        dep_ter_1 = findViewById(R.id.dep_ter_2);
        arr_ter_1 = findViewById(R.id.arr_ter_2);
        leave_info= findViewById(R.id.leave_info);
        arr_info = findViewById(R.id.arrive_info);

        recent_view = findViewById(R.id.recent_item);
        no_recent_view = findViewById(R.id.no_recent_view);

        flightArr = getIntent().getStringArrayExtra("flight");
        dep_name.setText(flightArr[4]);
        arr_name.setText(flightArr[6]);
        flight.setText(flightArr[7].toUpperCase(Locale.ROOT));
        leave_info.setText(flightArr[3].toUpperCase(Locale.ROOT));
        arr_info.setText(flightArr[5].toUpperCase(Locale.ROOT));

        API.getRoute(leave_time,arr_time,dep_ter, arr_ter,flightArr[3],flightArr[7],getApplicationContext(),flightArr[8],flightArr[9]);
        API.getRecent(sch_dep,sch_arr,est_dep,est_arr,act_dep,act_arr,dep_ter_1,arr_ter_1,status,flightArr[3],flightArr[7],recent_view,no_recent_view);
    }
}