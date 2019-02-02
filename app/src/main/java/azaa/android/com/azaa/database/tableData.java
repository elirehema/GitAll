package azaa.android.com.azaa.database;

import android.provider.BaseColumns;

public class tableData {

    public tableData()
    {

    }
    public static abstract class TableInfo implements BaseColumns
    {
        public static final String ID = "id";
        public static final int DATABASE_VERSION = 1;

        public static final String DATABASE_NAME = "u_r_a";

        //Database Tables

        public static final String TABLE_CLOTHES = "Clothes_";
        public static final String TABLE_SHOES = "Shoes_";
        public static final String TABLE_JEW = "Jewellery_";
        public static final String TABLE_PHONES = "Phones_";

        public static final String TABLE_USER = "user_info";
        public static final String TABLE_USER_STATUS= "login_status";
        public static final String TABLE_USER_SHOP = "user_shop";
        public static final String TABLE_USER_WISHES= "wish_list";

        //Products contents
        public static final String ITEM_NAME = "item_name";
        public static final String ITEM_PRICE = "item_price";
        public static final String ITEM_CONTACT = "item_contacts";
        public static final String ITEM_LOCATION = "item_location";
        public static final String ITEM_DESC = "item_description";
        public static final String ITEM_MAIL = "item_mail";
        public static final String ITEM_IMAGE = "item_image";

        //User profile contents
        public static final String USER_NAME = "user_name";
        public static final String USER_MAIL = "user_mail";
        public static final String USER_LOCATION = "user_location";
        public static final String USER_PHONE = "user_phone";
        public static final String USER_TOKEN = "user_token";

        //Table CLothes create Statment
        public static final String CREATE_TABLE_CLOTHES =
                "CREATE TABLE " + TABLE_CLOTHES + "("
                        + ID+ " TEXT PRIMARY KEY,"
                        + ITEM_NAME+ " TEXT,"
                        + ITEM_PRICE + " TEXT,"
                        + ITEM_CONTACT + " TEXT,"
                        + ITEM_DESC + " TEXT,"
                        + ITEM_MAIL + " TEXT,"
                        + ITEM_IMAGE + " TEXT,"
                        + ITEM_LOCATION + " TEXT"
                        + ")";
        //Create table Jewellery Statment
        public static final String CREATE_TABLE_JEW =
                "CREATE TABLE " + TABLE_JEW + "("
                        + ID+ " TEXT PRIMARY KEY,"
                        + ITEM_NAME+ " TEXT,"
                        + ITEM_PRICE + " TEXT,"
                        + ITEM_CONTACT + " TEXT,"
                        + ITEM_DESC + " TEXT,"
                        + ITEM_MAIL + " TEXT,"
                        + ITEM_IMAGE + " TEXT,"
                        + ITEM_LOCATION + " TEXT"
                        + ")";

        //Create table Phones Statment
        public static final String CREATE_TABLE_PHONE =
                "CREATE TABLE " + TABLE_PHONES + "("
                        + ID+ " TEXT PRIMARY KEY,"
                        + ITEM_NAME+ " TEXT,"
                        + ITEM_PRICE + " TEXT,"
                        + ITEM_CONTACT + " TEXT,"
                        + ITEM_DESC + " TEXT,"
                        + ITEM_MAIL + " TEXT,"
                        + ITEM_IMAGE + " TEXT,"
                        + ITEM_LOCATION+ " TEXT"
                        + ")";
        //Create table Shoes Statment
        public static final String CREATE_TABLE_SHOES =
                "CREATE TABLE " + TABLE_SHOES + "("
                        + ID+ " TEXT PRIMARY KEY,"
                        + ITEM_NAME+ " TEXT,"
                        + ITEM_PRICE + " TEXT,"
                        + ITEM_CONTACT + " TEXT,"
                        + ITEM_DESC + " TEXT,"
                        + ITEM_MAIL + " TEXT,"
                        + ITEM_IMAGE + " TEXT,"
                        + ITEM_LOCATION + " TEXT"
                        + ")";
        public static final String CREATE_TABLE_USER_SHOP =
                "CREATE TABLE " + TABLE_USER_SHOP + "("
                        + ID+ " TEXT PRIMARY KEY,"
                        + ITEM_NAME+ " TEXT,"
                        + ITEM_PRICE + " TEXT,"
                        + ITEM_CONTACT + " TEXT,"
                        + ITEM_DESC + " TEXT,"
                        + ITEM_MAIL + " TEXT,"
                        + ITEM_IMAGE + " TEXT,"
                        + ITEM_LOCATION + " TEXT"
                        + ")";
        public static final String CREATE_TABLE_USER =
                "CREATE TABLE " + TABLE_USER + "("
                        + ID+ " TEXT PRIMARY KEY,"
                        + USER_NAME+ " TEXT,"
                        + USER_MAIL + " TEXT,"
                        + USER_PHONE + " TEXT,"
                        + ITEM_DESC + " TEXT,"
                        + USER_LOCATION + " TEXT,"
                        + USER_TOKEN+ " TEXT"
                        + ")";

        public static final String CREATE_TABLE_WISH_LIST =
                "CREATE TABLE " + TABLE_USER_WISHES + "("
                        + ID+ " TEXT PRIMARY KEY,"
                        + ITEM_NAME+ " TEXT,"
                        + ITEM_PRICE + " TEXT,"
                        + ITEM_CONTACT + " TEXT,"
                        + ITEM_DESC + " TEXT,"
                        + ITEM_MAIL + " TEXT,"
                        + ITEM_IMAGE + " TEXT,"
                        + ITEM_LOCATION+ " TEXT"
                        + ")";
        public static final String CREAT_ALL_TABLE
                [] = {CREATE_TABLE_CLOTHES,
                        CREATE_TABLE_JEW,
                        CREATE_TABLE_PHONE,
                        CREATE_TABLE_USER_SHOP,
                        CREATE_TABLE_SHOES,
                        CREATE_TABLE_USER,
                CREATE_TABLE_WISH_LIST};


    }
}
