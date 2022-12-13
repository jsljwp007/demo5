package com.francis.app;
/**
 * 新建线程使用参数类
 * 用于向新线程中传递参数
 */
public class Parameter {
    public double db;
    public double db1;
    public double db2;
    public double db3;
    public double db4;
    public double db5;
    public String str;
    public String str1;
    public String str2;

    public Parameter(double db, double db1, double db2, double db3, double db4, double db5, String str, String str1,
            String str2) {
        this.db = db;
        this.db1 = db1;
        this.db2 = db2;
        this.db3 = db3;
        this.db4 = db4;
        this.db5 = db5;
        if (str == null || str.length() == 0)
            this.str = "";
        else
            this.str = str;
        if (str1 == null || str1.length() == 0)
            this.str1 = "";
        else
            this.str1 = str1;
        if (str2 == null || str2.length() == 0)
            this.str2 = "";
        else
            this.str2 = str2;
    }
}
