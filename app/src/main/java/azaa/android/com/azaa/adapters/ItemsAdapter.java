package azaa.android.com.azaa.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import azaa.android.com.azaa.activities.openItem;
import azaa.android.com.azaa.R;
import azaa.android.com.azaa.model.Product;
import azaa.android.com.azaa.roomApi.database.DatabaseClient;
import azaa.android.com.azaa.roomApi.entity.eProduct;
import azaa.android.com.azaa.util.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

import static azaa.android.com.azaa.network.Config.IMAGE_URL;

public class ItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
    private TextView itemtitle, itemPrice, itemDesc;

    public ItemsAdapter(List<Product> itemsList, Context context) {
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


        MyHolder myHolder = (MyHolder) holder;
        Product item = itemsList.get(position);
        myHolder.textName.setText(item.getProductName());
        myHolder.textPrice.setText(moneyFormatter(item.getProductPrice()));

        String imageUrl = IMAGE_URL + item.getProductImage();
        StorageReference imageStorage = FirebaseStorage.getInstance().getReference().child(Constants.DATABASE_PATH_UPLOADS).child(item.getProductImage());

        imageStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                GlideApp.with(context)
                        .load(uri.toString())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(14)))
                        .into(myHolder.imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("Exception", exception.getMessage() + "PrintTrace: " + exception.getStackTrace());
                // failed
            }
        });

    }

    @Override
    public int getItemCount() {
        if (itemsList != null) {
            return itemsList.size();
        }
        return 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.textItemTitle)
        TextView textName;
        @BindView(R.id.textItemPrice)
        TextView textPrice;
        @BindView(R.id.imageItemImage)
        ImageView imageView;
        @BindView(R.id.textItemShare)
        ImageView share;
        @BindView(R.id.textItemLike)
        ImageView likeItem;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imageView.setOnClickListener(this);
            share.setOnClickListener(this);
            imageView.setOnLongClickListener(this);
            likeItem.setOnClickListener(this);
        }

        @SuppressLint("CheckResult")
        @Override
        public void onClick(View v) {

            current = itemsList.get(getAdapterPosition());
            int pos = getAdapterPosition();
            clickListener.onItemClick(getAdapterPosition(), v);
            Context context = v.getContext();

            switch (v.getId()) {
                case R.id.imageItemImage:


                    Intent intent = new Intent(context, openItem.class);
                    intent.putExtra("name", current.getProductName());
                    intent.putExtra("desc", current.getProductDescription());
                    intent.putExtra("price", moneyFormatter(current.getProductPrice()));
                    intent.putExtra("mail", current.getProductContacts());
                    intent.putExtra("phone", current.getProductContacts());
                    intent.putExtra("image", current.getProductImage());
                    intent.putExtra("shop", current.getProductLocation());
                    intent.putExtra("category", current.getProductType());
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
                    String shareBody = "Checkout this product " + "From http://zedoo.000webhostapp.com/ura/";
                    String shareSub = "Your Subject here";
                    myIntent.setType("image/*");
                    myIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(IMAGE_URL + current.getProductImage()));
                    myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                    myIntent.putExtra(Intent.EXTRA_TEXT, shareBody.toString());
                    context.startActivity(Intent.createChooser(myIntent, "Share Via"));
                    break;
                case R.id.textItemLike:
                    updateStatus();
                    isOpen = true;
                    likeItem.setImageResource(R.drawable.cart_arrow_right);
                    Toast.makeText(context, "Saved Succesfully", Toast.LENGTH_SHORT).show();

                    break;
            }

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public void setOnItemClickListener(ItemsAdapter.ClickListener clickListener) {
        ItemsAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }

    public String moneyFormatter(String s) {
        String price = "Tsh " + String.format("%,.0f", Double.parseDouble(s));
        return price;
    }

    public void updateStatus() {
        class Tasks extends AsyncTask<Void, Void, Void> {


            @Override
            protected Void doInBackground(Void... voids) {

                eProduct product = new eProduct();
                product.setLiked("1");
                product.setType(current.getProductType());
                product.setMail(current.getProductEmail());
                product.setDesc(current.getProductDescription());
                product.setContacts(current.getProductContacts());
                product.setImage(current.getProductImage());
                product.setName(current.getProductName());
                product.setLocation(current.getProductLocation());
                product.setPrice(current.getProductPrice());
                product.setItemId(current.getProductId());

                //adding to database

                DatabaseClient.getmInstance(context).getAppDatabase()
                        .productDao()
                        .insert(product);
                return null;
            }
        }

        Tasks gt = new Tasks();
        gt.execute();
    }


}
