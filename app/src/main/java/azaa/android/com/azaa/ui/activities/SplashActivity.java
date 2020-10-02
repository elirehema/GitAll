package azaa.android.com.azaa.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Intent loggedInUserIntent = new Intent(SplashActivity.this, MainActivity.class);
                Intent i = new Intent(SplashActivity.this, AuthenticationActivity.class);

                if (!user.isAnonymous()){
                    startActivity(loggedInUserIntent);
                    finish();
                }else {
                    startActivity(i);
                    finish();
                }


            }
        }, 1000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

}
