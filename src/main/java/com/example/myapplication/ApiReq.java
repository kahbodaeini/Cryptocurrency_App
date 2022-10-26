package com.example.myapplication;

import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ApiReq {

    public static String startTime;
    public static String endTime;
    public static String openTime;
    public static String closeTime;
    private static ArrayList<Double> highPrices = new ArrayList();
    private static ArrayList<Double> lowPrices = new ArrayList();
    private static ArrayList<Double> openPrices = new ArrayList();
    private static ArrayList<Double> closePrices = new ArrayList();
    public static String tradedVolume;
    public static String tradesCount;
    static Log log;
    public static ArrayList<ArrayList> ret = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void getCandles(final String symbol, final int range) throws IOException {

        Thread thread = new Thread() {

            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                String miniUrl = "period_id=1DAY".concat("&limit=").concat(String.valueOf(range));
                HttpUrl.Builder urlBuilder = HttpUrl.parse("https://rest.coinapi.io/v1/ohlcv/".concat(symbol).concat("/USD/latest?".concat(miniUrl)))
                        .newBuilder();

                String url = urlBuilder.build().toString();

                final Request request = new Request.Builder().url(url).addHeader("X-CoinPI-Key", "E5CB2574-A0D2-4A8F-96A9-B0FF6FF42162").build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("network", Objects.requireNonNull(e.getMessage()));
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        if (!response.isSuccessful()) {
                            Log.e("network", response.body().string());
                        } else {
                            String body = response.body().string();
                            Log.d("response", body);
                            try {
                                JSONArray data = new JSONArray(body);
                                for (int i = 0; i < range; i++) {
                                    JSONObject object = (JSONObject) data.get(i);
                                    highPrices.set(i, object.getDouble("price_high"));
                                    lowPrices.set(i, object.getDouble("low_high"));
                                    closePrices.set(i, object.getDouble("close_high"));
                                    openPrices.set(i, object.getDouble("open_high"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            ret.set(0, highPrices);
                            ret.set(1, lowPrices);
                            ret.set(2, openPrices);
                            ret.set(3, closePrices);
                        }
                    }
                });
            }
        };
        thread.start();
    }
}