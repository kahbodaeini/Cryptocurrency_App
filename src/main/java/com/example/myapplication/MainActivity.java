package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static String symbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void coinSymbol(String string){
        symbol = string;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void monthButtonClicked(View view){

        try {
            ApiReq.getCandles(symbol, 30);

        }
        catch (IOException e){
            //TODO error layout
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void weekButtonClicked(View view){

//        MainActivity.this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//
//                TextView textView = findViewById(R.id.coinNameView);
//                textView.setText("WEEK");
//
//
//
//
//
//                try {
//                    ApiReq.getCandles(symbol, weekly);
//
//                }
//                catch (IOException e){
//                    //TODO error layout
//                }
//            }
//        });

        coinSymbol("BTC");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(MainActivity.this, CoinActivityWeek.class));
            }
        });
        thread.start();
    }
}