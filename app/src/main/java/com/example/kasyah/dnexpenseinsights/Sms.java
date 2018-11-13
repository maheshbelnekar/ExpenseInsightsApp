package com.example.kasyah.dnexpenseinsights;

import java.util.Date;

public class Sms{
    private int _sms_id;
    private String _sms_from;
    private Date _sms_date;
    private long _sms_date_long;
    private String _sms_type;
    private double _sms_amount;
    private String _sms_body;

    public int getSmsId(){
        return _sms_id;
    }
    public String getSmsFrom(){
        return _sms_from;
    }
    public Date getSmsDate(){
        return _sms_date;
    }
    public long getSmsDateLong(){
        return _sms_date_long;
    }
    public String getSmsType(){
        return _sms_type;
    }
    public double getSmsAmount(){
        return _sms_amount;
    }
    public String getSmsBody(){
        return _sms_body;
    }

    public void setSmsId(int sms_id){
        sms_id = sms_id;
    }
    public void setSmsFrom(String sms_from){
        _sms_from = sms_from;
    }
    public void setSmsDate(Date sms_date){
        _sms_date = sms_date;
    }
    public void setSmsDateLong(long sms_date_long){
        _sms_date_long = sms_date_long;
    }
    public void setSmsType(String sms_type){
        _sms_type = sms_type;
    }
    public void setSmsAmount(double sms_amount){
        _sms_amount = sms_amount;
    }
    public void setSmsBody(String sms_body){
        _sms_body = sms_body;
    }
}