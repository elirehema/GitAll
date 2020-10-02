package azaa.android.com.azaa.roomApi;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import azaa.android.com.azaa.R;
import azaa.android.com.azaa.ui.activities.openItem;
import azaa.android.com.azaa.adapters.GlideApp;
import azaa.android.com.azaa.roomApi.entity.eProduct;

import static azaa.android.com.azaa.network.Config.IMAGE_URL;

public class shoAdapter extends RecyclerView.Adapter<shoAdapter.TasksViewHolder> {

    private Context mCtx;
    private List<eProduct> taskList;
    eProduct t;

    public shoAdapter(Context mCtx, List<eProduct> taskList) {
        this.mCtx = mCtx;
        this.taskList = taskList;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.shop_row_layout, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        t = taskList.get(position);
        holder.textViewTask.setText(t.getDesc());
        holder.textViewDesc.setText(moneyFormatter(t.getPrice()));
        holder.textViewStatus.setText(t.getName());

        String imageUrl = IMAGE_URL + t.getImage();

        GlideApp.with(mCtx)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewStatus, textViewTask, textViewDesc, textViewFinishBy;
        ImageView imageView;

        public TasksViewHolder(View itemView) {
            super(itemView);

            textViewStatus = itemView.findViewById(R.id.s_name);
            textViewTask = itemView.findViewById(R.id.s_desc);
            textViewDesc = itemView.findViewById(R.id.s_price);
            imageView = itemView.findViewById(R.id.s_Image);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            eProduct ts = taskList.get(getAdapterPosition());


            Intent intent = new Intent(mCtx, openItem.class);
            intent.putExtra("name",ts.getName());
            intent.putExtra("desc",ts.getDesc());
            intent.putExtra("price",moneyFormatter(ts.getPrice()));
            intent.putExtra("mail",ts.getMail());
            intent.putExtra("phone",ts.getContacts());
            intent.putExtra("image",ts.getImage());
            intent.putExtra("shop",ts.getLocation());
            intent.putExtra("category",ts.getType());


            mCtx.startActivity(intent);
        }

    }
    public String moneyFormatter(String s){
        String price = "Tsh "+String.format("%,.0f", Double.parseDouble(s));
        return price;
    }
}