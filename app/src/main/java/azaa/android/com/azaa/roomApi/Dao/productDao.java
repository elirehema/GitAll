package azaa.android.com.azaa.roomApi.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import azaa.android.com.azaa.roomApi.entity.eProduct;

@Dao
public interface productDao {

    @Query("SELECT * FROM products WHERE item_liked = :s")
    List<eProduct> getAll(String s);

    @Query("UPDATE products SET item_liked=:state WHERE item_id = :id")
    void updateItem(String state, String id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(eProduct product);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(eProduct product);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllOrders(List<eProduct> order);




    @Delete
    void delete(eProduct product);

    @Update
    void update(eProduct product);
}
