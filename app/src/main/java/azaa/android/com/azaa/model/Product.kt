package azaa.android.com.azaa.model

import com.google.gson.annotations.SerializedName

data class Product(
        @field:SerializedName("contact")     var productContacts: String? = null,
        @field:SerializedName("desc")        var productDescription: String? = null,
        @field:SerializedName("email")       var productEmail: String? = null,
        @field:SerializedName("id")          var productId: String? = null,
        @field:SerializedName("image")       var productImage: String? = null,
        @field:SerializedName("location")    var productLocation: String? = null,
        @field:SerializedName("price")       var productPrice: String? = null,
        @field:SerializedName("type")        var productType: String? = null,
        @field:SerializedName("title")       var productName: String? = null
)