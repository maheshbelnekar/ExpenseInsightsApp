package com.example.kasyah.dnexpenseinsights;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class DataBase {

    public static final String  SMS_ID="sms_id";
    public static final String  SMS_FROM="sms_from";
    public static final String  SMS_DATE="sms_date";
    public static final String  SMS_TYPE="sms_type";
    public static final String  SMS_AMOUNT="sms_amount";
    public static final String  SMS_BODY="sms_body";

    public static final String DATABASE_NAME="DNExpenseInsights";
    public static final String SMS_DETAILS="sms_details";
    public static final int DATABASE_VERSION=1;

    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;


    private class DbHelper extends SQLiteOpenHelper{

        public DbHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db){
            db.execSQL("CREATE TABLE "+SMS_DETAILS+" ("+
                    SMS_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    SMS_FROM+ " TEXT NOT NULL, "+
                    SMS_DATE+ " LONG NOT NULL, "+
                    SMS_TYPE+ " TEXT NOT NULL, "+
                    SMS_AMOUNT+ " INTEGER NOT NULL, "+
                    SMS_BODY+ " TEXT NOT NULL, "+
                    "UNIQUE("+ SMS_FROM+ ","+ SMS_DATE+ ") );"
            );
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}
    }

    public DataBase(Context c){
        ourContext=c;
    }

    public DataBase open()throws SQLException{
        ourHelper=new DbHelper(ourContext);
        ourDatabase=ourHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        ourHelper.close();
    }

    public void updateLibrary(ArrayList<Sms> smsArrayList) {
        try {
            for (Sms sms : smsArrayList) {
                try {
                    ContentValues cv = new ContentValues();
                    cv.put(SMS_FROM, sms.getSmsFrom());
                    cv.put(SMS_DATE, sms.getSmsDateLong());
                    cv.put(SMS_TYPE, sms.getSmsType());
                    cv.put(SMS_AMOUNT, sms.getSmsAmount());
                    cv.put(SMS_BODY, sms.getSmsBody());
                    ourDatabase.insert(SMS_DETAILS, null, cv);
                } catch (Exception e) {
                    Toast.makeText(ourContext, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
            return;
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<Sms> retrieveAllSms() {
        ArrayList<Sms> smsArrayList = new ArrayList<Sms>();
        Cursor c = null;
        try{
            String query="select * from sms_details where sms_type NOT IN ('OFFERS','REMINDERS') order by sms_date desc";
            c = ourDatabase.rawQuery(query, null);
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try{
                    Sms sms = new Sms();
                    sms.setSmsId(c.getInt(0));
                    sms.setSmsFrom(c.getString(1));

                    DateFormat formatter=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                    formatter.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                    Date smsDate = formatter.parse(formatter.format(new Date(c.getLong(2))));
                    sms.setSmsDate(smsDate);

                    sms.setSmsType(c.getString(3));
                    sms.setSmsAmount(c.getDouble(4));
                    sms.setSmsBody(c.getString(5));
                    smsArrayList.add(sms);
                } catch (Exception e){
                    //Continue
                }
            }
            return smsArrayList;
        }
        catch(Exception e){
            Toast.makeText(ourContext, e.toString(), Toast.LENGTH_LONG).show();
            return null;
        }
        finally {
            c.close();
        }
    }

    public ArrayList<Sms> retrieveAllRecommendations() {
        ArrayList<Sms> smsArrayList = new ArrayList<Sms>();
        Cursor c = null;
        try{
            String query="select * from sms_details where sms_type IN ('OFFERS','REMINDERS') order by sms_date desc";
            c = ourDatabase.rawQuery(query, null);
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                try{
                    Sms sms = new Sms();
                    sms.setSmsId(c.getInt(0));
                    sms.setSmsFrom(c.getString(1));

                    DateFormat formatter=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                    formatter.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                    Date smsDate = formatter.parse(formatter.format(new Date(c.getLong(2))));
                    sms.setSmsDate(smsDate);

                    sms.setSmsType(c.getString(3));
                    sms.setSmsAmount(c.getDouble(4));
                    sms.setSmsBody(c.getString(5));
                    smsArrayList.add(sms);
                } catch (Exception e){
                    //Continue
                }
            }
            return smsArrayList;
        }
        catch(Exception e){
            Toast.makeText(ourContext, e.toString(), Toast.LENGTH_LONG).show();
            return null;
        }
        finally {
            c.close();
        }
    }

    public HashMap<String, Double> retrieveInsights(ExpenseInsights.Period period, int value, String type) throws Exception{

        Calendar calendar = Calendar.getInstance();

        String monthArr[] = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUNE", "JULY", "AUG", "SEP", "OCT", "NOV", "DEC"};

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        SimpleDateFormat df = new SimpleDateFormat("MMM dd yyyy");
        Date date;
        long epochStart=0,epochEnd=0;

        switch (period) {
            case YEARLY:
                if(value!=0) {
                    year = value;
                }
                int nextYear = year + 1;
                String strStart = "Jan 01 "+ year;
                String strNext = "Jan 01 "+ nextYear;
                date = df.parse(strStart);
                epochStart = date.getTime();
                date = df.parse(strNext);
                epochEnd = date.getTime();
                break;
            default:
                if(value!=0) {
                    month = value;
                }
                int nextMonth = month + 1;
                String strStartMonth = monthArr[month-1]+" 01 "+ year;
                String strNextMonth = monthArr[month]+" 01 "+ year;
                date = df.parse(strStartMonth);
                epochStart = date.getTime();
                date = df.parse(strNextMonth);
                epochEnd = date.getTime();
                break;
        }

        HashMap<String, Double> insightResults = new HashMap<String, Double>();
        Cursor c = null;
        try{
            String query="";
            if (type!=null) {
                query="select sms_date, sms_amount from sms_details where sms_type = "+type+ " and sms_date between "+epochStart+" and "+epochEnd+" order by sms_date desc ";
                c = ourDatabase.rawQuery(query, null);
                for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                    DateFormat formatter=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                    formatter.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
                    String smsDate = formatter.format(new Date(c.getLong(0)));

                    insightResults.put(smsDate, c.getDouble(1));
                }
                return insightResults;
            }

            query="select sms_type, sum(sms_amount) from sms_details where sms_type NOT IN ('OFFERS','REMINDERS') and sms_date between "+epochStart+" and "+epochEnd+" group by sms_type order by sms_date desc ";

            c = ourDatabase.rawQuery(query, null);
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                insightResults.put(c.getString(0), c.getDouble(1));
            }
            return insightResults;
        }
        catch(Exception e){
            Toast.makeText(ourContext, e.toString(), Toast.LENGTH_LONG).show();
            return null;
        }
        finally {
            c.close();
        }

    }
}




