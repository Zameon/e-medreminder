package com.example.alarm;

public class Time {
    private int hour;
    private int minute;
    private int second;
    private String time24 = new String();
    private String ans = new String();


    public Time(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public Time(String currentTime) {
        String[] time = currentTime.split(":");
        hour = Integer.parseInt(time[0]);
        minute = Integer.parseInt(time[1]);
        second = Integer.parseInt(time[2]);
    }

    public String getCurrentTime(){
        String hr = new String();
        String mn = new String();
        String sc = new String();

        if(hour/10 == 0) hr += '0';
        else hr += (hour/10);
        hr += (hour%10);


        if(minute/10 == 0) mn += '0';
        else mn += (minute/10);
        mn += (minute%10);

        if(second/10 == 0) sc += '0';
        else sc += (second/10);
        sc += (second%10);

        return hr + ":" + mn + ":" + sc;

    }

    public void oneSecondPassed(){
        second++;
        if(second == 60){
            minute++;
            second = 0;
            if(minute == 60){
                hour++;
                minute = 0;
                if(hour == 24){
                    hour = 0;
                    System.out.println("Next day");
                }
            }
        }
    }


}