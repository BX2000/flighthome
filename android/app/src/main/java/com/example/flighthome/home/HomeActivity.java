package com.example.flighthome.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.flighthome.api.API;
import com.example.flighthome.BasicActivity;
import com.example.flighthome.GlobalVariable;
import com.example.flighthome.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeActivity extends BasicActivity {
    private Button insertCity;
    private EditText from_place;
    private EditText to_place;
    private EditText date_place;
    private ImageView account_image;
    //String iata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setBar();

        from_place = findViewById(R.id.from_text);
        to_place = findViewById(R.id.to_text);
        date_place = findViewById(R.id.date_text);

        //set city and airport database(no need for user)
        insertCity = findViewById(R.id.button);
        insertCity.setVisibility(View.INVISIBLE);


        Button search_button = findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariable.result = 0;
                String fromPlace = from_place.getEditableText().toString().trim();
                String toPlace = to_place.getEditableText().toString().trim();
                String date_set = date_place.getEditableText().toString().trim();
                try{
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date stringDate= format.parse(date_set);
                    GlobalVariable.date = date_set;
                    //make sure get all necessary information
                    if(!fromPlace.equals("") && !toPlace.equals("") && !date_set.equals("")){
                        API.getIata(fromPlace,true);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                API.getIata(toPlace,false);
                            }},1000);
                        Handler handler2 = new Handler();
                        handler2.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //jump to next page only when get iata data for both cities
                                if(GlobalVariable.result == 2) {
                                    Intent settingsIntent = new Intent(HomeActivity.this, DepAirActivity2.class);
                                    String[] flight = new String[12];
                                    flight[0] = GlobalVariable.fromCity;
                                    flight[1] = GlobalVariable.toCity;
                                    flight[2] = GlobalVariable.date;
                                    flight[10] = fromPlace;
                                    flight[11] = toPlace;
                                    settingsIntent.putExtra("flight",flight);
                                    GlobalVariable.fromCity = "";
                                    GlobalVariable.toCity = "";
                                    GlobalVariable.date = "";
                                    startActivity(settingsIntent);
                                }else{
                                    Toast.makeText(HomeActivity.this,"Get Wrong City!",Toast.LENGTH_LONG).show();
                                }
                            }},2000);
                    }else{
                        Toast.makeText(HomeActivity.this,"Some necessary information missing!",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Log.d("Date",e.toString());
                    Toast.makeText(HomeActivity.this,"Check Format of Date!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}