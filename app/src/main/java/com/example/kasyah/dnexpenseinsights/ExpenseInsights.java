package com.example.kasyah.dnexpenseinsights;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpenseInsights {

    public static Context ourContext;

    public static enum Period {
        YEARLY,
        MONTHLY
    }

    public static void initializeLibrary(Context context) {
        ourContext = context;
        try{
            ArrayList<Sms> smsArrayList = SmsReader.readAllSms(ourContext);
            DataBase dataBase = new DataBase(ourContext);
            dataBase.open();
            dataBase.updateLibrary(smsArrayList);
            dataBase.close();
        } catch (Exception e){
            Toast.makeText(ourContext, "Message: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public static ArrayList<Sms>re retrieveAllSms(Context context) {
        ourContext = context;
        try{
            DataBase dataBase = new DataBase(ourContext);
            dataBase.open();
            ArrayList<Sms> smsDetailsArrayList = dataBase.retrieveAllSms();
            dataBase.close();
            return  smsDetailsArrayList;
        } catch (Exception e){
            Toast.makeText(ourContext, "Message: " + e.toString(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public static ArrayList<Sms> retrieveAllRecommendations(Context context) {
        ourContext = context;
        try{
            DataBase dataBase = new DataBase(ourContext);
            dataBase.open();
            ArrayList<Sms> smsDetailsArrayList = dataBase.retrieveAllRecommendations();
            dataBase.close();
            return  smsDetailsArrayList;
        } catch (Exception e){
            Toast.makeText(ourContext, "Message: " + e.toString(), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public static void updateLibrary(ArrayList<Sms> smsArrayList) {
        try{
            if(smsArrayList!=null){
                DataBase dataBase = new DataBase(ourContext);
                dataBase.open();
                dataBase.updateLibrary(smsArrayList);
                dataBase.close();
            }
        } catch (Exception e){
            Toast.makeText(ourContext, "Message: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public static HashMap<String, Double> retrieveInsights(Period period, int value, String type) {
        try {
            HashMap<String, Double> insightResults = new HashMap<String, Double>();
            DataBase dataBase = new DataBase(ourContext);
            dataBase.open();
            insightResults = dataBase.retrieveInsights(period, value, type);
            dataBase.close();
            return insightResults;
        } catch (Exception e) {
            return null;
        }
    }

}
