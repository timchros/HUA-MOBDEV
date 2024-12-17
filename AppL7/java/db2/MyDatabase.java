package hua.dit.mobdev.ec.appl7.db2;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {MyTable.class}, version = 2)
@TypeConverters({MyConverters.class})
public abstract class MyDatabase extends RoomDatabase {

    public abstract MyDao myDao();

}
