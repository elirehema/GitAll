package azaa.android.com.azaa.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import azaa.android.com.azaa.R;
import azaa.android.com.azaa.user.editprofile.model.PresenterImpl;
import azaa.android.com.azaa.user.editprofile.presenter.Presenter;
import azaa.android.com.azaa.user.editprofile.view.profileView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static azaa.android.com.azaa.network.Config.MY_PREFS_NAME;

public class viewProfile extends AppCompatActivity implements profileView {
    @BindView(R.id.profilename) TextView username;
    @BindView(R.id.profilephone) TextView userPhone;
    @BindView(R.id.profileemail) TextView userEmail;
    @BindView(R.id.profilelocation) TextView userLocation;
    @BindView(R.id.profileToken) TextView token;

    @BindView (R.id.profileedit) Button edit;
    @BindView(R.id.userprofileImage) ImageView imageView;
    @BindView(R.id.imageView2) ImageView userImage;

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_profile);
        ButterKnife.bind(this);

        presenter = new PresenterImpl(viewProfile.this);
        getData();
    }
    @OnClick(R.id.profileedit)
    void Onclick(){
        presenter.navigatEdit(new Intent(getApplicationContext(),editProfile.class));
    }

    public void getData(){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String phone = prefs.getString("phone", null);
        String location = prefs.getString("location", null);
        String name = prefs.getString("name", null);
        String email = prefs.getString("email", null);
        String previouslyEncodedImage = prefs.getString("image_data", "");

        if( !previouslyEncodedImage.equalsIgnoreCase("") ) {
            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            userImage.setImageBitmap(bitmap);
            imageView.setImageBitmap(bitmap);
        }


        if (phone == null||location==null||name==null||email==null) {
            startActivity(new Intent(getBaseContext(), editProfile.class));
            finish();
        }else{
            username.setText(name.toUpperCase().charAt(0)+name.substring(1,name.length()));
            userLocation.setText(email);
            userPhone.setText(phone);
            userEmail.setText(location);
            token.setText("Token No: "+prefs.getString("token",null));
        }
    }


    @Override
    public void startNewIntent() {
        startActivity(new Intent(getApplicationContext(),editProfile.class));
    }
}
