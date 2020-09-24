package org.techtown.puppydiary.kgmenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.techtown.puppydiary.MypuppyTab;
import org.techtown.puppydiary.R;
import org.techtown.puppydiary.accountmenu.MoneyTab;
import org.techtown.puppydiary.calendarmenu.CalendarTab;

import java.util.ArrayList;
import java.util.Calendar;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

public class KgTab extends AppCompatActivity {
    private TextView tvDate;
    private  static Context context;
    ActionBar actionBar;
    Calendar mCal;
    public static String kg_month;
    public static int yearr; // kgPopUp으로 넘어가면서 넘겨줄거임.
    public int flag; //년도가 바뀌었는지 확인
    double [] kgpuppy = new double[12]; // 전에 10년 후에 20년, 지금이 [9][]
    //double [] kgpuppy = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

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
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kg);

        tvDate = (TextView) findViewById(R.id.tv_date);
        mCal = Calendar.getInstance();

        // MoneyEdit.context = getApplicationContext();
        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffD6336B));
        getSupportActionBar().setTitle("댕댕이어리");
        actionBar.setIcon(R.drawable.white_puppy) ;
        actionBar.setDisplayUseLogoEnabled(true) ;
        actionBar.setDisplayShowHomeEnabled(true) ;

        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setCustomView(R.layout.custom_bar);


        Intent intent = new Intent(getIntent());
        flag = intent.getIntExtra("year", 0);

        // 캘린더 타이틀(년월 표시)을 세팅한다.
        if(flag ==0) {
            yearr = mCal.get(Calendar.YEAR);
            tvDate.setText(yearr + "년");
        }
        else {
            //Intent intent = new Intent(getIntent());
            yearr = flag; //intent.getIntExtra("year", 0);
            tvDate.setText(yearr + "년");
        }


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
                flag = 1;

                HorizontalBarChart chart = (HorizontalBarChart) findViewById(R.id.chart);

                chart.getXAxis().setDrawGridLines(false); //grid 선 없애주기
                XAxis x = chart.getXAxis(); //x라는 변수 만들어서 이용
                x.setPosition(XAxis.XAxisPosition.BOTTOM); //x축 왼쪽으로 옮기기
                x.setTextSize(0);
                x.setTextColor(0x00000000); //x 변수 안 보이게 설정



                ArrayList<BarEntry> entries = new ArrayList();

                BarDataSet dataset = new BarDataSet(entries,"체중(kg)");//속성값
                dataset.setColors(ColorTemplate.COLORFUL_COLORS);//color random

                BarData data = new BarData(getDataSet());

                final ArrayList<String> labels = new ArrayList<>();
                labels.add("Jan");
                labels.add("Feb");
                labels.add("Mar");
                labels.add("Apr");
                labels.add("May");
                labels.add("Jun");
                labels.add("Jul");
                labels.add("Aug");
                labels.add("Sep");
                labels.add("Oct");
                labels.add("Nov");
                labels.add("Dec");

                // chart.setDescription();
                chart.getXAxis().setDrawAxisLine(false);
                chart.setData(data); // 아래 setData 불러옴
                chart.setFitBars(true);
                chart.animateXY(2000, 2000); //애니메이션 기능 추가
                chart.invalidate(); //invalidate 해줘야 함

            }
        });
        nxt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearr = yearr + 1;
                tvDate.setText(yearr + "년");
                flag = 1;

                HorizontalBarChart chart = (HorizontalBarChart) findViewById(R.id.chart);

                chart.getXAxis().setDrawGridLines(false); //grid 선 없애주기
                XAxis x = chart.getXAxis(); //x라는 변수 만들어서 이용
                x.setPosition(XAxis.XAxisPosition.BOTTOM); //x축 왼쪽으로 옮기기
                x.setTextSize(0);
                x.setTextColor(0x00000000); //x 변수 안 보이게 설정



                ArrayList<BarEntry> entries = new ArrayList();

                BarDataSet dataset = new BarDataSet(entries,"체중(kg)");//속성값
                dataset.setColors(ColorTemplate.COLORFUL_COLORS);//color random

                BarData data = new BarData(getDataSet());

                final ArrayList<String> labels = new ArrayList<>();
                labels.add("Jan");
                labels.add("Feb");
                labels.add("Mar");
                labels.add("Apr");
                labels.add("May");
                labels.add("Jun");
                labels.add("Jul");
                labels.add("Aug");
                labels.add("Sep");
                labels.add("Oct");
                labels.add("Nov");
                labels.add("Dec");

                // chart.setDescription();
                chart.getXAxis().setDrawAxisLine(false);
                chart.setData(data); // 아래 setData 불러옴
                chart.setFitBars(true);
                chart.animateXY(2000, 2000); //애니메이션 기능 추가
                chart.invalidate(); //invalidate 해줘야 함
            }
        });

        //Intent intent = new Intent(KgTab.this, KgPopup.class);
        //intent.putExtra("year", yearr);

        //가로 그래프 horizontalbarchart

        if (flag ==0 || flag > 1) {

            HorizontalBarChart chart = (HorizontalBarChart) findViewById(R.id.chart);

            chart.getXAxis().setDrawGridLines(false); //grid 선 없애주기
            XAxis x = chart.getXAxis(); //x라는 변수 만들어서 이용
            x.setPosition(XAxis.XAxisPosition.BOTTOM); //x축 왼쪽으로 옮기기
            x.setTextSize(0);
            x.setTextColor(0x00000000); //x 변수 안 보이게 설정


            ArrayList<BarEntry> entries = new ArrayList();

            BarDataSet dataset = new BarDataSet(entries, "체중(kg)");//속성값
            dataset.setColors(ColorTemplate.COLORFUL_COLORS);//color random

            BarData data = new BarData(getDataSet());

            final ArrayList<String> labels = new ArrayList<>();
            labels.add("Jan");
            labels.add("Feb");
            labels.add("Mar");
            labels.add("Apr");
            labels.add("May");
            labels.add("Jun");
            labels.add("Jul");
            labels.add("Aug");
            labels.add("Sep");
            labels.add("Oct");
            labels.add("Nov");
            labels.add("Dec");

            // chart.setDescription();
            chart.getXAxis().setDrawAxisLine(false);
            chart.setData(data); // 아래 setData 불러옴
            chart.setFitBars(true);
            chart.animateXY(2000, 2000); //애니메이션 기능 추가
            chart.invalidate(); //invalidate 해줘야 함
        }
    }



    private ArrayList<String> getXAxisValues() { //x축 라벨 추가
        ArrayList<String> labels = new ArrayList();
        labels.add("Jan");
        labels.add("Feb");
        labels.add("Mar");
        labels.add("Apr");
        labels.add("May");
        labels.add("Jun");
        labels.add("Jul");
        labels.add("Aug");
        labels.add("Sep");
        labels.add("Oct");
        labels.add("Nov");
        labels.add("Dec");

        return labels;

    }

    private BarDataSet getDataSet() { //표시할 데이터 추가

        final DBHelper_kg dbHelper = new DBHelper_kg(getApplicationContext(), "KG.db", null, 1);
        //double kgs[] = new double[12];


        //    for (int i = 0; i < 12; i++) {
        //        kgs[i] = dbHelper.getResult(yearr, i);
        //    }

        //Log.d("kg", "kg ." + puppyjan);

        ArrayList<BarEntry> entries = new ArrayList();
        entries.add(new BarEntry(1f, 0));
        entries.add(new BarEntry(2f, 0));
        entries.add(new BarEntry(3f, 0));
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(5f, 0));
        entries.add(new BarEntry(6f, 0));
        entries.add(new BarEntry(7f, 0));
        entries.add(new BarEntry(8f, 0));
        entries.add(new BarEntry(9f, 0));
        entries.add(new BarEntry(10f, 0));
        entries.add(new BarEntry(11f, 0));
        entries.add(new BarEntry(12f, 0));
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

}