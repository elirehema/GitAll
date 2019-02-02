package azaa.android.com.azaa.roomApi.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import static azaa.android.com.azaa.network.constants.*;

@Entity(tableName = "products", indices = {@Index(value = { ITEM_ID },
        unique = true)})
public class eProduct implements Serializable {
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name =  ITEM_NAME)
    private String name;

    @ColumnInfo(name =  ITEM_PRICE)
    private String price;



    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContacts() {
        return this.contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLiked() {
        return this.liked;
    }

    public void setLiked(String liked) {
        this.liked = liked;
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @ColumnInfo(name = ITEM_CONTACT)
    private String contacts;

    @ColumnInfo(name = ITEM_LOCATION)
    private String location;

    @ColumnInfo(name = ITEM_DESC)
    private String desc;

    @ColumnInfo(name = ITEM_MAIL)
    private String mail;

    @ColumnInfo(name = ITEM_IMAGE)
    private String image;

    @ColumnInfo(name = ITEM_TYPE)
    private String type;

    @ColumnInfo(name = ITEM_LIKED)
    private String liked;



    @ColumnInfo(name = ITEM_ID)
    private String itemId;

}
