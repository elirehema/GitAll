package azaa.android.com.azaa.model;

import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    private String productId;
    @SerializedName("location")
    private String productLocation;
    @SerializedName("type")
    private String productType;
    @SerializedName("title")
    private String productName;
    @SerializedName("price")
    private String productPrice;
    @SerializedName("contact")
    private String productContacts;
    @SerializedName("image")
    private String productImage;
    @SerializedName("desc")
    private String productDescription;
    @SerializedName("email")
    private String productEmail;

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductLocation() {
        return this.productLocation;
    }

    public void setProductLocation(String productLocation) {
        this.productLocation = productLocation;
    }

    public String getProductType() {
        return this.productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return this.productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductContacts() {
        return this.productContacts;
    }

    public void setProductContacts(String productContacts) {
        this.productContacts = productContacts;
    }

    public String getProductImage() {
        return this.productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductDescription() {
        return this.productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductEmail() {
        return this.productEmail;
    }

    public void setProductEmail(String productEmail) {
        this.productEmail = productEmail;
    }

    public Product(String productContacts, String productDescription, String productEmail, String productId, String productImage, String productLocation, String productPrice, String productType, String productName) {
        this.productContacts = productContacts;
        this.productDescription = productDescription;
        this.productEmail = productEmail;
        this.productId = productId;
        this.productImage = productImage;
        this.productLocation = productLocation;
        this.productType = productType;
        this.productPrice = productPrice;
        this.productName = productName;

    }

    @Override
    public String toString() {
        return "Product{" + "id='" + productId + '\'' +
                ",title='" + productName + '\'' +
                ",contact='" + productContacts + '\'' +
                ",price='" + productPrice + '\'' +
                ",desc='" + productDescription + '\'' +
                ",image='" + productImage + '\'' +
                ",email='" + productEmail + '\'' +
                ",type='" + productType + '\'' +
                ",location='" + productLocation + '\'' +
                '}';
    }

}