package azaa.android.com.azaa.asynctasks;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.util.List;

import azaa.android.com.azaa.model.Product;
import azaa.android.com.azaa.rest.ApiClient;
import azaa.android.com.azaa.rest.ApiInterface;
import azaa.android.com.azaa.roomApi.database.DatabaseClient;
import azaa.android.com.azaa.roomApi.entity.eProduct;
import retrofit2.Call;
import retrofit2.Callback;

public class backgroundAsync extends AsyncTask<String, Integer, String> {
    List<eProduct> datalist;
    Context context;


    public backgroundAsync(Context context)
    {
        this.context = context;
    }


    @Override
    protected String doInBackground(String... strings) {
        getdaTa();
        return null;
    }

    public void getdaTa(){
        ApiInterface apiInterface= ApiClient.getInstance().create(ApiInterface.class);
        apiInterface.getAllWea().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, retrofit2.Response<List<Product>> response) {


                if(response!=null && response.isSuccessful()) {
                    List<Product> products = response.body();

                    if (products != null&&products.size()>0) {
                        for (int i = 0; i <products.size(); i++) {
                            Log.d("OUTPUT", "" + products.get(i).getProductName());

                            eProduct product = new eProduct();
                            product.setLiked("0");
                            product.setType(products.get(i).getProductType());
                            product.setMail(products.get(i).getProductEmail());
                            product.setDesc(products.get(i).getProductDescription());
                            product.setContacts(products.get(i).getProductContacts());
                            product.setImage(products.get(i).getProductImage());
                            product.setName(products.get(i).getProductName());
                            product.setLocation(products.get(i).getProductLocation());
                            product.setPrice(products.get(i).getProductPrice());
                            product.setItemId(products.get(i).getProductId());

                            //adding to database

                                DatabaseClient.getmInstance(context).getAppDatabase()
                                        .productDao()
                                        .insertAll(product);

                        }
                        //get values
                    }else{
                        //show alert not get value
                    }
                }else{

                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });
    }
}

