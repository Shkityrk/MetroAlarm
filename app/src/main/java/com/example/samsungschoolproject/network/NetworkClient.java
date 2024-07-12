package com.example.samsungschoolproject.network;

import android.content.Context;
import android.content.res.Resources;

import com.example.samsungschoolproject.R;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    private static Retrofit retrofit;

    /**
     * Метод для получения экземпляра Retrofit клиента с настроенным OkHttpClient,
     * поддерживающим SSL и загрузку пользовательского сертификата.
     *
     * @param context Контекст приложения для доступа к ресурсам
     * @return Retrofit клиент
     */
    public static Retrofit getRetrofitClient(Context context) {
        if (retrofit == null) {
            try {
                // Загрузка пользовательского сертификата из ресурсов
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                InputStream inputStream = context.getResources().openRawResource(R.raw.key); // Сертификат хранится в файле key.crt в папке res/raw
                Certificate ca;
                try {
                    ca = cf.generateCertificate(inputStream);
                } finally {
                    inputStream.close();
                }

                // Создание KeyStore, содержащего наш доверенный сертификат
                String keyStoreType = KeyStore.getDefaultType();
                KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                keyStore.load(null, null);
                keyStore.setCertificateEntry("ca", ca);

                // Создание TrustManager, доверяющего сертификатам в нашем KeyStore
                String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                tmf.init(keyStore);

                // Создание SSLContext, использующего наш TrustManager
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, tmf.getTrustManagers(), null);

                // Создание OkHttpClient с настроенным SSLContext
                OkHttpClient client = new OkHttpClient.Builder()
                        .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) tmf.getTrustManagers()[0])
                        .hostnameVerifier((hostname, session) -> true) // Разрешение всех хостов
                        .build();

                // Создание Retrofit с настройками OkHttpClient и базовым URL
                retrofit = new Retrofit.Builder()
                        .baseUrl("https://example.com") // Замените на ваш базовый URL
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return retrofit;
    }
}