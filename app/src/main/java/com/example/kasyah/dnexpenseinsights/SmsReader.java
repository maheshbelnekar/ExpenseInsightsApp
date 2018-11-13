package com.example.kasyah.dnexpenseinsights;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SmsReader extends BroadcastReceiver
{
    private static SmsListener mListener;

    public void onReceive(Context context, Intent intent)
    {
        Bundle myBundle = intent.getExtras();
        SmsMessage [] messages = null;
        String strMessage = "";

        if (myBundle != null)
        {
            Object [] pdus = (Object[]) myBundle.get("pdus");
            messages = new SmsMessage[pdus.length];

            for (int i = 0; i < messages.length; i++)
            {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                Sms sms = SmsParser.parse(context, messages[i].getOriginatingAddress(), messages[i].getMessageBody(), String.valueOf(new Date()));
                if(sms!=null) {
                    try{
                        ArrayList<Sms> arrayList = new ArrayList<Sms>();
                        arrayList.add(sms);
                        ExpenseInsights.updateLibrary(arrayList);
                    } catch (Exception e) {}

                }
                //mListener.messageReceived(sms);
            }
        }
    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }

    public static ArrayList<Sms> readAllSms(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int senderIndex = smsInboxCursor.getColumnIndex("address");
        int messageIndex = smsInboxCursor.getColumnIndex("body");
        int dateIndex = smsInboxCursor.getColumnIndex("date");
        if (messageIndex < 0 || !smsInboxCursor.moveToFirst()) return null;
        ArrayList<Sms> smsArrayList = new ArrayList<Sms>();
        do {
            String sender = smsInboxCursor.getString(senderIndex);
            String message = smsInboxCursor.getString(messageIndex);
            String smsDate =  smsInboxCursor.getString(dateIndex);
            Sms sms = SmsParser.parse(context, sender, message, smsDate);
            if(sms!=null)
                smsArrayList.add(sms);
        } while (smsInboxCursor.moveToNext());
        return smsArrayList;
    }
}