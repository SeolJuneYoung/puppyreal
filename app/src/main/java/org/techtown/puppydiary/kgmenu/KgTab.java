package org.techtown.puppydiary.kgmenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.techtown.puppydiary.MypuppyTab;
import org.techtown.puppydiary.R;
import org.techtown.puppydiary.accountmenu.MoneyEdit;
import org.techtown.puppydiary.accountmenu.MoneyTab;
import org.techtown.puppydiary.calendarmenu.CalendarTab;
import org.techtown.puppydiary.network.Response.MyinfoResponse;
import org.techtown.puppydiary.network.Response.ShowKgResponse;
import org.techtown.puppydiary.network.Response.account.CheckAccountResponse;
import org.techtown.puppydiary.network.RetrofitClient;
import org.techtown.puppydiary.network.ServiceApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KgTab extends AppCompatActivity {
    private TextView tvDate;
    private  static Context context;
    ActionBar actionBar;
    Calendar mCal;
    public static String kg_month;
    public static int yearr=2020; // kgPopUp으로 넘어가면서 넘겨줄거임.
    public int flag; //년도가 바뀌었는지 확인
    double [] kgpuppy = new double[12]; // 전에 10년 후에 20년, 지금이 [9][]
    //double [] kgpuppy = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    private ServiceApi service;

    public static int year_kg = 2020;
    int month_kg = 0;
    double puppy_kg = 0;
    public static double jan_kg=0;
    public static double feb_kg=0;
    public static double mar_kg=0;
    public static double apr_kg=0;
    public static double may_kg=0;
    public static double jun_kg=0;
    public static double jul_kg=0;
    public static double aug_kg=0;
    public static double sep_kg=0;
    public static double oct_kg=0;
    public static double nov_kg=0;
    public static double dec_kg=0;

    Button jan;
    Button feb;
    Button mar;
    Button apr;
    Button may;
    Button jun;
    Button jul;
    Button aug;
    Button sep;
    Button oct;
    Button nov;
    Button dec;

    static boolean jan_pr = false;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = RetrofitClient.getClient().create(ServiceApi.class);

        setContentView(R.layout.activity_kg);
        Log.e("now year", String.valueOf(year_kg));

        tvDate = (TextView) findViewById(R.id.tv_date);
        mCal = Calendar.getInstance();


        // MoneyEdit.context = getApplicationContext();
        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffed426e));
        getSupportActionBar().setTitle("댕댕이어리");
        actionBar.setIcon(R.drawable.white_puppy) ;
        actionBar.setDisplayUseLogoEnabled(true) ;
        actionBar.setDisplayShowHomeEnabled(true) ;


        HorizontalBarChart chart = (HorizontalBarChart) findViewById(R.id.chart);

        chart.getXAxis().setDrawGridLines(false); //grid 선 없애주기
        XAxis x = chart.getXAxis(); //x라는 변수 만들어서 이용
        x.setPosition(XAxis.XAxisPosition.BOTTOM); //x축 왼쪽으로 옮기기
        x.setTextSize(0);
        x.setTextColor(0x00000000); //x 변수 안 보이게 설정


        ShowKg();


//        final ArrayList<String> labels = new ArrayList<>();
//        labels.add("Jan");
//        labels.add("Feb");
//        labels.add("Mar");
//        labels.add("Apr");
//        labels.add("May");
//        labels.add("Jun");
//        labels.add("Jul");
//        labels.add("Aug");
//        labels.add("Sep");
//        labels.add("Oct");
//        labels.add("Nov");
//        labels.add("Dec");


        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setCustomView(R.layout.custom_bar);
//        yearr = mCal.get(Calendar.YEAR);
////        year_kg = yearr;
        tvDate.setText(yearr + "년");

//        Intent intent = new Intent(getIntent());
//        flag = intent.getIntExtra("year", 0);
//
//        // 캘린더 타이틀(년월 표시)을 세팅한다.
//        if(flag ==0) {
//            yearr = mCal.get(Calendar.YEAR);
//            year_kg = yearr;
//            tvDate.setText(yearr + "년");
//        }
//        else {
//            //Intent intent = new Intent(getIntent());
//            yearr = flag; //intent.getIntExtra("year", 0);
//            year_kg = yearr;
//            tvDate.setText(yearr + "년");
//        }



