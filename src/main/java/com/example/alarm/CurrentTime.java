package com.example.alarm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CurrentTime {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    Date date = new Date();

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    private Map week = new HashMap<>();

    String[] hocche = new String[5];


    public CurrentTime() {
        System.out.println("imgointocry");

    }

    public CurrentTime(String dt) throws ParseException
    {
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
        date = formatter1.parse(dt);
        //System.out.println("isthayou "+date.toString());
        week.put(1, "Sunday");
        week.put(2, "Monday");
        week.put(3, "Tuesday");
        week.put(4, "Wednesday");
        week.put(5, "Thursday");
        week.put(6, "Friday");
        week.put(7, "Saturday");
    }

    public String currentTime(){

        System.out.println(formatter.format(date));
        Date ndate = new Date(date.getTime() + MILLIS_IN_A_DAY);
        System.out.println(formatter.format(ndate));
        return dtf.format(now);
    }

    public String currentDate()
    {
        return formatter.format(date);
    }

    public String currentweekday()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return week.get(cal.get(Calendar.DAY_OF_WEEK)).toString();
    }

    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

    public String findNextDay()
    {
        Date ndate = new Date(date.getTime() + MILLIS_IN_A_DAY);
        return formatter.format(ndate).toString();
    }

    private static Date findPrevDay(Date date)
    {
        return new Date(date.getTime() - MILLIS_IN_A_DAY);
    }

    public String[] getNextTime(int add, String rmtime)
    {
        Calendar cal = Calendar.getInstance();
        String[] spleet = rmtime.split(":");
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(spleet[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(spleet[1]));
        cal.set(Calendar.SECOND, Integer.parseInt(spleet[2]));
        System.out.println(cal.getTime());
        System.out.println("Inside gnt " + date.toString());
        System.out.println(cal.get(Calendar.DAY_OF_WEEK));
        System.out.println(cal.getTime());
        cal.add(Calendar.HOUR_OF_DAY, +add );
        hocche = cal.getTime().toString().split(" ");
        System.out.println(hocche[3]);
        return hocche;
    }

    public String getWeekday()
    {
        return hocche[0];
    }
}