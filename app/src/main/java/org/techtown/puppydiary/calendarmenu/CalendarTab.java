package org.techtown.puppydiary.calendarmenu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.techtown.puppydiary.kgmenu.KgTab;
import org.techtown.puppydiary.MypuppyTab;
import org.techtown.puppydiary.R;
import org.techtown.puppydiary.accountmenu.MoneyTab;
import org.techtown.puppydiary.network.Data.calendar.ShowDayData;
import org.techtown.puppydiary.network.Response.calendar.ShowDayResponse;
import org.techtown.puppydiary.network.Response.calendar.ShowMonthResponse;
import org.techtown.puppydiary.network.RetrofitClient;
import org.techtown.puppydiary.network.ServiceApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarTab extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    ActionBar actionBar;
    public static String realmediname;
    // public static Context context;

    public static int SUNDAY = 1;
    public static int MONDAY = 2;
    public static int TUESDAY = 3;
    public static int WEDNSESDAY = 4;
    public static int THURSDAY = 5;
    public static int FRIDAY = 6;
    public static int SATURDAY = 7;


    int year = 0;
    int month = 0;
    int date = 0;
    int state_waterdrop;
    int state_injection;
    int showmonth_pos;
    String memo;
    String photo;

    //연 월 텍스트뷰
    private TextView tvDate;
    //그리드뷰
    private GridView gridView;
    //그리드뷰 어댑터
    private GridAdapter gridAdapter;
    //day 저장 리스트
    private ArrayList<DayInfo> dayList;

    Calendar mLastMonthCalendar;
    Calendar mCal;
    Calendar mNextMonthCalendar;

    Button pvs_button;
    Button nxt_button;

    int lastMonthStartDay;
    int dayOfMonth;
    int thisMonthLastDay;

    int getyear = 0;
    int getmonth = 0;

    private ServiceApi service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffD6336B));
        getSupportActionBar().setTitle("댕댕이어리");
        actionBar.setIcon(R.drawable.white_puppy) ;
        actionBar.setDisplayUseLogoEnabled(true) ;
        actionBar.setDisplayShowHomeEnabled(true) ;



        Button cal = findViewById(R.id.calendar);
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_calendar = new Intent(getApplicationContext(), CalendarTab.class);
                startActivity(intent_calendar);
            }
        });

        Button kg = findViewById(R.id.kg);
        kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_kg = new Intent(getApplicationContext(), KgTab.class);
                startActivity(intent_kg);
            }
        });

        Button money = findViewById(R.id.account1);
        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_money = new Intent(getApplicationContext(), MoneyTab.class);
                startActivity(intent_money);
            }
        });

        Button puppy = findViewById(R.id.puppy);
        puppy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_puppy = new Intent(getApplicationContext(), MypuppyTab.class);
                startActivity(intent_puppy);
            }
        });

        Intent intent_get = new Intent(getIntent());
        getyear = intent_get.getIntExtra("after_year", 0);
        getmonth = intent_get.getIntExtra("after_month", 0);

        tvDate = findViewById(R.id.tv_date);
        gridView = findViewById(R.id.gridview);

        pvs_button = findViewById(R.id.previous);
        nxt_button = findViewById(R.id.next);

        pvs_button.setOnClickListener(this);
        nxt_button.setOnClickListener(this);

        gridView.setOnItemClickListener(this);

        dayList = new ArrayList<DayInfo>();

        service = RetrofitClient.getClient().create(ServiceApi.class);

        mCal = Calendar.getInstance();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        getCalendar(mCal);

    }

    //캘린더 구현
    private void getCalendar(Calendar mCal) {

        dayList.clear();

        // 이번달 시작일의 요일을 구한다.
        dayOfMonth = mCal.get(Calendar.DAY_OF_WEEK);
        thisMonthLastDay = mCal.getActualMaximum(Calendar.DAY_OF_MONTH);

        mCal.add(Calendar.MONTH, -1);

        // 지난달의 마지막 일자를 구한다.
        lastMonthStartDay = mCal.getActualMaximum(Calendar.DAY_OF_MONTH);

        mCal.add(Calendar.MONTH, 1);

        lastMonthStartDay -= (dayOfMonth - 1) - 1;

        // 캘린더 타이틀(년월 표시)을 세팅
        tvDate.setText((mCal.get(Calendar.MONTH)+1) + "월");

        year = mCal.get(Calendar.YEAR);

        switch (mCal.get(Calendar.MONTH)){
            case 0: {month = 0; break;}
            case 1: {month = 1; break;}
            case 2: {month = 2; break;}
            case 3: {month = 3; break;}
            case 4: {month = 4; break;}
            case 5: {month = 5; break;}
            case 6: {month = 6; break;}
            case 7: {month = 7; break;}
            case 8: {month = 8; break;}
            case 9: {month = 9; break;}
            case 10: {month = 10; break;}
            case 11: {month = 11; break;}
            //case 12: {month = 12; break;}
        }



        DayInfo day;

        for (int i = 0; i < dayOfMonth - 1; i++) {
            int date = lastMonthStartDay + i;
            day = new DayInfo();
            day.setDay(Integer.toString(date));
            day.setInMonth(false);
            //System.out.println("lastmonthstartday : " + lastMonthStartDay + ", dayofmonth" + dayOfMonth);

            dayList.add(day);
        }
        for (int i = 1; i <= thisMonthLastDay; i++) {
            day = new DayInfo();
            day.setDay(Integer.toString(i));
            day.setInMonth(true);

            dayList.add(day);
        }
        for (int i = 1; i < 42 - (thisMonthLastDay + dayOfMonth - 1) + 1; i++) {
            day = new DayInfo();
            day.setDay(Integer.toString(i));
            day.setInMonth(false);
            dayList.add(day);
        }

        gridAdapter = new GridAdapter(getApplicationContext(), R.layout.item_calendar, dayList);
        gridView.setAdapter(gridAdapter);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        DayInfo day = dayList.get(position);
        date = position - dayOfMonth + 2;
        //해당 월에 해당하는 날짜일 때
        if(day.isInMonth()) {
            //Toast.makeText(getApplicationContext(),""+position, Toast.LENGTH_SHORT).show();
            ShowDay(new ShowDayData());
        }

    }

    //지난달, 다음달 구현
    public void onClick(View v) {
        if (v.getId()==R.id.previous){
            mCal.set(mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH), 1);
            mCal.add(Calendar.MONTH, -1);
            tvDate.setText((mCal.get(Calendar.MONTH)+1) + "월");
            getCalendar(mCal);
        } else if (v.getId()==R.id.next) {
            mCal.set(mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH), 1);
            mCal.add(Calendar.MONTH, +1);
            tvDate.setText((mCal.get(Calendar.MONTH)+1) + "월");
            getCalendar(mCal);
        }
    }

    // 그리드뷰 어댑터
    public class GridAdapter extends BaseAdapter {

        private ArrayList<DayInfo> mdayList;
        private Context mcontext;
        private int mresource;
        private LayoutInflater minflater;

        //int useridx = new SigninResponse().load(getApplicationContext());
        int useridx = 0;

        public GridAdapter(Context context, int textResource, ArrayList<DayInfo> dayList) {
            this.mcontext = context;
            this.mdayList = dayList;
            this.mresource = textResource;
            this.minflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mdayList.size();
        }

        @Override
        public Object getItem(int position) { return mdayList.get(showmonth_pos); }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            //final DBHelper_cal dbHelper = new DBHelper_cal(getApplicationContext(), "caltest.db", null, 1);
            DayInfo day = dayList.get(position);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = minflater.inflate(mresource, null);

                convertView.setLayoutParams(new GridView.LayoutParams(1440 / 7 + 1440 % 7, 300));

                holder = new ViewHolder();
                holder.tvItem = (TextView) convertView.findViewById(R.id.tv_item_gridview);
                holder.waterdrop = (ImageView) convertView.findViewById(R.id.waterdrop);
                holder.injection = (ImageView) convertView.findViewById(R.id.injection);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //기본세팅
            holder.waterdrop.setImageResource(R.drawable.waterdrop);
            holder.injection.setImageResource(R.drawable.injection);

            final ViewHolder finalHolder = holder;
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String token = sp.getString("TOKEN", "");
            service.showmonth(token, year, month).enqueue(new Callback<ShowMonthResponse>() {
                @Override
                public void onResponse(Call<ShowMonthResponse> call, Response<ShowMonthResponse> response) {
                    if (response.isSuccessful()) {
                        ShowMonthResponse showmonth = response.body();
                        List<ShowMonthResponse.ShowMonth> my = showmonth.getData();
                        if (my != null) {
                            for (int i = 0; i < my.size(); i++) {
                                showmonth_pos = my.get(i).getDate();
                                state_waterdrop = my.get(i).getWater();
                                state_injection = my.get(i).getInject();
                                if(showmonth_pos == (position-dayOfMonth+2)){
                                    if (state_waterdrop == 0 && state_injection == 0) {
                                        finalHolder.waterdrop.setImageResource(R.drawable.waterdrop);
                                        finalHolder.injection.setImageResource(R.drawable.injection);
                                    } else if (state_waterdrop == 1 && state_injection == 0) {
                                        //물방울만
                                        finalHolder.waterdrop.setImageResource(R.drawable.waterdrop_color);
                                        finalHolder.injection.setImageResource(R.drawable.injection);
                                    } else if (state_waterdrop == 0 && state_injection == 1) {
                                        //주사기만
                                        finalHolder.waterdrop.setImageResource(R.drawable.waterdrop);
                                        finalHolder.injection.setImageResource(R.drawable.injection_color);
                                    } else if (state_waterdrop == 1 && state_injection == 1) {
                                        finalHolder.waterdrop.setImageResource(R.drawable.waterdrop_color);
                                        finalHolder.injection.setImageResource(R.drawable.injection_color);
                                    }
                                }
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ShowMonthResponse> call, Throwable t) {
                    Toast.makeText(CalendarTab.this, "getcall 에러 발생", Toast.LENGTH_SHORT).show();
                    Log.e("getcall 에러 발생", t.getMessage());
                }
            });


            if (day != null) {
                holder.tvItem.setText(day.getDay());
                if (day.isInMonth()) {
                    if (position % 7 == 0) {
                        holder.tvItem.setTextColor(Color.RED);
                    } else {
                        holder.tvItem.setTextColor(Color.GRAY);
                    }

                } else {
                    holder.tvItem.setTextColor(Color.TRANSPARENT);
                    holder.waterdrop.setVisibility(View.INVISIBLE);
                    holder.injection.setVisibility(View.INVISIBLE);
                }
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView waterdrop;
            public ImageView injection;
            public TextView tvItem;

        }

    }

    //하루 정보 확인하고 저장
    public class DayInfo {
        private String day;
        private boolean inMonth;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        } //날짜저장

        public boolean isInMonth() {
            return inMonth;
        } //이번 달 날짜인지 확인

        public void setInMonth(boolean inMonth) {
            this.inMonth = inMonth;
        } //정보저장
    }

    private void ShowMonth(){

    }

    private void ShowDay(ShowDayData data){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = sp.getString("TOKEN", "");
        service.showday(token, year, month, date).enqueue(new Callback<ShowDayResponse>() {
            @Override
            public void onResponse(Call<ShowDayResponse> call, Response<ShowDayResponse> response) {
                ShowDayResponse result = response.body();
                //Toast.makeText(CalendarTab.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                Intent intent_day = new Intent(getApplicationContext(), CalendarDetail.class);
                //intent_day.putExtra("pos", position);
                intent_day.putExtra("year", year);
                intent_day.putExtra("month", month);
                intent_day.putExtra("date", date);
                startActivityForResult(intent_day, 2000);
            }

            @Override
            public void onFailure(Call<ShowDayResponse> call, Throwable t) {
                Toast.makeText(CalendarTab.this, "달력 일일 조회 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("달력 일일 조회 에러 발생", t.getMessage());
            }
        });
    }
}