//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        String token = sp.getString("TOKEN", "");

//        final Call<ShowKgResponse> getCall = service.showkg( year_kg);
//        getCall.enqueue(new Callback<ShowKgResponse>() {
//
//            @Override
//            public void onResponse(Call<ShowKgResponse> call, Response<ShowKgResponse> response) {
//                if (response.isSuccessful()) {
////    private void  ShowKg(int yearr) {
////        service.showkg(year_kg).enqueue(new Callback<ShowKgResponse>() {
////            @Override
////            public void onResponse(Call<ShowKgResponse> call, Response<ShowKgResponse> response) {
////
//                    ShowKgResponse showkg = response.body();
//                    List<ShowKgResponse.ShowKg> my = showkg.getData();
//
//                    String result = "";
//
//                    for (ShowKgResponse.ShowKg showkg1 : my) {
//                        puppy_kg = showkg1.getKg();
//                        Log.e("puppy kg", String.valueOf(puppy_kg));
//                        month_kg = showkg1.getMonth();
//                        Log.e("month kg", String.valueOf(month_kg));
//                        if (month_kg == 1) {
//                            jan_kg = puppy_kg;
//                            Log.e("jan" , String.valueOf(jan_kg));
//                        }
//                        else if (month_kg == 2) {
//                            feb_kg = puppy_kg;
//                            Log.e("feb" , String.valueOf(feb_kg));
//                        }
//                        else if (month_kg == 3) {
//                            mar_kg = puppy_kg;
//                            Log.e("mar" , String.valueOf(mar_kg));
//                        }
//                        else if (month_kg == 4) {
//                            apr_kg = puppy_kg;
//                            Log.e("apr" , String.valueOf(apr_kg));
//                        }
//                        else if (month_kg == 5) {
//                            may_kg = puppy_kg;
//                            Log.e("may" , String.valueOf(may_kg));
//                        }
//                        else if (month_kg == 6) {
//                            jun_kg = puppy_kg;
//                            Log.e("jun" , String.valueOf(jun_kg));
//                        }
//                        else if (month_kg == 7) {
//                            jul_kg = puppy_kg;
//                            Log.e("jul" , String.valueOf(jul_kg));
//                        }
//                        else if (month_kg == 8) {
//                            aug_kg = puppy_kg;
//                            Log.e("aug" , String.valueOf(aug_kg));
//                        }
//                        else if (month_kg == 9) {
//                            sep_kg = puppy_kg;
//                            Log.e("sep" , String.valueOf(sep_kg));
//                        }
//                        else if (month_kg == 10) {
//                            oct_kg = puppy_kg;
//                            Log.e("oct" , String.valueOf(oct_kg));
//                        }
//                        else if (month_kg == 11) {
//                            nov_kg = puppy_kg;
//                            Log.e("nov" , String.valueOf(nov_kg));
//                        }
//                        else if (month_kg == 12) {
//                            dec_kg = puppy_kg;
//                            Log.e("dec" , String.valueOf(dec_kg));
//                        }
//                    }
//                }}
//
//            @Override
//            public void onFailure(Call<ShowKgResponse> call, Throwable t) {
//                Toast.makeText(KgTab.this, "show kg 에러 발생", Toast.LENGTH_SHORT).show();
//                Log.e("show kg 에러 발생", t.getMessage());
//            }
//        });
//



        //하단탭 클릭 시
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

        Button money = findViewById(R.id.account);
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

        // Monthly KG upload BUTTON

        jan = findViewById(R.id.jan);
        jan.setOnClickListener(new View.OnClickListener() {
            //int jan = 0;
            @Override
            public void onClick(View v) {
                //jan.setSelected(true);
                jan.setBackgroundResource(R.drawable.button_pressed);
                Intent intent_kgjan = new Intent(getApplicationContext(), KgPopup.class);
                startActivity(intent_kgjan);
                kg_month = "January";
            }
        });
        feb = findViewById(R.id.feb);
        feb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feb.setBackgroundResource(R.drawable.button_pressed);
                Intent intent_kgfeb = new Intent(getApplicationContext(), KgPopup.class);
                startActivity(intent_kgfeb);
                kg_month = "February";
            }
        });
        mar = findViewById(R.id.mar);
        mar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mar.setBackgroundResource(R.drawable.button_pressed);
                Intent intent_kgmar = new Intent(getApplicationContext(), KgPopup.class);
                startActivity(intent_kgmar);
                kg_month = "March";
            }
        });
        apr = findViewById(R.id.apr);
        apr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apr.setBackgroundResource(R.drawable.button_pressed);
                Intent intent_kgapr = new Intent(getApplicationContext(), KgPopup.class);
                startActivity(intent_kgapr);
                kg_month = "April";
            }
        });
        may = findViewById(R.id.may);
        may.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                may.setBackgroundResource(R.drawable.button_pressed);
                Intent intent_kgmay = new Intent(getApplicationContext(), KgPopup.class);
                startActivity(intent_kgmay);
                kg_month = "May";
            }
        });
        jun = findViewById(R.id.jun);
        jun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jun.setBackgroundResource(R.drawable.button_pressed);
                Intent intent_kgjun = new Intent(getApplicationContext(), KgPopup.class);
                startActivity(intent_kgjun);
                kg_month = "June";
            }
        });
        jul = findViewById(R.id.jul);
        jul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jul.setBackgroundResource(R.drawable.button_pressed);
                Intent intent_kgjun = new Intent(getApplicationContext(), KgPopup.class);
                startActivity(intent_kgjun);
                kg_month = "July";
            }
        });
        aug= findViewById(R.id.aug);
        aug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aug.setBackgroundResource(R.drawable.button_pressed);
                Intent intent_kgjun = new Intent(getApplicationContext(), KgPopup.class);
                startActivity(intent_kgjun);
                kg_month = "August";
            }
        });
        sep = findViewById(R.id.sep);
        sep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sep.setBackgroundResource(R.drawable.button_pressed);
                Intent intent_kgjun = new Intent(getApplicationContext(), KgPopup.class);
                startActivity(intent_kgjun);
                kg_month = "September";
            }
        });
        oct = findViewById(R.id.oct);
        oct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oct.setBackgroundResource(R.drawable.button_pressed);
                Intent intent_kgjun = new Intent(getApplicationContext(), KgPopup.class);
                startActivity(intent_kgjun);
                kg_month = "October";
            }
        });
        nov = findViewById(R.id.nov);
        nov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nov.setBackgroundResource(R.drawable.button_pressed);
                Intent intent_kgjun = new Intent(getApplicationContext(), KgPopup.class);
                startActivity(intent_kgjun);
                kg_month = "November";
            }
        });
        dec = findViewById(R.id.dec);
        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dec.setBackgroundResource(R.drawable.button_pressed);
                Intent intent_kgjun = new Intent(getApplicationContext(), KgPopup.class);
                startActivity(intent_kgjun);
                kg_month = "December";
            }
        });



        //상단 화살표로 전년도 후년도 클릭
        Button pvs_button = findViewById(R.id.previous);
        Button nxt_button = findViewById(R.id.next);

        pvs_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearr = yearr - 1 ;
                tvDate.setText(yearr + "년");
