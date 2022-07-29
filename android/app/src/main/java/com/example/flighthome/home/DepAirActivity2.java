package com.example.flighthome.home;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.flighthome.api.API;
import com.example.flighthome.airport.Airport;
import com.example.flighthome.airport.AirportAdapter;
import com.example.flighthome.BasicActivity;
import com.example.flighthome.GlobalVariable;
import com.example.flighthome.R;

import java.util.ArrayList;
import java.util.List;

public class DepAirActivity2 extends BasicActivity {
    private List<Airport> airportList = new ArrayList<>();
    private RecyclerView airportRecyclerView;
    private LinearLayoutManager layoutManager;
    private AirportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dep_air2);

        setBar();

        GlobalVariable.isArrival=false;

        //build recyclerView
        airportRecyclerView = findViewById(R.id.airport_view);
        layoutManager = new LinearLayoutManager(this);
        adapter = new AirportAdapter(airportList,getIntent().getStringArrayExtra("flight"));
        airportRecyclerView.setLayoutManager(layoutManager);
        airportRecyclerView.setAdapter(adapter);

        getData();
    }

    private void getData(){
        String[] city = getIntent().getStringArrayExtra("flight");
        Log.d("Message-dep",city[0]);
        API.getairportIata(airportList,city[0],airportRecyclerView,adapter,getApplicationContext());
    }

}