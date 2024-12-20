package hua.dit.mobdev.ec.appl8.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "user", foreignKeys = {
        @ForeignKey(
                entity = Sex.class,
                parentColumns = { "id" },
                childColumns = {"sex_id"}
        )})
public class User {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;

    public Integer age;

    public long sex_id;

    public User(String name, Integer age, long sex_id) {
        this.name = name;
        this.age = age;
        this.sex_id = sex_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex_id=" + sex_id +
                '}';
    }

}