//                flag = 1;
                year_kg = yearr;
                Log.e("year previous", String.valueOf(year_kg));
                ShowKg();

//                HorizontalBarChart chart = (HorizontalBarChart) findViewById(R.id.chart);
//
//
//                BarData data = new BarData(getDataSet());
//
//                final ArrayList<String> labels = new ArrayList<>();
//                labels.add("Jan");
//                labels.add("Feb");
//                labels.add("Mar");
//                labels.add("Apr");
//                labels.add("May");
//                labels.add("Jun");
//                labels.add("Jul");
//                labels.add("Aug");
//                labels.add("Sep");
//                labels.add("Oct");
//                labels.add("Nov");
//                labels.add("Dec");
//
//                // chart.setDescription();
//                chart.getXAxis().setDrawAxisLine(false);
//                chart.setData(data); // 아래 setData 불러옴
//                chart.setFitBars(true);
//                chart.animateXY(2000, 2000); //애니메이션 기능 추가
//                chart.invalidate(); //invalidate 해줘야 함

            }
        });
        nxt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearr = yearr + 1;
                tvDate.setText(yearr + "년");
//                flag = 1;
                year_kg = yearr;
                Log.e("year next", String.valueOf(year_kg));
                ShowKg();


//                HorizontalBarChart chart = (HorizontalBarChart) findViewById(R.id.chart);
//
//                chart.getXAxis().setDrawGridLines(false); //grid 선 없애주기
//                XAxis x = chart.getXAxis(); //x라는 변수 만들어서 이용
//                x.setPosition(XAxis.XAxisPosition.BOTTOM); //x축 왼쪽으로 옮기기
//                x.setTextSize(0);
//                x.setTextColor(0x00000000); //x 변수 안 보이게 설정
//
//
//
//                ArrayList<BarEntry> entries = new ArrayList();
//
//                BarDataSet dataset = new BarDataSet(entries,"체중(kg)");//속성값
//                dataset.setColors(ColorTemplate.COLORFUL_COLORS);//color random
//
//                BarData data = new BarData(getDataSet());
//
//                final ArrayList<String> labels = new ArrayList<>();
//                labels.add("Jan");
//                labels.add("Feb");
//                labels.add("Mar");
//                labels.add("Apr");
//                labels.add("May");
//                labels.add("Jun");
//                labels.add("Jul");
//                labels.add("Aug");
//                labels.add("Sep");
//                labels.add("Oct");
//                labels.add("Nov");
//                labels.add("Dec");
//
//                // chart.setDescription();
//                chart.getXAxis().setDrawAxisLine(false);
//                chart.setData(data); // 아래 setData 불러옴
//                chart.setFitBars(true);
//                chart.animateXY(2000, 2000); //애니메이션 기능 추가
//                chart.invalidate(); //invalidate 해줘야 함
            }
        });

        //Intent intent = new Intent(KgTab.this, KgPopup.class);
        //intent.putExtra("year", yearr);

        //가로 그래프 horizontalbarchart

