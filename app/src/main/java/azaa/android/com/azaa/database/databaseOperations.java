package azaa.android.com.azaa.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import azaa.android.com.azaa.adapters.Item;
import azaa.android.com.azaa.database.tableData.TableInfo;
import azaa.android.com.azaa.model.Product;

import java.util.ArrayList;
import java.util.List;

import static azaa.android.com.azaa.database.tableData.TableInfo.*;

public class databaseOperations extends SQLiteOpenHelper {



    public databaseOperations(Context context) {
        super(context, TableInfo.DATABASE_NAME, null, TableInfo.DATABASE_VERSION);
        Log.d("DATABASE OPERATION","Database Creation");
    }

    public databaseOperations(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {
        for(int i = 0;  i< CREAT_ALL_TABLE.length; i++){
            sdb.execSQL(CREAT_ALL_TABLE[i]);
        }
        Log.d("DATABASE OPERATION","Database Created Succesfully");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOTHES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JEW);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHONES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_SHOP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_WISHES);

        // create new tables
        onCreate(db);
    }
    public void insertData(databaseOperations dop, String _id, String _name, String _description, String _category, String _mail, String _phone, String _price, String _image, String t_Name)
    {
        SQLiteDatabase SQ = dop.getWritableDatabase();
        ContentValues CV = new ContentValues();
        CV.put(TableInfo.ID,_id);
        CV.put(TableInfo.ITEM_NAME,_name);
        CV.put(TableInfo.ITEM_DESC,_description);
        CV.put(TableInfo.ITEM_LOCATION,_category);
        CV.put(TableInfo.ITEM_CONTACT,_phone);
        CV.put(TableInfo.ITEM_MAIL,_mail);
        CV.put(TableInfo.ITEM_PRICE,_price);
        CV.put(TableInfo.ITEM_IMAGE,_image);

        Long k = SQ.insert(t_Name,null,CV);
        Log.d("DATABASE OPERATION","One Row Affected");
    }

    public Cursor getInfomation(SQLiteDatabase db){
        String [] projection = {TableInfo.ID,TableInfo.ITEM_NAME,TableInfo.ITEM_DESC,TableInfo.ITEM_PRICE,
        TableInfo.ITEM_MAIL,TableInfo.ITEM_CONTACT,TableInfo.ITEM_LOCATION,TableInfo.ITEM_IMAGE,};

        Cursor cursor = db.query(TableInfo.TABLE_CLOTHES,projection,null,null,null,null,null);
        return cursor;

    }
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TableInfo.TABLE_CLOTHES);
    }
    public List<Product> getCurrentdata(String tablename){
        // DataModel dataModel = new DataModel();
        List<Product> data=new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String [] projection = {TableInfo.ITEM_NAME,TableInfo.ITEM_DESC,TableInfo.ITEM_PRICE,TableInfo.ITEM_MAIL,TableInfo.ITEM_CONTACT,TableInfo.ITEM_LOCATION, TableInfo.ITEM_IMAGE};

        Cursor cursor = db.query(tablename,projection,null,null,null,null,null);;
        StringBuffer stringBuffer = new StringBuffer();
        Product items = null;
        if(cursor.moveToFirst()) {
            do {
                items = new Product(
                        cursor.getString(cursor.getColumnIndex(TableInfo.ITEM_CONTACT)),
                        cursor.getString(cursor.getColumnIndex(TableInfo.ITEM_DESC)),
                        cursor.getString(cursor.getColumnIndex(TableInfo.ITEM_MAIL)),
                        "2",
                        cursor.getString(cursor.getColumnIndex(TableInfo.ITEM_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(TableInfo.ITEM_LOCATION)),
                        cursor.getString(cursor.getColumnIndex(TableInfo.ITEM_PRICE)),
                        "v",
                        cursor.getString(cursor.getColumnIndex(TableInfo.ITEM_NAME)));

                ;
                data.add(items);
            }while (cursor.moveToNext());
        }

        for (Product mo:data ) {

            Log.i("Hellomo",""+mo.getProductId());
        }

        //

        return data;
    }

}
