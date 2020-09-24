package org.techtown.puppydiary.network;

import org.techtown.puppydiary.Login;
import org.techtown.puppydiary.network.Response.UpdatepwResponse;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class RetrofitClient {

    private final static String BASE_URL = "http://52.79.228.150:3000";
    private static Retrofit retrofit = null;

    private RetrofitClient() {
    }


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // 요청을 보낼 base url을 설정한다.
                    .addConverterFactory(GsonConverterFactory.create()) // JSON 파싱을 위한 GsonConverterFactory를 추가한다.
                    .build();
        }

        return retrofit;
    }


}