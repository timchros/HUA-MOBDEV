package hua.dit.mobdev.ec.appl7.db2;

import androidx.room.ColumnInfo;

public class MyTableSubset {

    public int id;

    @ColumnInfo(name = "string_value")
    public String stringValue;

    @Override
    public String toString() {
        return "MyTableSubset{" +
                "id=" + id +
                ", stringValue='" + stringValue + '\'' +
                '}';
    }

}
