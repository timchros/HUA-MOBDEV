package hua.dit.mobdev.ec.appl8.list;

public class MyData {

    public String str1;
    public String str2;
    public Integer intValue;

    public MyData(String str1, String str2, Integer intValue) {
        this.str1 = str1;
        this.str2 = str2;
        this.intValue = intValue;
    }

    @Override
    public String toString() {
        return "MyData{" +
                "str1='" + str1 + '\'' +
                ", str2='" + str2 + '\'' +
                ", intValue=" + intValue +
                '}';
    }

}
