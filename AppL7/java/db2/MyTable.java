package hua.dit.mobdev.ec.appl7.db2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "my_table")
public class MyTable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "string_value")
    private String stringValue;

    @Ignore
    private int stringValueLength;

    @ColumnInfo(name = "integer_value")
    private Integer integerValue;

    @ColumnInfo(name = "date_value")
    private Date dateValue;

    public MyTable(String stringValue, Integer integerValue, Date dateValue) {
        this.stringValue = stringValue;
        this.stringValueLength = (stringValue != null) ? stringValue.length() : null;
        this.integerValue = integerValue;
        this.dateValue = dateValue;
    }

    public int getId() {
        return id;
    }

    public String getStringValue() {
        return stringValue;
    }

    public int getStringValueLength() {
        return stringValueLength;
    }

    public Integer getIntegerValue() {
        return integerValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public void setStringValueLength(int stringValueLength) {
        this.stringValueLength = stringValueLength;
    }

    public void setIntegerValue(Integer integerValue) {
        this.integerValue = integerValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    @Override
    public String toString() {
        return "MyTable{" +
                "id=" + id +
                ", stringValue='" + stringValue + '\'' +
                ", integerValue=" + integerValue +
                ", dateValue=" + dateValue +
                '}';
    }

}
