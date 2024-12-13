package hua.dit.mobdev.ec.appl6.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = { MyTable.class }, version = 1)
public abstract class MyDatabase extends RoomDatabase {

    public abstract MyTableDAO myTableDAO();

}
