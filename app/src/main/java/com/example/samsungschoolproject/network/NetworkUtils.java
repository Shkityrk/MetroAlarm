package com.example.samsungschoolproject.network;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.samsungschoolproject.activity.IntroErrorActivity;
import com.example.samsungschoolproject.data.StationRepository;
import com.example.samsungschoolproject.model.Station;
import com.example.samsungschoolproject.utils.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    /**
     * Метод для выполнения GET-запроса к серверу и получения JSON-ответа.
     *
     * @param urlString URL-адрес для выполнения запроса
     * @return Строка, содержащая JSON-ответ от сервера, или null в случае ошибки
     */
    public static String getJSONFromServer(String urlString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResponse = null;

        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Получаем поток ввода
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            if (builder.length() == 0) {
                return null;
            }
            jsonResponse = builder.toString();
        } catch (IOException e) {
            Log.e(TAG, "Error ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }
        return jsonResponse;
    }
// Метод getJSONFromServer стоит переписать
//    public static String getJSONFromServer(String urlString) {
//        ApiService apiService = NetworkClient.getRetrofitClient().create(ApiService.class);
//        Call<String> call = apiService.getJson(urlString);
//
//        try {
//            Response<String> response = call.execute();
//            if (response.isSuccessful() && response.body() != null) {
//                return response.body();
//            } else {
//                Log.e(TAG, "Response not successful: " + response.message());
//            }
//        } catch (IOException e) {
//            Log.e(TAG, "Error fetching data: ", e);
//        }
//        return null;
//    }

    /**
     * Метод для отключения проверки SSL-сертификатов.
     * Используется только в тестовых или доверенных средах.
     */
    public static void disableSSLCertificateChecking() {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для обновления данных на основе JSON-ответа от сервера через Retrofit.
     *
     * @param url         URL-адрес для выполнения запроса
     * @param application Объект Application для доступа к репозиторию
     * @param context     Контекст для запуска операции
     */
    public static void updateDataFromJSON(String url, Application application, Context context) {
        ApiService apiService = NetworkClient.getRetrofitClient(context).create(ApiService.class);
        Call<List<Station>> call = apiService.getStations(url);

        call.enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Station> stationList = response.body();
                    StationRepository repository = new StationRepository(application);
                    repository.deleteAndInsertAll(stationList, context);
                } else {
                    Log.e(TAG, "Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                Log.e(TAG, "Error fetching data: ", t);
                // Здесь можно вызвать startErrorActivity или другое действие
            }
        });
    }

}