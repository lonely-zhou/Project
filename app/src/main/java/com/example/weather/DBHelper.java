package com.example.weather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//数据库类
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        创建数据库表SQL
        String createCityTable = "create table city(cityId varchar(20) primary key,cityName varchar(20),adm1 varchar(20),adm2 varchar(20))";
        String createCityStateTable = "create table cityState(cityId varchar(20) primary key,cityName varchar(20))";
        //        执行
        sqLiteDatabase.execSQL(createCityTable);
        sqLiteDatabase.execSQL(createCityStateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
