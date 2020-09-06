package azaa.android.com.azaa.model

import com.google.gson.annotations.SerializedName

class Product(@field:SerializedName("contact") var productContacts: String, @field:SerializedName("desc") var productDescription: String, @field:SerializedName("email") var productEmail: String, @field:SerializedName("id") var productId: String, @field:SerializedName("image") var productImage: String, @field:SerializedName("location") var productLocation: String, @field:SerializedName("price") var productPrice: String, @field:SerializedName("type") var productType: String, @field:SerializedName("title") var productName: String) {

    override fun toString(): String {
        return "Product{" + "id='" + productId + '\'' +
                ",title='" + productName + '\'' +
                ",contact='" + productContacts + '\'' +
                ",price='" + productPrice + '\'' +
                ",desc='" + productDescription + '\'' +
                ",image='" + productImage + '\'' +
                ",email='" + productEmail + '\'' +
                ",type='" + productType + '\'' +
                ",location='" + productLocation + '\'' +
                '}'
    }
}