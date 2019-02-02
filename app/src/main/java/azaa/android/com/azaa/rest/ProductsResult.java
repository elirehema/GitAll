package azaa.android.com.azaa.rest;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import azaa.android.com.azaa.model.Product;

public class ProductsResult {

    @SerializedName("from_db")
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }
}
