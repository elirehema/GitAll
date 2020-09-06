package azaa.android.com.azaa;

import android.app.Application;
import androidx.multidex.MultiDex;

/**
 * Created by ravi on 25/12/17.
 */

public class ApplicationContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}