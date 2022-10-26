package com.example.myapplication;

import android.app.AppComponentFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.example.myapplication.ApiReq.getCandles;
import static com.example.myapplication.ApiReq.ret;
import static com.example.myapplication.MainActivity.symbol;

public class CoinActivityWeek extends AppCompatActivity {

    public static int getId(String resourceName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / " + c, e);
        }
    }

    private Double[] highPrices = new Double[7];
    private Double[] lowPrices = new Double[7];
    private Double[] openPrices = new Double[7];
    private Double[] closePrices = new Double[7];
    private ArrayList[] arr = new ArrayList[4];

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coin_activity_week);

        try {

            for(int i = 0; i < 4; i++){
                getCandles(symbol, 7);
            }

            for (int i = 0; i < 7; i++){
//                highPrices[i] = String.valueOf(theCrypto.getValues().get(0).get(i));
//                lowPrices[i] = String.valueOf(theCrypto.getValues().get(1).get(i));
//                openPrices[i] = String.valueOf(theCrypto.getValues().get(2).get(i));
//                closePrices[i] = String.valueOf(theCrypto.getValues().get(3).get(i));

                highPrices[i] = (Double) ret.get(0).get(i);
                lowPrices[i] = (Double) ret.get(1).get(i);
                openPrices[i] = (Double) ret.get(2).get(i);
                closePrices[i] = (Double) ret.get(3).get(i);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        CoinActivityWeek.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                for (int i = 1; i <= 7; i++){
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

                    highView.setText(String.valueOf(highPrices[i]));
                    lowView.setText(String.valueOf(lowPrices[i]));
                    openView.setText(String.valueOf(openPrices[i]));
                    closeView.setText(String.valueOf(closePrices[i]));
                }

            }
        });
    }
}
