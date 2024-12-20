package hua.dit.mobdev.ec.appl8.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Sex.class, User.class}, version = 1)
public abstract class UserDB extends RoomDatabase {

    public abstract SexDao sexDao();

    public abstract UserDao userDao();

}
