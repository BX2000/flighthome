package com.example.flighthome.flight;

public class Flight {
    private String leaveTime;
    private String arriveTime;
    private String airline;
    private String flightNum;
    private String fromIata;
    private String toIata;
    private String dep_city;
    private String arr_city;
    private String date;
    private String airport_dep_name;
    private String airport_arr_name;

    public Flight(String leaveTime,String arriveTime,String airline,String flightNum,String fromIata,String toIata,String dep_city,String arr_city,String date,String airport_dep_name,String airport_arr_name){
        this.leaveTime=leaveTime;
        this.arriveTime=arriveTime;
        this.airline=airline;
        this.fromIata=fromIata;
        this.toIata=toIata;
        this.flightNum=flightNum;
        this.dep_city = dep_city;
        this.arr_city = arr_city;
        this.date = date;
        this.airport_dep_name = airport_dep_name;
        this.airport_arr_name = airport_arr_name;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public void setFromIata(String fromIata) {
        this.fromIata = fromIata;
    }

    public void setToIata(String toIata) {
        this.toIata = toIata;
    }

    public void setDep_city(String dep_city) {
        this.dep_city = dep_city;
    }

    public void setArr_city(String arr_city) {
        this.arr_city = arr_city;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAirport_dep_name(String airport_dep_name) {
        this.airport_dep_name = airport_dep_name;
    }

    public void setAirport_arr_name(String airport_arr_name) {
        this.airport_arr_name = airport_arr_name;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public String getAirline() {
        return airline;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public String getFromIata() {
        return fromIata;
    }

    public String getToIata() {
        return toIata;
    }

    public String getDep_city() {
        return dep_city;
    }

    public String getArr_city() {
        return arr_city;
    }

    public String getDate() {
        return date;
    }

    public String getAirport_dep_name() {
        return airport_dep_name;
    }

    public String getAirport_arr_name() {
        return airport_arr_name;
    }
}
