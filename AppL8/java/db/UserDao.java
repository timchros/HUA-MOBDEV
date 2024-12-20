package hua.dit.mobdev.ec.appl8.db;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    public long storeData(User user);

    @Query("SELECT * FROM user")
    public List<User> getUserALL();

    @Query("SELECT u.name, u.age, s.name as sex FROM user u, sex s WHERE u.sex_id = s.id")
    public List<UserWithSex> getUserWithSexList();

    @Query("SELECT u.name, u.age, s.name as sex FROM user u, sex s WHERE u.sex_id = s.id")
    public Cursor getUserWithSexCursor();

}
