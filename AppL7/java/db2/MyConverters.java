package hua.dit.mobdev.ec.appl7.db2;

import androidx.room.TypeConverter;

import java.util.Date;

public class MyConverters {

    @TypeConverter
    public  Long dateToLong(Date date) {
        return (date != null) ? date.getTime() : null;
    }

    @TypeConverter
    public Date longToDate(Long longval) {
        return (longval != null) ? new Date(longval) : null;
    }

}
