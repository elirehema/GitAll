package azaa.android.com.azaa.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ShareEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import azaa.android.com.azaa.activities.openItem;
import azaa.android.com.azaa.R;

import static azaa.android.com.azaa.network.Config.IMAGE_URL;

public class itemsAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    List<Item> itemsList = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    private static ClickListener clickListener;
    private PopupWindow popupWindow;
    private String sellerMail;
    private FloatingActionButton email, sms, call;
    private String phone;
    Item current;
    private String sellerPhone;
    private boolean isOpen = false;
    private ImageView popupImage;
    private TextView itemtitle, itemPrice,itemDesc;

    public itemsAdapter(List<Item> itemsList, Context context) {
        this.itemsList = itemsList;
        //layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

                View v1 = inflater.inflate(R.layout.fashion_right, parent, false);
                viewHolder = new MyHolder(v1);

        return viewHolder;

    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        MyHolder myHolder = (MyHolder)holder;
        Item item = itemsList.get(position);
        myHolder.textName.setText(item.getName());
        myHolder.textPrice.setText(moneyFormatter(item.getPrice()));

        String imageUrl = IMAGE_URL + item.getImage();

        GlideApp.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .into(myHolder.imageView);


    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView textName;
        TextView textPrice;
        ImageView imageView;
        ImageView share;
        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textName= (TextView) itemView.findViewById(R.id.textItemTitle);
            textPrice = (TextView) itemView.findViewById(R.id.textItemPrice);
            share = itemView.findViewById(R.id.textItemShare);
            imageView = itemView.findViewById(R.id.imageItemImage);
            imageView.setOnClickListener(this);
            share.setOnClickListener(this);
            imageView.setOnLongClickListener(this);
        }

        @SuppressLint("CheckResult")
        @Override
        public void onClick(View v) {

           current = itemsList.get(getAdapterPosition());
            int pos = getAdapterPosition();
            clickListener.onItemClick(getAdapterPosition(), v);
            Context context = v.getContext();

            switch (v.getId()){
                case R.id.imageItemImage:


                        Intent intent = new Intent(context, openItem.class);
                        intent.putExtra("name",current.getName());
                        intent.putExtra("desc",current.getDescription());
                        intent.putExtra("price",moneyFormatter(current.getPrice()));
                        intent.putExtra("mail",current.getEmail());
                        intent.putExtra("phone",current.getContacts());
                        intent.putExtra("image",current.getImage());
                        intent.putExtra("shop",current.getLocation());
                        intent.putExtra("category",current.getCategory());
                        context.startActivity(intent);

                    break;
                case R.id.textItemShare:
                    //Toast.makeText(v.getContext(), "Share Clicked!!!.", Toast.LENGTH_SHORT).show();

                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());

                    File imageFileToShare = new File(IMAGE_URL + current.getImage());
                    Uri uri = Uri.fromFile(imageFileToShare);

                            Intent myIntent = new Intent(Intent.ACTION_SEND);
                            myIntent.setType("text/plain");
                            String shareBody = "Checkout this product "+"From http://zedoo.000webhostapp.com/ura/";
                            String shareSub = "Your Subject here";
                            myIntent.setType("image/*");
                    myIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(IMAGE_URL + current.getImage()));
                    myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                    myIntent.putExtra(Intent.EXTRA_TEXT, shareBody.toString());
                    context.startActivity(Intent.createChooser(myIntent, "Share Via"));
                    Answers.getInstance().logShare(new ShareEvent()
                            .putMethod("Share")
                            .putContentName(current.getName())
                            .putContentType(current.getCategory())
                            .putContentId("60107305"));

                    break;
            }

        }
        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
    public void setOnItemClickListener(itemsAdapter.ClickListener clickListener) {
        itemsAdapter.clickListener = clickListener;
    }
    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
    public String moneyFormatter(String s){
       String price = "Tsh "+String.format("%,.0f", Double.parseDouble(s));
        return price;
    }
}
