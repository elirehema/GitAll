package azaa.android.com.azaa.user;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import azaa.android.com.azaa.R;
import azaa.android.com.azaa.ui.activities.ProductUpload;
import azaa.android.com.azaa.adapters.shopAdapter;
import azaa.android.com.azaa.database.databaseOperations;
import azaa.android.com.azaa.model.Product;
import azaa.android.com.azaa.roomApi.database.DatabaseClient;
import azaa.android.com.azaa.roomApi.entity.eProduct;
import azaa.android.com.azaa.roomApi.shoAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class itemStores extends AppCompatActivity  {
    List<Product> productList = new ArrayList<>();
    @BindView(R.id.store_add)
    ImageView i_add_items;
    @BindView(R.id.t_recycler)
    RecyclerView currentNewsRecycler;
    shopAdapter itemsAdapter;
    @BindView(R.id.item_tittle)
    TextView sessionTitle;
    @BindView(R.id.back_arrow)
    ImageView backArrow;
    @BindView(R.id.texPrice)
    TextView textprice;
    String id,title;
    List<eProduct> taskList;
    int totalprice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_stores);
        ButterKnife.bind(this);
        i_add_items.setVisibility(View.VISIBLE);

        currentNewsRecycler.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = this.getIntent();
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");


        sessionTitle.setText(title);

                //getCurentData();
        getTasks();
    }

    public void getCurentData()
    {
        databaseOperations databaseOperations = new databaseOperations(getApplicationContext());
        productList =  databaseOperations.getCurrentdata(id);
        itemsAdapter =new shopAdapter(productList,getApplicationContext());
        itemsAdapter.setHasStableIds(true);


        RecyclerView.LayoutManager reLayoutManager =new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        currentNewsRecycler.setLayoutManager(reLayoutManager);
        currentNewsRecycler.setItemAnimator(new DefaultItemAnimator());
        currentNewsRecycler.setHasFixedSize(true);
        currentNewsRecycler.setItemViewCacheSize(20);
        currentNewsRecycler.setDrawingCacheEnabled(true);
        currentNewsRecycler.setNestedScrollingEnabled(false);
        currentNewsRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        currentNewsRecycler.setAdapter(itemsAdapter);
        //databaseOperations.close();
        itemsAdapter.setOnItemClickListener(new shopAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d("MSG", "onItemClick position: " + position);
                //Log.d("MSG", "onItemClick position: " + position + disaggrigationList.get(position).getDisag_value());
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Toast.makeText(getApplicationContext(),"text",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<eProduct>> {

            @Override
            protected List<eProduct> doInBackground(Void... voids) {
                List<eProduct> taskList = DatabaseClient
                        .getmInstance(getApplicationContext())
                        .getAppDatabase()
                        .productDao()
                        .getAll(id);

                if (taskList != null&&taskList.size()>0) {

                    for (int i = 0; i < taskList.size(); i++) {
                        totalprice = totalprice + Integer.parseInt( taskList.get(i).getPrice());

                    }
                    Log.d("TOTAL PRICE", "" + totalprice);

                }
                return taskList;
            }

            @Override
            protected void onPostExecute(List<eProduct> tasks) {
                super.onPostExecute(tasks);
                shoAdapter adapter = new shoAdapter(itemStores.this, tasks);
                currentNewsRecycler.setAdapter(adapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }




    @OnClick(R.id.back_arrow)
    public void close(){
        finish();
    }
    @OnClick(R.id.store_add)
    public void add(){
        startActivity(new Intent(getApplicationContext(), ProductUpload.class));
    }
}
