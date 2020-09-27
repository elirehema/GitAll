package azaa.android.com.azaa.roomApi.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import azaa.android.com.azaa.roomApi.dao.productDao;
import azaa.android.com.azaa.roomApi.entity.eProduct;

@Database(entities = {eProduct.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract productDao productDao();
}

