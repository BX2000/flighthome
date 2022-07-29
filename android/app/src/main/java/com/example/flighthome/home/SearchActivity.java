package com.example.flighthome.home;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.flighthome.api.API;
import com.example.flighthome.BasicActivity;
import com.example.flighthome.flight.Flight;
import com.example.flighthome.flight.FlightAdapter;
import com.example.flighthome.R;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BasicActivity {
    private List<Flight> flightList = new ArrayList<>();
    private RecyclerView flightRecyclerView;
    private LinearLayoutManager layoutManager;
    private FlightAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setBar();

        flightRecyclerView = findViewById(R.id.flightView);
        layoutManager = new LinearLayoutManager(this);
        adapter = new FlightAdapter(flightList,getIntent().getStringArrayExtra("flight"));
        flightRecyclerView.setLayoutManager(layoutManager);
        flightRecyclerView.setAdapter(adapter);

        getData();
    }

    private void getData(){
        String[] flight = getIntent().getStringArrayExtra("flight");
        API.getflight(flightList,flightRecyclerView,adapter,flight[3],flight[5],flight[2], getApplicationContext());
    }

}