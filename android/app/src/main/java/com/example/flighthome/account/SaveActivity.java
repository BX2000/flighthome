package com.example.flighthome.account;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.flighthome.api.API;
import com.example.flighthome.BasicActivity;
import com.example.flighthome.R;
import com.example.flighthome.flight.saveFlightAdapter;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class SaveActivity extends BasicActivity {
    private List<JsonObject> flightList = new ArrayList<>();
    private RecyclerView flightRecyclerView;
    private LinearLayoutManager layoutManager;
    private saveFlightAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        setBar();

        flightRecyclerView = findViewById(R.id.save_recycler);
        layoutManager = new LinearLayoutManager(this);
        adapter = new saveFlightAdapter(flightList);
        flightRecyclerView.setLayoutManager(layoutManager);
        flightRecyclerView.setAdapter(adapter);

        getData();
    }

    private void getData(){
        API.getMyflight(flightList,flightRecyclerView,adapter);
    }
}