package com.weihaozhao.bigdays.db;

import android.net.Uri;

import org.litepal.crud.DataSupport;

import java.util.Calendar;

/**
 * Created by Zhao Weihao on 2017/4/2.
 * Updated by Zhao Weihao on 2017/5/1
 */

public class Events extends DataSupport implements Comparable<Events>{

    private int id;

    private String eventsname;

    private int year;

    private int month;

    private int day;


    public int getYear(){
        return year;

    }
    public void setYear(int year){
        this.year=year;
    }

    public int getMonth(){
        return month;
    }

    public void setMonth(int month){
        this.month=month;
    }
    public int getDay(){
        return day;
    }
    public void setDay(int day){
        this.day=day;
    }

    public String getEventsname(){
        return eventsname;
    }

    public void setEventsname(String eventsname){
        this.eventsname=eventsname;
    }

    public int getId(){return id;}

    @Override
    public int compareTo(Events o) {
        int i=this.dayLeft(this.getYear(),this.getMonth(),this.getDay());
        int z=o.dayLeft(o.getYear(),o.getMonth(),o.getDay());
        if(i>=0&&z>=0){
            return i-z;
        }else if(i<0&&z<0){
            return z-i;
        }
        return z-i;

    }

    public int dayLeft(int y, int m, int d){

            Calendar calendar=Calendar.getInstance();
    int Events_year=calendar.get(Calendar.YEAR),
            Events_month=calendar.get(Calendar.MONTH)+1,
            Events_day=calendar.get(Calendar.DAY_OF_MONTH);


        calendar.set(y,m-1,d);
        long time2=calendar.getTimeInMillis();
        calendar.set(Events_year,Events_month-1,Events_day);
        long time1=calendar.getTimeInMillis();
        long subDay=(time2-time1)/(1000*60*60*24);
                int z=Integer.parseInt(String.valueOf(subDay));
        return z;

    }



}
