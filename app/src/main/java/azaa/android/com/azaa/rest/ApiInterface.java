package azaa.android.com.azaa.rest;

import java.util.List;

import azaa.android.com.azaa.model.Product;
import azaa.android.com.azaa.roomApi.entity.eProduct;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("product.php?")
    Call<List<Product>> getAllWear(@Query("c") String id);

    @GET("getall.php")
    Call<List<Product>> getAllWea();

    @GET("frequent.php")
    Call<List<Product>> getData();
}
