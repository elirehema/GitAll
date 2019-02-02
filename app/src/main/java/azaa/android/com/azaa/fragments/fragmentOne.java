package azaa.android.com.azaa.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import azaa.android.com.azaa.adapters.Item;
import azaa.android.com.azaa.adapters.ItemsAdapter;
import azaa.android.com.azaa.adapters.itemsAdapter;
import azaa.android.com.azaa.R;
import azaa.android.com.azaa.asynctasks.backgroundAsync;
import azaa.android.com.azaa.model.Product;
import azaa.android.com.azaa.rest.ApiClient;
import azaa.android.com.azaa.rest.ApiInterface;
import azaa.android.com.azaa.rest.ProductsResult;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static azaa.android.com.azaa.network.constants.SPANCOUNT;

@SuppressLint("ValidFragment")
public class fragmentOne extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private List<Item> data = new ArrayList<>();
    private String id, title, contact, price, desc, image, email,type,location,t_Name;
    private ItemsAdapter itemsAdapter;
    @BindView(R.id.recyclerItems)
        RecyclerView  recyclerView;
    @BindView(R.id.progressBar3)
        ProgressBar progressBar;
    @BindView(R.id.swipe_layout)
        SwipeRefreshLayout swipeLayout;
    backgroundAsync async;

    List<Product> datalist;


    View view;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private RecyclerView mRecyclerView;
    private static Bundle mBundleRecyclerViewState;

    public fragmentOne() {
        super();
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_one, container, false);
            ButterKnife.bind(this, view);
        }
        progressBar.setProgress(0);
        excuteAsyc();

        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_red_dark),
                        getResources().getColor(android.R.color.holo_blue_dark),
                                getResources().getColor(android.R.color.holo_orange_dark));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), SPANCOUNT));
        itemsAdapter = new ItemsAdapter(datalist,getContext());
        recyclerView.setAdapter(itemsAdapter);
        itemsAdapter.setOnItemClickListener(new ItemsAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d("MSG", "onItemClick position: " + position);
                //Log.d("MSG", "onItemClick position: " + position + disaggrigationList.get(position).getDisag_value());
            }
            @Override
            public void onItemLongClick(int position, View v) {
                Toast.makeText(getActivity(),"text",Toast.LENGTH_LONG).show();
            }
        });
        getdaTa();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBundleRecyclerViewState != null && recyclerView != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            if (recyclerView.getLayoutManager() != null) {
                recyclerView.getLayoutManager().onRestoreInstanceState(listState);
            }
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override public void run() {

                swipeLayout.setRefreshing(false);
            }
        }, 10000);
       getdaTa();
    }

    public void getdaTa(){
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface= ApiClient.getInstance().create(ApiInterface.class);
        apiInterface.getAllWear("w").enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, retrofit2.Response<List<Product>> response) {
                datalist = response.body();

                List<Product> products = response.body();
                recyclerView.setAdapter(new ItemsAdapter(products,getContext()));
                progressBar.setVisibility(View.GONE);



            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    public void excuteAsyc(){
        backgroundAsync async = new backgroundAsync(getContext());
        Log.d("TAG","Background Async Started");
        async.execute();

    }

}