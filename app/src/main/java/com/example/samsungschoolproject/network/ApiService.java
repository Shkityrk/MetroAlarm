package com.example.samsungschoolproject.network;

import com.example.samsungschoolproject.model.Station;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {
    /**
     * Метод для выполнения GET-запроса на получение списка объектов Station.
     *
     * @param url URL-адрес для выполнения запроса
     * @return Объект Call, представляющий асинхронный результат запроса
     */
    @GET
    Call<List<Station>> getStations(@Url String url);

    /**
     * Метод для выполнения GET-запроса на получение JSON-строки.
     *
     * @param url URL-адрес для выполнения запроса
     * @return Объект Call, представляющий асинхронный результат запроса
     */
    @GET
    Call<String> getJson(@Url String url);
}
