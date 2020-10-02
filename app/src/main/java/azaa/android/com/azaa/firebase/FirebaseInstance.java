package azaa.android.com.azaa.firebase;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import azaa.android.com.azaa.ui.activities.AuthenticationActivity;

public class FirebaseInstance {
    FirebaseAuth firebaseAuth;
    private Context context;

    public FirebaseInstance(Context context) {
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void isInstanceAuthenticated() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser.equals(null)) {
            context.startActivity(new Intent(context, AuthenticationActivity.class));
        }
    }

}
