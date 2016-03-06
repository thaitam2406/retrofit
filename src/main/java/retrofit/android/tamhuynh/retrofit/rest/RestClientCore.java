package retrofit.android.tamhuynh.retrofit.rest;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

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
            okClient.interceptors().add(logging);
            okClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    return response;
                }
            });

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(APIConfigCore.domainAPI)
                    .client(okClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiServerCoreInterface = client.create(APIServerCore.class);
        }
        return apiServerCoreInterface ;
    }

}
