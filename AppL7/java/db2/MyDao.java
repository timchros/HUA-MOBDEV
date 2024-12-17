package hua.dit.mobdev.ec.appl7.db2;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    public void insertData(MyTable myTable);

    @Insert
    public long insertDataVersion2(MyTable myTable);

    @Insert
    public void insertAllData(List<MyTable> myTable);

    @Query("SELECT * FROM my_table")
    public List<MyTable> getMyTableALL();

    @Query("SELECT * FROM my_table WHERE id >= :value")
    public List<MyTable> getMyTableAllWithGreaterIdValue(int value);

    @Query("SELECT id, string_value FROM my_table")
    public List<MyTableSubset> getMyTableAll2Columns();

}
