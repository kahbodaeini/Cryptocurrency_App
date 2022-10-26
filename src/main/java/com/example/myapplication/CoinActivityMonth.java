package com.example.myapplication;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.myapplication.ApiReq.getCandles;
import static com.example.myapplication.CoinActivityWeek.getId;
import static com.example.myapplication.MainActivity.symbol;

public class CoinActivityMonth extends AppCompatActivity {

    private ArrayList highPrices = new ArrayList();
    private ArrayList lowPrices = new ArrayList();
    private ArrayList openPrices = new ArrayList();
    private ArrayList closePrices = new ArrayList();
    private ArrayList<ArrayList> arr = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coin_activity_month);

        try {
            getCandles(symbol, 30);
            highPrices = arr.get(0);
            lowPrices = arr.get(0);
            openPrices = arr.get(0);
            closePrices = arr.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }



        CoinActivityMonth.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                for (int i = 1; i <= 30; i++){
                    TextView highView;
                    TextView lowView;
                    TextView openView;
                    TextView closeView;

                    String highString = "highPriceDay" + String.valueOf(i) + "Week";
                    String lowString = "lowPriceDay" + String.valueOf(i) + "Week";
                    String openString = "openPriceDay" + String.valueOf(i) + "Week";
                    String closeString = "closePriceDay" + String.valueOf(i) + "Week";

                    int highInt = getId(highString, TextView.class);
                    int lowInt = getId(lowString, TextView.class);
                    int openInt = getId(openString, TextView.class);
                    int closeInt = getId(closeString, TextView.class);

                    highView = findViewById(highInt);
                    lowView = findViewById(lowInt);
                    openView = findViewById(openInt);
                    closeView = findViewById(closeInt);

                    highView.setText(String.valueOf(highPrices.get(i)));
                    lowView.setText(String.valueOf(lowPrices.get(i)));
                    openView.setText(String.valueOf(openPrices.get(i)));
                    closeView.setText(String.valueOf(closePrices.get(i)));
                }
            }
        });
    }
}