//        if (flag ==0 || flag > 1) {
//


    }

//        final Call<ShowKgResponse> getCall = service.showkg(year_kg);
//        getCall.enqueue(new Callback<ShowKgResponse>() {
//
//            @Override
//            public void onResponse(Call<ShowKgResponse> call, Response<ShowKgResponse> response) {
//                if (response.isSuccessful()) {
////    private void  ShowKg(int yearr) {
////        service.showkg(year_kg).enqueue(new Callback<ShowKgResponse>() {
////            @Override
////            public void onResponse(Call<ShowKgResponse> call, Response<ShowKgResponse> response) {
////
//                    ShowKgResponse showkg = response.body();
//                    List<ShowKgResponse.ShowKg> my = showkg.getData();
//
//                    String result = "";
//
//                    for (ShowKgResponse.ShowKg showkg1 : my) {
//                        puppy_kg = showkg1.getKg();
//                        Log.e("puppy kg", String.valueOf(puppy_kg));
//                        month_kg = showkg1.getMonth();
//                        Log.e("month kg", String.valueOf(month_kg));
//                        if (month_kg == 1) {
//                            jan_kg = puppy_kg;
//                            Log.e("jan" , String.valueOf(jan_kg));
//                        }
//                    }
//            }}
//
//            @Override
//                public void onFailure(Call<ShowKgResponse> call, Throwable t) {
//                    Toast.makeText(KgTab.this, "show kg 에러 발생", Toast.LENGTH_SHORT).show();
//                    Log.e("show kg 에러 발생", t.getMessage());
//                }
//            });
//




