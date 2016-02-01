package retrofit.android.tamhuynh.retrofit.rest;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by tamhuynh on 1/31/16.
 */
public class RestClient {

    public static APIServer apiServerInterface;

    public synchronized static APIServer getClient() {
        if (apiServerInterface == null) {

            OkHttpClient okClient = new OkHttpClient();
            okClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    return response;
                }
            });

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(APIConfig.domainAPI)
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiServerInterface = client.create(APIServer.class);
        }
        return apiServerInterface ;
    }

}
