package hua.dit.mobdev.ec.appl6.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyTableDAO {

    @Query("SELECT * FROM my_table")
    public List<MyTable> getMyTableObjALL();

    @Insert
    public void storeMyTableObj(MyTable myTableObj);

}