//    private ArrayList<String> getXAxisValues() { //x축 라벨 추가
//        ArrayList<String> labels = new ArrayList();
//        labels.add("Jan");
//        labels.add("Feb");
//        labels.add("Mar");
//        labels.add("Apr");
//        labels.add("May");
//        labels.add("Jun");
//        labels.add("Jul");
//        labels.add("Aug");
//        labels.add("Sep");
//        labels.add("Oct");
//        labels.add("Nov");
//        labels.add("Dec");
//
//        return labels;
//
//    }

    private BarDataSet getDataSet() { //표시할 데이터 추가



//        final Call<ShowKgResponse> getCall = service.showkg(year_kg);
//        Log.e("getDataset year_kg", String.valueOf(year_kg));
//        getCall.enqueue(new Callback<ShowKgResponse>() {
//
//            @Override
//            public void onResponse(Call<ShowKgResponse> call, Response<ShowKgResponse> response) {
//                if (response.isSuccessful()) {
////    private void  ShowKg(int yearr) {
////        service.showkg(year_kg).enqueue(new Callback<ShowKgResponse>() {
////            @Override
////            public void onResponse(Call<ShowKgResponse> call, Response<ShowKgResponse> response) {
////
//                    ShowKgResponse showkg = response.body();
//                    List<ShowKgResponse.ShowKg> my = showkg.getData();
//
//                    String result = "";
//
//                    for (ShowKgResponse.ShowKg showkg1 : my) {
//                        puppy_kg = showkg1.getKg();
//                        Log.e("puppy kg", String.valueOf(puppy_kg));
//                        Log.e("puppy kg year", String.valueOf(year_kg));
//                        month_kg = showkg1.getMonth();
//                        Log.e("month kg", String.valueOf(month_kg));
//                        Log.e("month kg year", String.valueOf(year_kg));
//                        if (month_kg == 1) {
//                            jan_kg = puppy_kg;
//                            Log.e("jan" , String.valueOf(jan_kg));
//                        }
//                        else if (month_kg == 2) {
//                            feb_kg = puppy_kg;
//                            Log.e("feb" , String.valueOf(feb_kg));
//                        }
//                        else if (month_kg == 3) {
//                            mar_kg = puppy_kg;
//                            Log.e("mar" , String.valueOf(mar_kg));
//                        }
//                        else if (month_kg == 4) {
//                            apr_kg = puppy_kg;
//                            Log.e("apr" , String.valueOf(apr_kg));
//                        }
//                        else if (month_kg == 5) {
//                            may_kg = puppy_kg;
//                            Log.e("may" , String.valueOf(may_kg));
//                        }
//                        else if (month_kg == 6) {
//                            jun_kg = puppy_kg;
//                            Log.e("jun" , String.valueOf(jun_kg));
//                        }
//                        else if (month_kg == 7) {
//                            jul_kg = puppy_kg;
//                            Log.e("jul" , String.valueOf(jul_kg));
//                        }
//                        else if (month_kg == 8) {
//                            aug_kg = puppy_kg;
//                            Log.e("aug" , String.valueOf(aug_kg));
//                        }
//                        else if (month_kg == 9) {
//                            sep_kg = puppy_kg;
//                            Log.e("sep" , String.valueOf(sep_kg));
//                        }
//                        else if (month_kg == 10) {
//                            oct_kg = puppy_kg;
//                            Log.e("oct" , String.valueOf(oct_kg));
//                        }
//                        else if (month_kg == 11) {
//                            nov_kg = puppy_kg;
//                            Log.e("nov" , String.valueOf(nov_kg));
//                        }
//                        else if (month_kg == 12) {
//                            dec_kg = puppy_kg;
//                            Log.e("dec" , String.valueOf(dec_kg));
//                        }
//                    }
//                }}
//
//            @Override
//            public void onFailure(Call<ShowKgResponse> call, Throwable t) {
//                Toast.makeText(KgTab.this, "show kg 에러 발생", Toast.LENGTH_SHORT).show();
//                Log.e("show kg 에러 발생", t.getMessage());
//            }
//        });


        //final DBHelper_kg dbHelper = new DBHelper_kg(getApplicationContext(), "KG.db", null, 1);
        //double kgs[] = new double[12];


        //    for (int i = 0; i < 12; i++) {
        //        kgs[i] = dbHelper.getResult(yearr, i);
        //    }

        //Log.d("kg", "kg ." + puppyjan);
        ArrayList<BarEntry> entries = new ArrayList();
        entries.add(new BarEntry(12f, (float) jan_kg));
        Log.e("+jan" , String.valueOf(jan_kg));
        entries.add(new BarEntry(11f, (float) feb_kg));
        entries.add(new BarEntry(10f, (float) mar_kg));
        entries.add(new BarEntry(9f, (float) apr_kg));
        entries.add(new BarEntry(8f, (float) may_kg));
        entries.add(new BarEntry(7f, (float) jun_kg));
        entries.add(new BarEntry(6f, (float) jul_kg));
        entries.add(new BarEntry(5f, (float) aug_kg));
        entries.add(new BarEntry(4f, (float) sep_kg));
        entries.add(new BarEntry(3f, (float) oct_kg));
        entries.add(new BarEntry(2f, (float) nov_kg));
        entries.add(new BarEntry(1f, (float) dec_kg));
        // 해당 kg 값은 서버에서 받아서 그때마다 보여주게 해야함. 여기서 year에 맞는 kgs을 받아오면 됨.

        //entries.add(new BarEntry(1f, (float) dbHelper.getResult(31, yearr, 12)));
        //entries.add(new BarEntry(2f, (float) dbHelper.getResult(31, yearr, 11)));
        //entries.add(new BarEntry(3f, (float) dbHelper.getResult(31, yearr, 10)));
        //entries.add(new BarEntry(4f, (float) dbHelper.getResult(31, yearr, 9)));
        //entries.add(new BarEntry(5f, (float) dbHelper.getResult(31, yearr, 8)));
        //entries.add(new BarEntry(6f, (float) dbHelper.getResult(31, yearr, 7)));
        //entries.add(new BarEntry(7f, (float) dbHelper.getResult(31, yearr, 6)));
        //entries.add(new BarEntry(8f, (float) dbHelper.getResult(31, yearr, 5)));
        //entries.add(new BarEntry(9f, (float) dbHelper.getResult(31, yearr, 4)));
        //entries.add(new BarEntry(10f, (float) dbHelper.getResult(31, yearr, 3)));
        //entries.add(new BarEntry(11f, (float) dbHelper.getResult(31, yearr, 2)));
        //entries.add(new BarEntry(12f, (float) dbHelper.getResult(31, yearr, 1))); //아마 이게 january, 이건 확인해보면 암

        BarDataSet dataset = new BarDataSet(entries,"체중(kg)");//속성값
        dataset.setColors(ColorTemplate.VORDIPLOM_COLORS);//color random


        return dataset;
    }

    private void ShowKg() {
        Log.e("getDataset year_kg", String.valueOf(year_kg));
        service.showkg(year_kg).enqueue(new Callback<ShowKgResponse>() {
            @Override
            public void onResponse(Call<ShowKgResponse> call, Response<ShowKgResponse> response) {
                ShowKgResponse showkg = response.body();

                if (response.isSuccessful()) {
//    private void  ShowKg(int yearr) {
//        service.showkg(year_kg).enqueue(new Callback<ShowKgResponse>() {
//            @Override
//            public void onResponse(Call<ShowKgResponse> call, Response<ShowKgResponse> response) {
//
                    List<ShowKgResponse.ShowKg> my = showkg.getData();
                    jan_kg = 0;
                    feb_kg = 0;
                    mar_kg = 0;
                    apr_kg = 0;
                    may_kg = 0;
                    jun_kg = 0;
                    jul_kg = 0;
                    aug_kg = 0;
                    sep_kg = 0;
                    oct_kg = 0;
                    nov_kg = 0;
                    dec_kg = 0;
                    if (my != null) {
                        for (ShowKgResponse.ShowKg showkg1 : my) {
                            puppy_kg = showkg1.getKg();
                            month_kg = showkg1.getMonth();
                            if (month_kg == 1) {
                                jan_kg = puppy_kg;
                            } else if (month_kg == 2) {
                                feb_kg = puppy_kg;
                            } else if (month_kg == 3) {
                                mar_kg = puppy_kg;
                            } else if (month_kg == 4) {
                                apr_kg = puppy_kg;
                            } else if (month_kg == 5) {
                                may_kg = puppy_kg;
                            } else if (month_kg == 6) {
                                jun_kg = puppy_kg;
                            } else if (month_kg == 7) {
                                jul_kg = puppy_kg;
                            } else if (month_kg == 8) {
                                aug_kg = puppy_kg;
                            } else if (month_kg == 9) {
                                sep_kg = puppy_kg;
                            } else if (month_kg == 10) {
                                oct_kg = puppy_kg;
                            } else if (month_kg == 11) {
                                nov_kg = puppy_kg;
                            } else if (month_kg == 12) {
                                dec_kg = puppy_kg;
                            }
                            BarData data = new BarData(getDataSet());

                            HorizontalBarChart chart = (HorizontalBarChart) findViewById(R.id.chart);
                            // chart.setDescription();
                            chart.setTouchEnabled(false);
                            chart.getXAxis().setDrawAxisLine(false);
                            chart.setData(data); // 아래 setData 불러옴
                            chart.setFitBars(true);
                            chart.animateXY(2000, 2000); //애니메이션 기능 추가
                            chart.invalidate(); //invalidate 해줘야 함
                        }
                    } else {
                        jan_kg = 0;
                        feb_kg = 0;
                        mar_kg = 0;
                        apr_kg = 0;
                        may_kg = 0;
                        jun_kg = 0;
                        jul_kg = 0;
                        aug_kg = 0;
                        sep_kg = 0;
                        oct_kg = 0;
                        nov_kg = 0;
                        dec_kg = 0;

                        BarData data = new BarData(getDataSet());

                        HorizontalBarChart chart = (HorizontalBarChart) findViewById(R.id.chart);
                        // chart.setDescription();
                        chart.setTouchEnabled(false);
                        chart.getXAxis().setDrawAxisLine(false);
                        chart.setData(data); // 아래 setData 불러옴
                        chart.setFitBars(true);
                        chart.animateXY(2000, 2000); //애니메이션 기능 추가
                        chart.invalidate(); //invalidate 해줘야 함
                    }

                }

            }



            @Override
            public void onFailure(Call<ShowKgResponse> call, Throwable t) {
                Toast.makeText(KgTab.this, "show kg 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("show kg 에러 발생", t.getMessage());
            }
        });
    }

//    final Call<ShowKgResponse> getCall = service.showkg();
//        getCall.enqueue(new Callback<MyinfoResponse>() {
//        @Override
//        public void onResponse(Call< ShowKgResponse> call, Response<MyinfoResponse> response) {
//            if(response.isSuccessful()){
////    private void  ShowKg(int yearr) {
////        service.showkg(year_kg).enqueue(new Callback<ShowKgResponse>() {
////            @Override
////            public void onResponse(Call<ShowKgResponse> call, Response<ShowKgResponse> response) {
////
//                ShowKgResponse showkg = response.body();
//                List<ShowKgResponse.ShowKg> my = showkg.getData();
//
//                String result = "";
//
//                for(ShowKgResponse.ShowKg showkg1 : my) {
//                    puppy_kg = showkg1.getKg();
//                    month_kg = showkg1.getMonth();
//
//                    if(month_kg == 1){
//                        jan_kg = puppy_kg;
//                    }
//                }
//
////                if(response.isSuccessful()){
////                    idx = showkg.getData();
////                    Intent intent = new Intent(MoneyTab.this, MoneyEdit.class);
////                    intent.putExtra("idx", idx);
////                    intent.putExtra("year", year_money);
////                    intent.putExtra("month", month_money);
////                    intent.putExtra("day", day_money);
////                    intent.putExtra("memo", memo);
////                    intent.putExtra("price", price);
////                    startActivity(intent);
////                }
//            }
//
//            @Override
//            public void onFailure(Call<ShowKgResponse> call, Throwable t) {
//                Toast.makeText(KgTab.this, "show kg 에러 발생", Toast.LENGTH_SHORT).show();
//                Log.e("show kg 에러 발생", t.getMessage());
//            }
//        });
//    }


}