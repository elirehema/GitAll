package azaa.android.com.azaa.user;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;

import com.google.android.material.textfield.TextInputEditText;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import azaa.android.com.azaa.R;
import butterknife.BindView;
import butterknife.ButterKnife;

import static azaa.android.com.azaa.network.Config.MY_PERMISSIONS_REQUEST_SEND_SMS;
import static azaa.android.com.azaa.network.Config.MY_PREFS_NAME;

public class editProfile extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "RESPONSE" ;
    @BindView(R.id.profileUsername)
    TextInputEditText username;
    @BindView(R.id.profilePhone)
    TextInputEditText userPhone;
    @BindView(R.id.profileEmail)
    TextInputEditText userEmail;
    @BindView(R.id.profileLocation)
    TextInputEditText userLocation;
    @BindView(R.id.profileToken)
    TextInputEditText token;
    @BindView(R.id.profileSaveButton)
    Button save;
    @BindView(R.id.profile_image)
    ImageView imageView;
    @BindView(R.id.imageButton)
    TextView imageButton;
    @BindView(R.id.textWarning)
    TextView warning;

    private Spinner spinner;
    private String name,phone,location,email,ulocation;
    private final int SELECT_PHOTO = 1;
    String phoneNo = "+255716087522";
    String  message = "Test message";
    private String previouslyEncodedImage;
    private Bitmap selectedImage;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        //Set CLick listeners
        save.setEnabled(false);
        imageButton.setOnClickListener(this);

        //Invoke the methods Below

        savedData();


    }
    public void doUpload() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        imageUri  = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        selectedImage  = BitmapFactory.decodeStream(imageStream);
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageBitmap(selectedImage);
                        warning.setVisibility(View.INVISIBLE);
                        save.setEnabled(true);
                        save.setOnClickListener(this);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }


        }
    }
    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte [] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }



    public void savedData(){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String phone = prefs.getString("phone", null);
        String location = prefs.getString("location", null);
        String name = prefs.getString("name", null);

         previouslyEncodedImage = prefs.getString("image_data", "");
        String email = prefs.getString("email", null);

        if( !previouslyEncodedImage.equalsIgnoreCase("") ) {
            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            imageView.setImageBitmap(bitmap);
            username.setText(prefs.getString("name", null));
            userLocation.setText(prefs.getString("location", null));
            userPhone.setText(phone);
            userEmail.setText(prefs.getString("email", null));
        }else if(phone == null||location==null||name==null||email==null) {

            }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.profileSaveButton:
                if( TextUtils.isEmpty(username.getText())) {
                    username.setError("Invalid Username!");
                }else if ( TextUtils.isEmpty(userPhone.getText()) || !PhoneNumberUtils.isGlobalPhoneNumber(userPhone.getText().toString())||!validCellPhone(userPhone.getText().toString())) {
                    userPhone.setError("Invalid Phone No.!");
                }else if ( TextUtils.isEmpty(userEmail.getText()) ||!isValidEmailAddress(userEmail.getText().toString())) {
                    userEmail.setError("Invalid Email!");
                }else if ( TextUtils.isEmpty(userLocation.getText())) {
                    userLocation.setError("Location Required!");
                }else if(TextUtils.isEmpty(imageToString(selectedImage))){
                    imageView.setImageBitmap(BitmapFactory.decodeByteArray(Base64.decode(previouslyEncodedImage,Base64.DEFAULT),0,(Base64.decode(previouslyEncodedImage,Base64.DEFAULT).length)));

                }else {

                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("name", username.getText().toString().toLowerCase());
                    editor.putString("phone", userPhone.getText().toString().toLowerCase());
                    editor.putString("location", userLocation.getText().toString().toLowerCase());
                    editor.putString("email", userEmail.getText().toString().toLowerCase());
                    editor.putString("image_data",imageToString(selectedImage));
                    editor.putString("token",token.getText().toString());
                    editor.apply();
                    startActivity(new Intent(getBaseContext(), viewProfile.class));
                    this.finish();
                }

            break;
            case R.id.imageButton:
                if (isReadStoragePermissionGranted() && isWriteStoragePermissionGranted()) {
                    doUpload();
                }
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
}

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    public boolean validCellPhone(String number)
    {
        return android.util.Patterns.PHONE.matcher(number).matches();
    }
    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted1");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted1");
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted2");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted2");
            return true;
        }
    }


}
