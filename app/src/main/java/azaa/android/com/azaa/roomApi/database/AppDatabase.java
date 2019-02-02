package azaa.android.com.azaa.roomApi.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import azaa.android.com.azaa.roomApi.Dao.productDao;
import azaa.android.com.azaa.roomApi.entity.eProduct;

@Database(entities = {eProduct.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract productDao productDao();
}

