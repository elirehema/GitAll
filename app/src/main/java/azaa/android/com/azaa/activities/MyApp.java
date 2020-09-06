package azaa.android.com.azaa.activities;

import android.app.Application;
import androidx.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by ravi on 25/12/17.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        MultiDex.install(this);

        // initialize the AdMob app
        //MobileAds.initialize(this, getString(R.string.admob_app_id));
    }
}