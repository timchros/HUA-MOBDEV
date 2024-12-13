package hua.dit.mobdev.ec.appl6.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "my_table")
public class MyTable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "my_data")
    public String myData;

    @Ignore
    public int myDataLength;

    @ColumnInfo(name = "my_number")
    public int myNumber;

    @ColumnInfo(name = "my_boolean")
    public boolean myBoolean;

    public MyTable(String myData, int myNumber, boolean myBoolean) {
        this.myData = myData;
        this.myDataLength = (myData != null) ? myData.length() : -1;
        this.myNumber = myNumber;
        this.myBoolean = myBoolean;
    }

    @Override
    public String toString() {
        return "MyTable{" +
                "id=" + id +
                ", myData='" + myData + '\'' +
                ", myDataLength=" + myDataLength +
                ", myNumber=" + myNumber +
                ", myBoolean=" + myBoolean +
                '}';
    }

}
