package azaa.android.com.azaa.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;

import azaa.android.com.azaa.R;
import azaa.android.com.azaa.user.editProfile;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static azaa.android.com.azaa.network.Config.IMAGE_URL;
import static azaa.android.com.azaa.network.Config.MY_PREFS_NAME;

public class openItem extends AppCompatActivity {
    @BindView(R.id.itemName)
    TextView itemName;
    @BindView(R.id.itemPrice)
    TextView itemPrice;
    @BindView(R.id.itemDesc)
    TextView itemDesc;
    @BindView(R.id.itemShop)
    TextView itemShop;
    @BindView(R.id.itemImage)
    ImageView imageView;

    private String name, price,desc,shop, phone,emails,category,title;

    @BindView(R.id.buttonMessage)
    FloatingActionButton sms;
    @BindView(R.id.buttonCall)
    FloatingActionButton call;
    @BindView(R.id.buttonPlaceOrder)
    FloatingActionButton emailb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_item);

        ButterKnife.bind(this);



        Intent itent = this.getIntent();
        name = this.getIntent().getStringExtra("name");
        price = this.getIntent().getStringExtra("price");
        desc = this.getIntent().getStringExtra("desc");
        shop = "Shop";
        title = this.getIntent().getStringExtra("name").toUpperCase();
        phone = this.getIntent().getStringExtra("phone");
        emails =  this.getIntent().getStringExtra("mail");
        category = this.getIntent().getStringExtra("category");

        itemShop.setText(shop);
        itemName.setText(name);
        itemPrice.setText(price);
        itemDesc.setText(desc);

        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName(name)
                .putContentType(category)
                .putContentId("article-350"));

        String imageUrl = IMAGE_URL + this.getIntent().getStringExtra("image");
        RequestOptions requestOptions = new RequestOptions();

        requestOptions.placeholder(R.drawable.placeholder);
        requestOptions.error(R.drawable.placeholder);

        Glide.with(getBaseContext())
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView);

    }

    @OnClick(R.id.buttonMessage)
    public void sendMail(){

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String phone = prefs.getString("phone", null);
        String location = prefs.getString("location", null);
        String name = prefs.getString("name", null);
        String email = prefs.getString("email", null);
        if (phone == null||location==null||name==null||email==null) {
            startActivity(new Intent(openItem.this, editProfile.class));
        }else {

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{emails});
            i.putExtra(Intent.EXTRA_SUBJECT, "CUSTOMER ORDER (Ura)");
            i.putExtra(Intent.EXTRA_TEXT, "Hellow am Interested to buy " + name.toString() +
                    "From you.\n\n" + "DETAILS:\n" + "Name:\t" + name + "\n LOCATION:\t" + location + "\n EMAIL:\t" + email + "\n PHONE:\t" + phone);
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(openItem.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            } finally {

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        //popupWindow.dismiss();
                    }
                }, 10000);
                //Toast.makeText(context, "Order placed Succesfully!!!.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @OnClick(R.id.buttonCall)
    public void makeCall(){

            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
            startActivity(intent);


    }
    @OnClick(R.id.buttonPlaceOrder)
    public void sendSmS() {

            SharedPreferences prefs = getBaseContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            String phone = prefs.getString("phone", null);
            String location = prefs.getString("location", null);
            String name = prefs.getString("name", null);
            String email = prefs.getString("email", null);
            if (phone == null || location == null || name == null || email == null) {
                startActivity(new Intent(openItem.this, editProfile.class));
            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phone));
                intent.putExtra("sms_body", "Hellow am Interested to buy " + name.toString() +
                        "\t From you.\n\n" + "DETAILS:\n" + "Name:\t" + name + "\n LOCATION:\t" + location + "\n EMAIL:\t" + email + "\n PHONE:\t" + phone);
                startActivity(intent);
            }


    }



}
