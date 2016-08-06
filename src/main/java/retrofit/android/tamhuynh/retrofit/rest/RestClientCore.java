package retrofit.android.tamhuynh.retrofit.rest;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by tamhuynh on 1/31/16.
 */
public class RestClientCore {

    public static APIServerCore apiServerCoreInterface;

    public synchronized static APIServerCore getClient() {
        if (apiServerCoreInterface == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okClient = new OkHttpClient();
            okClient.interceptors().add(new okhttp3.Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    if (chain != null) {
                        Request originalRequest = chain.request();
                        return chain.proceed(originalRequest);
                    }
                    return null;
                }
            });
            okClient.interceptors().add(logging);

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(APIConfigCore.domainAPI)
                    .client(okClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiServerCoreInterface = client.create(APIServerCore.class);
        }
        return apiServerCoreInterface;
    }

}
