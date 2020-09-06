package azaa.android.com.azaa.roomApi.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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
