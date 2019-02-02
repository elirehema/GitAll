package azaa.android.com.azaa.adapters;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton mInstance;
    private  RequestQueue requestQueue;
    private static Context ctx;

    private MySingleton(Context context)

    {
        ctx = context;
        requestQueue =  getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if(requestQueue == null)

            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
            return requestQueue;

    }

    public static synchronized MySingleton getmInstance(Context context)
    {
        if(mInstance == null)
        {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }
    public <T> void addToRequestQueue(Request<T> request)
    {
        request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(request);

    }
}
