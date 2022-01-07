package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qweather.sdk.bean.IndicesBean;
import com.qweather.sdk.bean.WarningBean;
import com.qweather.sdk.bean.air.AirNowBean;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.IndicesType;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Unit;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherHourlyBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.HeConfig;
import com.qweather.sdk.view.QWeather;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView text, temp, feelsLike, category;
    private TextView main_weather24h_list_time, main_weather24h_list_temp, main_weather24h_list_pop;
    private TextView title, indices;
    private ImageView main_weather24h_list_icon;
    private RecyclerView weather24h_listView;
    private TextView main_weather7d_list_date, main_weather7d_list_tempMax, main_weather7d_list_tempMin, main_weather7d_list_precip;
    private ImageView main_weather7d_list_iconDay, main_weather7d_list_iconNight;
    private RecyclerView main_weather7d_list_recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        //显示 标题栏 图标
        Toolbar toolbar = findViewById(R.id.main_toolBar);
        toolbar.inflateMenu(R.menu.main_toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
        //和风天气SDK
        HeConfig.init("HE2108171636301752", "0d2e267715d045e8b99d6e41d7749486");
        HeConfig.switchToDevService();
        getWeather();

    }

    private void init() {
//        main_activity
        temp = findViewById(R.id.temp);
        feelsLike = findViewById(R.id.feelsLike);
        category = findViewById(R.id.category);
        text = findViewById(R.id.text);
        weather24h_listView = findViewById(R.id.weather24h_listView);
//        main_weather24h_list
//        main_weather24h_list_time = findViewById(R.id.main_weather24h_list_time);
//        main_weather24h_list_temp = findViewById(R.id.main_weather24h_list_temp);
//        main_weather24h_list_pop = findViewById(R.id.main_weather24h_list_pop);
//        main_weather24h_list_icon = findViewById(R.id.main_weather24h_list_icon);
//
        title = findViewById(R.id.title);
        indices = findViewById(R.id.indices);
        main_weather7d_list_recyclerView = findViewById(R.id.main_weather7d_list_recyclerView);
    }

    //返回按键 事件
    @Override
    public void onBackPressed() {
        finish();
    }

    //    获取天气
    private void getWeather() {
        System.out.println("进入getWeather");
//        Intent intent = getIntent();
//        String cityId = intent.getStringExtra("cityId");
        DBHelper dbHelper = new DBHelper(MainActivity.this, "cityState", null, 1);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor result = sqLiteDatabase.query("cityState", null, null, null, null, null, null);
        result.moveToNext();
        String cityId;
        String cityName;
        if (!result.isAfterLast()) {
            cityId = result.getString(0);
            cityName = result.getString(1);
        } else {
            cityId = "101010100";
            cityName = "默认城市";
        }
        QWeather.getWeatherNow(MainActivity.this, cityId, new QWeather.OnResultWeatherNowListener() {
            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable);
            }

            @Override
            public void onSuccess(WeatherNowBean weatherNowBean) {
                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因

                if (Code.OK == weatherNowBean.getCode()) {
                    WeatherNowBean.NowBaseBean now = weatherNowBean.getNow();
                    temp.setText(now.getTemp() + "°");
                    text.setText(now.getText() + ",");
                    feelsLike.setText("体感" + now.getFeelsLike() + "°");
                    getAirNow(cityId);
                    Toolbar toolbar = findViewById(R.id.main_toolBar);
                    toolbar.setTitle(cityName);
                    String iconCode = now.getIcon();
                    setCityWeatherIcon(iconCode);
                    getWeather24h(cityId);
                    getWeatherWarning(cityId);
                    getIndices(cityId);
                    getWeather7D(cityId);
                } else {
                    //在此查看返回数据失败的原因
                    Code code = weatherNowBean.getCode();
                }
            }
        });
    }

    //    空气质量
    private void getAirNow(String cityId) {
        QWeather.getAirNow(this, cityId, Lang.ZH_HANS, new QWeather.OnResultAirNowListener() {
            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable);
            }

            @Override
            public void onSuccess(AirNowBean airNowBean) {
                category.setText("空气质量 - " + airNowBean.getNow().getCategory());
            }
        });
    }

    //逐小时天气
    private void getWeather24h(String cityId) {
        QWeather.getWeather24Hourly(this, cityId, new QWeather.OnResultWeatherHourlyListener() {
            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error: " + throwable);
            }

            @Override
            public void onSuccess(WeatherHourlyBean weatherHourlyBean) {
                List<WeatherHourlyBean.HourlyBean> weather24h = weatherHourlyBean.getHourly();
                List<HashMap<String, Object>> weather24h_list_data = new ArrayList<>();
                Resources resources = MainActivity.this.getResources();
                for (int i = 0; i < weather24h.size(); i++) {
                    HashMap<String, Object> h = new HashMap<>();
                    h.put("main_weather24h_list_time", weather24h.get(i).getFxTime());
                    h.put("main_weather24h_list_temp", weather24h.get(i).getTemp());
                    h.put("main_weather24h_list_icon", resources.getIdentifier("w" + weather24h.get(i).getIcon(), "drawable", MainActivity.this.getPackageName()));
                    h.put("main_weather24h_list_pop", weather24h.get(i).getPop());
                    weather24h_list_data.add(h);
                }
//                MyAdapter myAdapter = new MyAdapter(MainActivity.this, weather24h_list_data, R.layout.main_weather24h_list,
//                        new String[]{"main_weather24h_list_time", "main_weather24h_list_temp", "main_weather24h_list_icon", "main_weather24h_list_pop"},
//                        new int[]{R.id.main_weather24h_list_time, R.id.main_weather24h_list_temp, R.id.main_weather24h_list_icon, R.id.main_weather24h_list_pop});

//                weather24h_listView.setAdapter(myAdapter);
                ReAdapter reAdapter = new ReAdapter(weather24h_list_data);
                weather24h_listView.setAdapter(reAdapter);
                weather24h_listView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false));
            }
        });
    }

    //逐天预报
    private void getWeather7D(String cityId) {
        QWeather.getWeather7D(MainActivity.this, cityId, new QWeather.OnResultWeatherDailyListener() {
            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable);
            }

            @Override
            public void onSuccess(WeatherDailyBean weatherDailyBean) {
                List<WeatherDailyBean.DailyBean> dailyBeans = weatherDailyBean.getDaily();
                List<HashMap<String, Object>> weather7d_list = new ArrayList<>();
                Resources resources = MainActivity.this.getResources();
                for (int i = 0; i < dailyBeans.size(); i++) {
                    HashMap<String, Object> h = new HashMap<>();
                    h.put("main_weather7d_list_date", dailyBeans.get(i).getFxDate());
                    h.put("main_weather7d_list_iconDay", resources.getIdentifier("w" + dailyBeans.get(i).getIconDay(), "drawable", MainActivity.this.getPackageName()));
                    h.put("main_weather7d_list_tempMax", dailyBeans.get(i).getTempMax());
                    h.put("main_weather7d_list_tempMin", dailyBeans.get(i).getTempMin());
                    h.put("main_weather7d_list_precip", dailyBeans.get(i).getPrecip());
                    h.put("main_weather7d_list_iconNight", resources.getIdentifier("w" + dailyBeans.get(i).getIconNight(), "drawable", MainActivity.this.getPackageName()));
                    weather7d_list.add(h);
                }
                ReAdapter_7d reAdapter_7d = new ReAdapter_7d(weather7d_list);
                main_weather7d_list_recyclerView.setAdapter(reAdapter_7d);
                main_weather7d_list_recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false));
            }
        });
    }

    //标题栏 点击
    private Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Intent intent = new Intent();
            switch (item.getItemId()) {
                case R.id.city:
                    intent.setClass(MainActivity.this, City.class);
                    startActivity(intent);
                    break;

            }
            return true;
        }
    };

    //    添加天气图片
    private void setCityWeatherIcon(String iconCode) {
        RelativeLayout main_relativeLayout = findViewById(R.id.main_relativeLayout);
        main_relativeLayout.removeAllViews();
        ImageView main_imageView = new ImageView(this);
        Resources resources = this.getResources();
        int id = resources.getIdentifier("w" + iconCode, "drawable", this.getPackageName());
        main_imageView.setImageResource(id);
        main_imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        main_relativeLayout.addView(main_imageView);
    }

    //    天气预警
    private void getWeatherWarning(String cityId) {
        QWeather.getWarning(this, cityId, Lang.ZH_HANS, new QWeather.OnResultWarningListener() {
            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable);
            }

            @Override
            public void onSuccess(WarningBean warningBean) {
                List<WarningBean.WarningBeanBase> warningBeanBaseList = warningBean.getWarningList();
                if (warningBeanBaseList == null || warningBeanBaseList.size() == 0) {
                    title.setText("更新于 " + warningBean.getBasic().getUpdateTime().split("T")[1].split("\\+")[0]);
                } else {
                    title.setText(warningBeanBaseList.get(0).getText());
                }


            }
        });
    }

    //生活指数
    private void getIndices(String cityId) {
        QWeather.getIndices1D(MainActivity.this, cityId, Lang.ZH_HANS, Collections.singletonList(IndicesType.COMF), new QWeather.OnResultIndicesListener() {
            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable);
            }

            @Override
            public void onSuccess(IndicesBean indicesBean) {
                List<IndicesBean.DailyBean> dailyBeans = indicesBean.getDailyList();
                indices.setText(dailyBeans.get(0).getText());
            }
        });
    }

    class VH extends RecyclerView.ViewHolder {
        public VH(@NonNull View itemView) {
            super(itemView);
            main_weather24h_list_time = itemView.findViewById(R.id.main_weather24h_list_time);
            main_weather24h_list_temp = itemView.findViewById(R.id.main_weather24h_list_temp);
            main_weather24h_list_pop = itemView.findViewById(R.id.main_weather24h_list_pop);
            main_weather24h_list_icon = itemView.findViewById(R.id.main_weather24h_list_icon);
            //        main_weather7d_list
            main_weather7d_list_date = itemView.findViewById(R.id.main_weather7d_list_date);
            main_weather7d_list_tempMax = itemView.findViewById(R.id.main_weather7d_list_tempMax);
            main_weather7d_list_tempMin = itemView.findViewById(R.id.main_weather7d_list_tempMin);
            main_weather7d_list_precip = itemView.findViewById(R.id.main_weather7d_list_precip);
            main_weather7d_list_iconDay = itemView.findViewById(R.id.main_weather7d_list_iconDay);
            main_weather7d_list_iconNight = itemView.findViewById(R.id.main_weather7d_list_iconNight);
        }
    }

    class ReAdapter extends RecyclerView.Adapter<VH> {
        private List<HashMap<String, Object>> data_list;

        public ReAdapter(List<HashMap<String, Object>> data_list) {
            this.data_list = data_list;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_weather24h_list, parent, false));
        }

        //解决数据显示错乱
        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            int time = Integer.parseInt(data_list.get(position).get("main_weather24h_list_time").toString().split("T")[1].split(":")[0]);
            String time_str;
            if (time > 12) {
                time -= 12;
                time_str = String.valueOf(time);

            } else {
                time_str = String.valueOf(time);
            }
            if (time_str.equals("0")) {
                time_str = "12";
            }
            main_weather24h_list_time.setText(time_str.toString() + "时");
            main_weather24h_list_temp.setText(data_list.get(position).get("main_weather24h_list_temp").toString() + "°");
            main_weather24h_list_pop.setText(" " + data_list.get(position).get("main_weather24h_list_pop").toString() + " %");
            main_weather24h_list_icon.setImageResource((Integer) data_list.get(position).get("main_weather24h_list_icon"));
        }

        @Override
        public int getItemCount() {
            return data_list.size();
        }
    }

    class ReAdapter_7d extends RecyclerView.Adapter<VH> {
        private List<HashMap<String, Object>> data_list;

        public ReAdapter_7d(List<HashMap<String, Object>> data_list) {
            this.data_list = data_list;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_weather7d_list, parent, false));
        }

        //解决数据显示错乱
        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            main_weather7d_list_date.setText(data_list.get(position).get("main_weather7d_list_date").toString().split("-")[2]);
            main_weather7d_list_iconDay.setImageResource((Integer) data_list.get(position).get("main_weather7d_list_iconDay"));
            main_weather7d_list_tempMax.setText(data_list.get(position).get("main_weather7d_list_tempMax").toString()+"°");
            main_weather7d_list_tempMin.setText(data_list.get(position).get("main_weather7d_list_tempMin").toString()+"°");
            main_weather7d_list_precip.setText(data_list.get(position).get("main_weather7d_list_precip").toString());
            main_weather7d_list_iconNight.setImageResource((Integer) data_list.get(position).get("main_weather7d_list_iconNight"));
        }

        @Override
        public int getItemCount() {
            return data_list.size();
        }
    }
}