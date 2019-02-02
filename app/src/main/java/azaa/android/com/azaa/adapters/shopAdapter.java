package azaa.android.com.azaa.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ShareEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import azaa.android.com.azaa.activities.openItem;
import azaa.android.com.azaa.R;
import azaa.android.com.azaa.model.Product;
import butterknife.BindView;
import butterknife.ButterKnife;

import static azaa.android.com.azaa.network.Config.IMAGE_URL;

public class shopAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{
    List<Product> itemsList = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    private static ClickListener clickListener;
    private FloatingActionButton email, sms, call;
    private String phone;
    Product current;
    private String sellerPhone;
    private boolean isOpen = false;
    private ImageView popupImage;
    private TextView itemtitle, itemPrice,itemDesc;

    public shopAdapter(List<Product> itemsList, Context context) {
        this.itemsList = itemsList;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v1 = inflater.inflate(R.layout.shop_row_layout, parent, false);
        viewHolder = new MyHolder(v1);

        return viewHolder;

    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        MyHolder myHolder = (MyHolder)holder;
        Product item = itemsList.get(position);
        myHolder.textName.setText(item.getProductName());
        myHolder.textPrice.setText(moneyFormatter(item.getProductPrice()));
        myHolder.share.setText(item.getProductDescription());

        String imageUrl = IMAGE_URL + item.getProductImage();

        GlideApp.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .into(myHolder.imageView);


    }

    @Override
    public int getItemCount() {
        if (itemsList != null) {
            return itemsList.size();
        }
        return 0;
    }
    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        @BindView(R.id.s_name)
        TextView textName;
        @BindView(R.id.s_price)
        TextView textPrice;
        @BindView(R.id.s_Image)
        ImageView imageView;
        @BindView(R.id.s_desc)
        TextView share;
        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
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
                    intent.putExtra("name",current.getProductName());
                    intent.putExtra("desc",current.getProductDescription());
                    intent.putExtra("price",moneyFormatter(current.getProductPrice()));
                    intent.putExtra("mail",current.getProductContacts());
                    intent.putExtra("phone",current.getProductContacts());
                    intent.putExtra("image",current.getProductImage());
                    intent.putExtra("shop",current.getProductLocation());
                    intent.putExtra("category",current.getProductType());
                    context.startActivity(intent);

                    break;
                case R.id.textItemShare:
                    //Toast.makeText(v.getContext(), "Share Clicked!!!.", Toast.LENGTH_SHORT).show();

                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());

                    File imageFileToShare = new File(IMAGE_URL + current.getProductImage());
                    Uri uri = Uri.fromFile(imageFileToShare);

                    Intent myIntent = new Intent(Intent.ACTION_SEND);
                    myIntent.setType("text/plain");
                    String shareBody = "Checkout this product "+"From http://zedoo.000webhostapp.com/ura/";
                    String shareSub = "Your Subject here";
                    myIntent.setType("image/*");
                    myIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(IMAGE_URL + current.getProductImage()));
                    myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                    myIntent.putExtra(Intent.EXTRA_TEXT, shareBody.toString());
                    context.startActivity(Intent.createChooser(myIntent, "Share Via"));
                    Answers.getInstance().logShare(new ShareEvent()
                            .putMethod("Share")
                            .putContentName(current.getProductName())
                            .putContentType(current.getProductType())
                            .putContentId("60107305"));

                    break;
            }

        }
        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
    public void setOnItemClickListener(shopAdapter.ClickListener clickListener) {
        shopAdapter.clickListener = clickListener;
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

