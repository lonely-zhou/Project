package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Range;
import com.qweather.sdk.bean.geo.GeoBean;
import com.qweather.sdk.view.QWeather;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CitySearch extends AppCompatActivity {
    SearchView city_search_searchView;
    List<HashMap<String, Object>> city_search_list;
    ListView city_search_listView;
    TextView city_name_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_search);
        getCityData();

    }

    //获取城市信息
    private void getCityData() {
        city_search_searchView = findViewById(R.id.city_search);
        city_search_searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                QWeather.getGeoCityLookup(CitySearch.this, newText, Range.CN, 10, Lang.ZH_HANS, new QWeather.OnResultGeoListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println(throwable);
                    }

                    @Override
                    public void onSuccess(GeoBean geoBean) {
                        List<GeoBean.LocationBean> city_data = geoBean.getLocationBean();

                        city_search_list = new ArrayList<>();
                        for (int i = 0; i < city_data.size(); i++) {
                            HashMap<String, Object> h = new HashMap<>();
                            h.put("city_name", city_data.get(i).getName());
                            h.put("city_country", city_data.get(i).getCountry());
                            h.put("city_adm1", city_data.get(i).getAdm1());
                            h.put("city_adm2", city_data.get(i).getAdm2());
                            city_search_list.add(h);
                        }

                        city_search_listView = findViewById(R.id.city_search_listView);
                        MyAdapter myAdapter = new MyAdapter(CitySearch.this, city_search_list, R.layout.city_search_list,
                                new String[]{"city_name", "city_country", "city_adm1", "city_adm2"},
                                new int[]{R.id.city_name, R.id.city_country, R.id.city_adm1, R.id.city_adm2});
                        city_search_listView.setAdapter(myAdapter);
                        city_search_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                //添加到数据库
                                DBHelper dbHelper = new DBHelper(CitySearch.this, "city", null, 1);
                                SQLiteDatabase database = dbHelper.getWritableDatabase();
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("cityId", city_data.get(i).getId());
                                contentValues.put("cityName", city_data.get(i).getName());
                                contentValues.put("adm1", city_data.get(i).getAdm1());
                                contentValues.put("adm2", city_data.get(i).getAdm2());
                                database.insert("city", null, contentValues);
//                                database.close();

                                Intent intent = new Intent();
                                intent.setClass(CitySearch.this, City.class);
                                startActivity(intent);
                            }
                        });
                    }

                    class MyAdapter extends SimpleAdapter {
                        public MyAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
                            super(context, data, resource, from, to);
                        }

                    }
                });

                return false;
            }
        });
    }

}