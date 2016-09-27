package br.com.pagueamigo.testsendpicture.rest;


import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


/**
 * Class that handle http client.
 */
public class RetrofitAPI {

    private static final String API_URL = "http://pagueamigo-api.cfapps.io/api/v2/"; // Tests
    public static final String GOOGLE_API_URL = "https://www.googleapis.com/plus/v1/"; // Tests

    public ServerAPI client;
    private Retrofit retrofit;

    /**
     * Constructor method.
     */
    public RetrofitAPI() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        client = retrofit.create(ServerAPI.class);
    }

    public void setUrl(String url) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        client = retrofit.create(ServerAPI.class);
    }

    /**
     * This interface represents the server API and its methods.
     */
    public interface ServerAPI {

        @Multipart
        @POST("person/{ownerId}/receipt")
        Call<Void> addRequestTransaction(
                @Header("X-Auth-Token") String token,
                @Path("ownerId") String ownerId,
                @Part("json") RequestBody transaction,
                @Part MultipartBody.Part filePart);


    }
}