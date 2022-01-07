package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class City extends AppCompatActivity {
    private Toolbar city_toolbar;
    private ListView city_listView;
    private final String CITYCN = "中国";
    List<HashMap<String, Object>> city_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        init();
        //标题栏事件
        setCityToolBar();
        //获取城市数据
        getCityData();
        deleteCity();
    }

    private void init() {
        city_listView = findViewById(R.id.city_listView);
        city_list = new ArrayList<>();
    }

    //返回键 事件
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(City.this, MainActivity.class);
        startActivity(intent);
    }

    //获取城市数据
    private void getCityData() {

        city_list.clear();

        DBHelper dbHelper = new DBHelper(City.this, "city", null, 1);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor result = sqLiteDatabase.query("city", null, null, null, null, null, null);


        result.moveToNext();
        while (!result.isAfterLast()) {
            HashMap<String, Object> h = new HashMap<>();
            h.put("cityId", result.getString(0));
            h.put("city_name", result.getString(1));
            h.put("city_country", CITYCN);
            h.put("city_adm1", result.getString(2));
            h.put("city_adm2", result.getString(3));
            city_list.add(h);
            result.moveToNext();
        }
        result.close();
        sqLiteDatabase.close();


        MyAdapter myAdapter = new MyAdapter(City.this, city_list, R.layout.city_list,
                new String[]{"city_name", "city_country", "city_adm1", "city_adm2"},
                new int[]{R.id.city_name, R.id.city_country, R.id.city_adm1, R.id.city_adm2});


        city_listView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        city_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(City.this, MainActivity.class);
//                禁止MaibActivity返回City
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                String[] str = city_list.get(i).toString().split(",");
                DBHelper dbHelper1 = new DBHelper(City.this, "cityState", null, 1);
                SQLiteDatabase sqLiteDatabase1 = dbHelper1.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("cityId", str[2].split("=")[1]);
                contentValues.put("cityName", str[0].split("=")[1]);
                sqLiteDatabase1.delete("cityState", null, null);
                sqLiteDatabase1.insert("cityState", null, contentValues);
//                intent.putExtra("cityId", str[2].split("=")[1]);
                startActivity(intent);
                sqLiteDatabase1.close();
            }
        });
    }

    //删除城市数据
    private void deleteCity() {
        city_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DBHelper dbHelper1 = new DBHelper(City.this, "cityState", null, 1);
                SQLiteDatabase sqLiteDatabase1 = dbHelper1.getWritableDatabase();
                String cityIdState;
                Cursor result = sqLiteDatabase1.query("cityState", null, "cityId", null, null, null, null);
                result.moveToNext();
                cityIdState = result.getString(0);

                String[] cityId = {city_list.get(i).get("cityId").toString()};
                if (cityIdState.equals(cityId[0])) {
                    Toast.makeText(City.this, "默认城市不能删除", Toast.LENGTH_SHORT).show();
                } else {
                    DBHelper dbHelper = new DBHelper(City.this, "city", null, 1);
                    SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
                    sqLiteDatabase.delete("city", "cityId=?", cityId);
                    getCityData();
                }

                return true;
            }
        });
    }

//    class MyAdapter extends SimpleAdapter {
//        public MyAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
//            super(context, data, resource, from, to);
//        }
//
//    }

    //标题栏 点击事件
    private void setCityToolBar() {
        city_toolbar = findViewById(R.id.city_toolBar);
        city_toolbar.inflateMenu(R.menu.city_toolbar);
        city_toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.search_icon) {
                    Intent intent = new Intent();
//                    跳转到搜索页面
                    intent.setClass(City.this, CitySearch.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

}