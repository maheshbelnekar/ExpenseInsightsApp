package com.example.kasyah.dnexpenseinsights;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main_App extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__app);

        if ((ContextCompat.checkSelfPermission(Main_App.this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(Main_App.this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) ) {
                ActivityCompat.requestPermissions(Main_App.this,
                    new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS},
                    1);
        } else {
            try {
                ExpenseInsights.initializeLibrary(Main_App.this);
                HashMap<String, Double> insightResults = ExpenseInsights.retrieveInsights(ExpenseInsights.Period.YEARLY, 0, null);
                if (insightResults!=null) {
                    for (Map.Entry<String, Double> result : insightResults.entrySet()) {
                        String key = result.getKey();
                        Double value = result.getValue();
                        Toast.makeText(Main_App.this, key + " : " + value, Toast.LENGTH_LONG).show();
                    }
                }

                HashMap<String, Double> insightBillsResults = ExpenseInsights.retrieveInsights(ExpenseInsights.Period.MONTHLY, 3, "BILLS");
                if (insightBillsResults!=null) {
                    for (Map.Entry<String, Double> result : insightBillsResults.entrySet()) {
                        String key = result.getKey();
                        Double value = result.getValue();
                        //Toast.makeText(Main_App.this, key + " : " + value, Toast.LENGTH_LONG).show();
                    }
                }

                ArrayList<Sms> smsDetailsArrayList = ExpenseInsights.retrieveAllSms(Main_App.this);
                if (smsDetailsArrayList!=null) {
                    for (Sms sms : smsDetailsArrayList) {
                        //Toast.makeText(Main_App.this, String.valueOf(sms.getSmsDate())+" : "+ sms.getSmsType(), Toast.LENGTH_LONG).show();
                    }
                }

                ArrayList<Sms> smsRecommendationsArrayList = ExpenseInsights.retrieveAllRecommendations(Main_App.this);
                if (smsRecommendationsArrayList!=null) {
                    for (Sms sms : smsRecommendationsArrayList) {
                        //Toast.makeText(Main_App.this, String.valueOf(sms.getSmsDate())+" : "+ sms.getSmsType(), Toast.LENGTH_LONG).show();
                    }
                }
            } catch (Exception e) {
                Toast.makeText(Main_App.this, "Exception: " + e.toString(), Toast.LENGTH_LONG).show();
            }

            /*SmsReader.bindListener(new SmsListener() {
                @Override
                public void messageReceived(Sms sms) {
                    Toast.makeText(Main_App.this, "Message: " + sms.getSmsFrom(), Toast.LENGTH_LONG).show();
                }
            });*/
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main__app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
