package com.example.alarm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.sql.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DBConn {

    Connection conn;
    public DBConn()
    {
        try {
            // 1) Register the driver class
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection("jdbc:mysql:///firsttime", "root", "admin");

        } catch (SQLException e) {
            System.out.println(" Error while connecting to database. Exception code : " + e);
        } catch (ClassNotFoundException e) {
            System.out.println(" Failed to register driver. Exception code : " + e);
        }
    }

    public void insertval(String mname, String remtim, int doses)
    {

        try {

            Statement stmt = ((java.sql.Connection) conn).createStatement();

            String query = "Insert into demo values('"+mname+"','"+doses+"','"+remtim+"')";
            int a = stmt.executeUpdate(query);
            if (a > 0) {
                System.out.println("Data is inserted");
            } else {
                System.out.println("Insertion failed");
            }
            stmt.close();
            //((java.sql.Connection) conn).close();


        } catch (SQLException e) {
            System.out.println(" Error while connecting to database. Exception code : " + e);
        }
    }

    public void close()
    {
        try
        {
            ((java.sql.Connection) conn).close();
        }
        catch (SQLException e) {
            System.out.println(" Error while connecting to database. Exception code : " + e);
        }
    }

    public void insertvalues(String mname, int doses, String uneet, String type, int reps, String rmtime, String stardet, String endet, String weekde, int repeet) throws ParseException
    {
        CurrentTime ctime = new CurrentTime(stardet);
        String[] instance = ctime.getNextTime((int) (24/reps), rmtime);
        try {

            Statement stmt = ((java.sql.Connection) conn).createStatement();

            String query = "Insert into demo values('"+mname+"','"+doses+"','"+uneet+"', '"+type+"', '"+reps+"','"+rmtime+"', '"+instance[3]+"', '"+stardet+"', '"+endet+"', '"+weekde+"', '"+repeet+"')";
            int a = stmt.executeUpdate(query);
            if (a > 0) {
                System.out.println("Data is inserted");
            } else {
                System.out.println("Insertion failed");
            }
            stmt.close();
            //((java.sql.Connection) conn).close();


        } catch (SQLException e) {
            System.out.println(" Error while connecting to database. Exception code : " + e);
        }
    }

    private ObservableList<demoinfo> list = FXCollections.observableArrayList();
    public ObservableList<demoinfo> getTable()
    {
        //ObservableList<demoinfo> list = FXCollections.observableArrayList();
        try
        {
            PreparedStatement ps = conn.prepareStatement("select * from demo");
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                list.add(new demoinfo(rs.getString("med_name"), rs.getString("remtime"), rs.getInt("doses")));
            }



        }
        catch (SQLException e) {
            System.out.println(" Error while connecting to database. Exception code : " + e);
        }

        return list;

    }

    public demoinfo[] forDisplay()
    {
        demoinfo[] llst = new demoinfo[list.size()];
        try
        {
            PreparedStatement ps = conn.prepareStatement("select * from demo");
            ResultSet rs = ps.executeQuery();
            int i = 0;

            while(rs.next())
            {
                llst[i] = new demoinfo(rs.getString("med_name"), rs.getString("remtime"), rs.getInt("doses"));
                //list.add(new demoinfo(rs.getString("med_name"), rs.getString("remtime"), rs.getInt("doses")));
                i++;
            }



        }
        catch (SQLException e) {
            System.out.println(" Error while connecting to database. Exception code : " + e);
        }

        return llst;
    }

    public HashMap<String, Boolean> getAlarms()
    {
        HashMap<String, Boolean> ans = new HashMap<String, Boolean>();

        try
        {
            PreparedStatement ps = conn.prepareStatement("select remtime from demo");
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                ans.put(rs.getString("remtime"), true);
            }

        }
        catch (SQLException e) {
            System.out.println(" Error while connecting to database. Exception code : " + e);
        }

        return ans;
    }

    public HashMap<Pair<String, String>, Boolean> getTimes()
    {
        //HashMap<String, HashMap<String, Boolean>> timeandweek = new HashMap<String, HashMap<String, Boolean>>();
        HashMap<Pair<String, String>, Boolean> timeandweek = new HashMap<>();
        try
        {
            PreparedStatement ps = conn.prepareStatement("select remtime, weekday from demo order by remtime");
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                Pair<String, String> temp = new Pair<String, String>(rs.getString("remtime"), rs.getString("weekday"));
                timeandweek.put(temp, Boolean.TRUE);

            }

            Iterator it = timeandweek.entrySet().iterator();
            while(it.hasNext())
            {
                Map.Entry mpel = (Map.Entry)it.next();
                System.out.println(mpel.getKey() + " " + mpel.getValue());
            }

        }
        catch (SQLException e) {
            System.out.println(" Error while connecting to database. Exception code : " + e);
        }

        return timeandweek;

    }
    public int doss()
    {
        int cnt = 0;
        try
        {
            PreparedStatement ps = conn.prepareStatement("select doses from demo");
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                cnt += rs.getInt("doses");
            }

        }
        catch (SQLException e) {
            System.out.println(" Error while connecting to database. Exception code : " + e);
        }
        return cnt;
    }

    public void updatenextTimes(String mname, String almtime, String weekde)
    {

    }



}