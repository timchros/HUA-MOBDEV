package hua.dit.mobdev.ec.appl8.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SexDao {

    @Insert
    public long insert(Sex sex);

    @Insert
    public List<Long> insert(List<Sex> sexList);

    @Query("SELECT * FROM sex")
    public List<Sex> getSexALL();

    @Query("SELECT * FROM sex WHERE name == :sex_name")
    public Sex getSexByName(String sex_name);

}
