package hua.dit.mobdev.ec.appl8.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sex")
public class Sex {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String name;

    public Sex(@NonNull String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Sex{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
