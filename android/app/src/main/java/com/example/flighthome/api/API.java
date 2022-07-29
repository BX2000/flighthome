package com.example.flighthome.api;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.flighthome.GlobalVariable;
import com.example.flighthome.airport.Airport;
import com.example.flighthome.airport.AirportAdapter;
import com.example.flighthome.flight.Flight;
import com.example.flighthome.flight.FlightAdapter;
import com.example.flighthome.flight.saveFlightAdapter;
import com.example.flighthome.home.HomeActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    //get iataCode from the name of city
    public static void getIata(String city, boolean isFrom){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<JsonObject> call = retrofitAPI.getCityIata(city);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("Message", response.toString());
                if(response.body()!= null) {
                    if(response.body().get("iata")!=null) {
                        if (isFrom) {
                            //departure city iata
                            GlobalVariable.fromCity = response.body().get("iata").getAsString();
                            Log.d("Message", GlobalVariable.fromCity);
                            GlobalVariable.result ++;
                        } else {
                            //arrival city iata
                            GlobalVariable.toCity = response.body().get("iata").getAsString();
                            Log.d("Message", GlobalVariable.toCity);
                            GlobalVariable.result ++;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("Message",t.toString());
            }
        });
    }

    public static void getflight(List<Flight> list, RecyclerView flightRecyclerView, FlightAdapter adapter, String dep_iata, String arr_iata, String date, Context context){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://aviation-edge.com/v2/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<JsonArray> call = retrofitAPI.getFlight("d5ee99-63f7dd","departure",dep_iata,arr_iata,date);
        Log.d("Plane", GlobalVariable.date);

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Log.d("Plane",response.toString());
                try {
                    JsonArray data = response.body();
                    for(int i = 0; i<data.size();i++){
                        JsonObject json = data.get(i).getAsJsonObject();
                        Log.d("Plane", json.toString());
                        Flight flight = new Flight("-","-","-","-","-","-","-","-","-","-","-");
                        String leave = json.get("departure").getAsJsonObject().get("scheduledTime").getAsString();
                        flight.setLeaveTime(leave);
                        String arrive = json.get("arrival").getAsJsonObject().get("scheduledTime").getAsString();
                        flight.setArriveTime(arrive);
                        flight.setAirline(json.get("airline").getAsJsonObject().get("name").getAsString());
                        flight.setFlightNum(json.get("flight").getAsJsonObject().get("iataNumber").getAsString());
                        flight.setFromIata(json.get("departure").getAsJsonObject().get("iataCode").getAsString());
                        flight.setToIata(json.get("arrival").getAsJsonObject().get("iataCode").getAsString());
                        list.add(flight);
                        adapter.notifyItemInserted(list.size()-1);
                        flightRecyclerView.scrollToPosition(list.size()-1);
                    }
                    flightRecyclerView.scrollToPosition(0);
                }catch(Exception e){
                    Log.d("Plane", e.toString());
                    Toast.makeText(context,"No Flights Found",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("Plane",t.toString());
                Toast.makeText(context,"No Flights Found",Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void getairportIata(List<Airport> airportList, String city, RecyclerView airportRecyclerView, AirportAdapter adapter, Context context){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<JsonArray> call = retrofitAPI.getAirportIata(city);

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Log.d("Message-dep", response.toString());
                try {
                    if (response.body() != null) {
                        JsonArray json = response.body();
                        for (int i = 0; i < json.size(); i++) {
                            JsonObject object = json.get(i).getAsJsonObject();
                            Airport airport = new Airport("", "");
                            airport.setIata(object.get("airportiata").getAsString());
                            airport.setName(object.get("airportname").getAsString());
                            airportList.add(airport);
                            Log.d("Message-dep",String.valueOf(airportList.size()));
                            adapter.notifyItemInserted(airportList.size() - 1);
                            airportRecyclerView.scrollToPosition(airportList.size() - 1);
                        }
                        airportRecyclerView.scrollToPosition(0);
                    }else{
                        Toast.makeText(context,"No Airports Found",Toast.LENGTH_LONG).show();
                        Intent settingsIntent = new Intent(context, HomeActivity.class);
                        context.startActivity(settingsIntent);
                    }
                }catch(Exception e){
                    Log.d("Message-dep", e.toString());
                    Toast.makeText(context,"No Airports Found",Toast.LENGTH_LONG).show();
                    Intent settingsIntent = new Intent(context, HomeActivity.class);
                    context.startActivity(settingsIntent);
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.d("Message-dep",t.toString());
                Toast.makeText(context,"No Airports Found",Toast.LENGTH_LONG).show();
                Intent settingsIntent = new Intent(context, HomeActivity.class);
                context.startActivity(settingsIntent);
            }
        });
    }

    //get current flight information with same flight number
    public static void getRecent(TextView sch_dep, TextView sch_arr, TextView est_dep, TextView est_arr, TextView act_dep, TextView act_arr, TextView dep_ter_1, TextView arr_ter_1, TextView status, String dep, String flight_num, LinearLayout recent_view, LinearLayout no_recent_view){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://aviation-edge.com/v2/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<JsonArray> call = retrofitAPI.getRecent("d5ee99-63f7dd","departure",dep,flight_num);

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Log.d("Plane",response.toString());
                try {
                    JsonArray data = response.body();
                    JsonObject flight = data.get(data.size()-1).getAsJsonObject();
                    JsonObject dep = flight.get("departure").getAsJsonObject();
                    JsonObject arr = flight.get("arrival").getAsJsonObject();
                    if(dep.get("scheduledTime")!=null){
                        String schDep = !dep.get("scheduledTime").isJsonNull() ? dep.get("scheduledTime").getAsString() : "";
                        if(!schDep.equals("")) {
                            sch_dep.setText(schDep.substring(11, 16));
                        }
                    }
                    if(arr.get("scheduledTime")!=null){
                        String schArr = !arr.get("scheduledTime").isJsonNull() ? arr.get("scheduledTime").getAsString() : "";
                        if(!schArr.equals("")) {
                            sch_arr.setText(schArr.substring(11, 16));
                        }
                    }
                    if(dep.get("estimatedTime")!=null){
                        String estDep = !dep.get("estimatedTime").isJsonNull() ? dep.get("estimatedTime").getAsString() : "";
                        if(!estDep.equals("")) {
                            est_dep.setText(estDep.substring(11, 16));
                        }
                    }
                    if(arr.get("estimatedTime")!=null){
                        String estArr = !arr.get("estimatedTime").isJsonNull() ? arr.get("estimatedTime").getAsString() : "";
                        if(!estArr.equals("")) {
                            est_arr.setText(estArr.substring(11, 16));
                        }
                    }
                    if(dep.get("actualTime")!=null){
                        String actDep = !dep.get("actualTime").isJsonNull() ? dep.get("actualTime").getAsString() : "";
                        if(!actDep.equals("")) {
                            act_dep.setText(actDep.substring(11, 16));
                        }
                    }
                    if(arr.get("actualTime")!=null){
                        String actArr = !arr.get("actualTime").isJsonNull() ? arr.get("actualTime").getAsString() : "";
                        if(!actArr.equals("")) {
                            act_arr.setText(actArr.substring(11, 16));
                        }
                    }
                    if(flight.get("status")!=null){
                        status.setText(flight.get("status").getAsString());
                    }
                    if(dep.get("terminal")!=null){
                        String terDep = !dep.get("terminal").isJsonNull() ? dep.get("terminal").getAsString() : "";
                        if(!terDep.equals("")) {
                            dep_ter_1.setText(terDep);
                        }
                    }
                    if(arr.get("terminal")!=null){
                        String terArr = !arr.get("terminal").isJsonNull() ? arr.get("terminal").getAsString() : "";
                        if(!terArr.equals("")) {
                            arr_ter_1.setText(terArr);
                        }
                    }
                    //if can get current flight,hide the layout for no flight
                    no_recent_view.setVisibility(View.GONE);
                }catch(Exception e){
                    //if unable to find one, hide the layout for current flight information
                    Log.d("Plane", e.toString());
                    recent_view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("Plane",t.toString());
                recent_view.setVisibility(View.GONE);
            }
        });
    }

    //get the specific route information, if not get use old future schedule data
    public static void getRoute(TextView leave_time,TextView arr_time,TextView dep_ter, TextView arr_ter, String dep, String flight_num, Context context, String leave, String arrive){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://aviation-edge.com/v2/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        String iata = flight_num.substring(0,2);
        String num = flight_num.substring(2);
        Log.d("Plane",num);

        Call<JsonArray> call = retrofitAPI.getRoute("d5ee99-63f7dd",dep,iata,num);
        //Log.d("Plane", GlobalVariable.date);

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                Log.d("Plane",response.toString());
                try {
                    if(response.body().size()==1) {
                        JsonObject data = response.body().get(0).getAsJsonObject();
                        String leave = data.get("departureTime").getAsString();
                        leave_time.setText(leave.substring(0,5));
                        String arr = data.get("arrivalTime").getAsString();
                        arr_time.setText(arr.substring(0,5));
                        dep_ter.setText(data.get("departureTerminal").getAsString());
                        arr_ter.setText(data.get("arrivalTerminal").getAsString());
                    }
                }catch(Exception e){
                    //if not get use old future schedule data and set the notification
                    Log.d("Plane", e.toString());
                    leave_time.setText(leave);
                    arr_time.setText(arrive);
                    Toast.makeText(context,"No Route Information",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("Plane",t.toString());
                leave_time.setText(leave);
                arr_time.setText(arrive);
                Toast.makeText(context,"No Route Information",Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void registerAccount(String id,String name) throws JSONException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        JSONObject json = new JSONObject();
        json.put("userid", id);
        json.put("name",name);

        Call<String> call = retrofitAPI.createAccount(json);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("Message",response.toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Message",t.toString());
            }
        });
    }

    public static void postSave(JsonObject json) throws JSONException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<String> call = retrofitAPI.postFlight(GlobalVariable.userid,json);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("Message",response.toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Message",t.toString());
            }
        });
    }

    public static void getMyflight(List<JsonObject> list, RecyclerView flightRecyclerView, saveFlightAdapter adapter){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<JsonObject> call = retrofitAPI.getMyFlight(GlobalVariable.userid);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("Plane",response.toString());
                try {
                    JsonArray data = response.body().get("Flights").getAsJsonArray();
                    for(int i = 0; i<data.size();i++){
                        JsonObject json = data.get(i).getAsJsonObject();
                        list.add(json);
                        adapter.notifyItemInserted(list.size()-1);
                        flightRecyclerView.scrollToPosition(list.size()-1);
                    }
                    flightRecyclerView.scrollToPosition(0);
                }catch(Exception e){
                    Log.d("Plane", e.toString());
                    //GlobalVariable.error_search = 1;
                    //Toast.makeText(getApplicationContext(),"No Flights Found",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("Plane",t.toString());
                //GlobalVariable.error_search = 2;
            }
        });
    }
}
