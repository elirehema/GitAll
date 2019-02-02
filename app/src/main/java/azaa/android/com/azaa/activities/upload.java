package azaa.android.com.azaa.activities;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import azaa.android.com.azaa.adapters.MySingleton;
import azaa.android.com.azaa.R;
import azaa.android.com.azaa.roomApi.database.DatabaseClient;
import azaa.android.com.azaa.roomApi.entity.eProduct;
import butterknife.BindView;
import butterknife.ButterKnife;

import static azaa.android.com.azaa.network.Config.MY_PREFS_NAME;
import static azaa.android.com.azaa.network.Config.UPLOAD_URL;


public class upload extends Activity implements AdapterView.OnItemSelectedListener{

    @BindView(R.id.btnUpload) Button uploadImage;
    @BindView(R.id.btn_pick) Button pickImage;
    private final int SELECT_PHOTO = 1;
    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.imageTitle) TextInputEditText itemTitle;
    @BindView(R.id.imagePrice) TextInputEditText  itemPrice;
    @BindView(R.id.imageDesc) TextInputEditText itemDesc;
    private Bitmap selectedImage;
    private String item,category;
    String itemName;
    SharedPreferences prefs;
    //private ProgressBar progressBar;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setProgressBarIndeterminateVisibility(true);
        setContentView(R.layout.activity_upload_image);
        ButterKnife.bind(this);
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();


        doUpload();

        imageView.setVisibility(View.GONE);
        uploadImage.setVisibility(View.GONE);

        itemTitle.setVisibility(View.GONE);

        uploadImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if( TextUtils.isEmpty(itemTitle.getText())){
                    itemTitle.setError( "Title Required!" );

                }else if (TextUtils.isEmpty(itemPrice.getText())){
                    itemPrice.setError("Price Required!");
                }else if (TextUtils.isEmpty(itemDesc.getText())){
                    itemDesc.setError("Description Required!");
                }else {
                    new AsyncFetch().execute();
                }
            }
        });

        pickImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                doUpload();
            }
        });
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
                if (resultCode == RESULT_OK && imageReturnedIntent != null) {
                    try {
                        imageUri  = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        selectedImage  = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        imageView.setVisibility(View.VISIBLE);
                        uploadImage.setVisibility(View.VISIBLE);
                        imageView.setImageBitmap(selectedImage);
                        String text = "Change Image";
                        itemTitle.setVisibility(View.VISIBLE);
                        pickImage.setText(text);
                        setSpinnerAdapter();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else {
                    this.finish();
                }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    private class AsyncFetch extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            imageView.setImageResource(0);
            imageView.setVisibility(View.INVISIBLE);
            uploadImage.setVisibility(View.INVISIBLE);
            pickImage.setText("Select Image");

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkResponse errorRes = error.networkResponse;
                    String stringData = "";
                    if(errorRes != null && errorRes.data != null){
                        try {
                            stringData = new String(errorRes.data,"UTF-8");
                            //progressBar.setVisibility(View.GONE);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();

                            //Toast.makeText(upload.this,imageToString(selectedImage),Toast.LENGTH_LONG).show();
                            //Log.d("Image Error",imageToString(selectedImage));
                        }
                    }else{
                        //progressBar.setVisibility(View.GONE);
                    }
                    Log.e("Error",stringData);
                    //Toast.makeText(upload.this,imageToString(selectedImage),Toast.LENGTH_LONG).show();
                    Log.d("Image Error",imageToString(selectedImage));
                }
            })

            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();


                    String s = prefs.getString("location",null);
                    String mLocation = s;
                    itemName  = System.currentTimeMillis()+".png";


                    params.put("desc",itemDesc.getText().toString());
                    params.put("image",itemName);
                    params.put("photo",imageToString(selectedImage));
                    params.put("title",itemTitle.getText().toString().trim());
                    params.put("email",prefs.getString("email",null));
                    params.put("contact",prefs.getString("phone",null));
                    params.put("price",itemPrice.getText().toString().trim());
                    params.put("token",prefs.getString("token",null));
                    params.put("location",mLocation);
                    params.put("category",category);

                    eProduct product = new eProduct();
                    product.setLiked("3");
                    product.setType(category);
                    product.setMail(prefs.getString("email",null));
                    product.setDesc(itemDesc.getText().toString().trim().toLowerCase());
                    product.setContacts(prefs.getString("phone",null));
                    product.setImage(itemName);
                    product.setName(itemTitle.getText().toString());
                    product.setLocation(prefs.getString("location",null));
                    product.setPrice(itemPrice.getText().toString().trim());
                    product.setItemId(itemName);

                    //adding to database

                    DatabaseClient.getmInstance(getApplicationContext()).getAppDatabase()
                            .productDao()
                            .insert(product);

                    return params;
                }
            };
            MySingleton.getmInstance(upload.this).addToRequestQueue(stringRequest);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Toast.makeText(getBaseContext(),"Uploading in Background",Toast.LENGTH_LONG).show();
        }
    }

    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte [] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        item = parent.getItemAtPosition(i).toString();
        switch (item){
            case "Clothes":
                category="w";
                break;
            case "Shoes":
                category="v";
                break;
            case "Jewellery":
                category="j";
                break;
            case "MobilePhones":
                category="p";
                break;
        }

        // Showing selected spinner item
        if (item.equals("Category")){
            uploadImage.setEnabled(false);
            Toast.makeText(parent.getContext(), "Select Category" , Toast.LENGTH_LONG).show();
            Log.d("IMAGE STRING",imageToString(selectedImage));
        } else {
            uploadImage.setEnabled(true);
            Toast.makeText(parent.getContext(), "You have Selected: " + item, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void setSpinnerAdapter(){
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCategory);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Category");
        categories.add("Clothes");
        categories.add("Shoes");
        categories.add("Jewellery");
        categories.add("MobilePhones");



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }






}

