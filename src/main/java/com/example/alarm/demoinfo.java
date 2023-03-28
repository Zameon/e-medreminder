package com.example.alarm;

public class demoinfo {
    String m_name, remtimee;
    int doses;

    public demoinfo()
    {

    }

    public demoinfo(String m_name, String remtimee, int doses) {
        this.m_name = m_name;
        this.remtimee = remtimee;
        this.doses = doses;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getRemtimee() {
        return remtimee;
    }

    public void setRemtimee(String remtimee) {
        this.remtimee = remtimee;
    }

    public int getDoses() {
        return doses;
    }

    public void setDoses(int doses) {
        this.doses = doses;
    }
}
