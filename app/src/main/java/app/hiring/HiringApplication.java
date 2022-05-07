package app.hiring;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import app.hiring.common.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/*
 * HiringApplication.java - MainApplication
 * Functions - check if internet available
 *           - get retrofit client
 */

public class HiringApplication extends Application {
    private static HiringApplication mInstance;
    private static Retrofit retrofit = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized HiringApplication getInstance() {
        return mInstance;
    }

    /*
     * check if internet
     * add permission in manifest.xml
     * ACCESS_NETWORK_STATE, INTERNET
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static Retrofit getRetrofitClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder().build();
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constants.BASE_URL)
                    .build();
        }
        return retrofit;
    }
}
