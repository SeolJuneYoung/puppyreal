package org.techtown.puppydiary;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.techtown.puppydiary.accountmenu.MoneyTab;
import org.techtown.puppydiary.calendarmenu.CalendarTab;
import org.techtown.puppydiary.kgmenu.KgTab;
import org.techtown.puppydiary.network.Data.MyinfoData;
import org.techtown.puppydiary.network.Response.MyinfoResponse;
import org.techtown.puppydiary.network.RetrofitClient;
import org.techtown.puppydiary.network.ServiceApi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MypuppyTab extends AppCompatActivity {
    ActionBar actionBar;

    private ServiceApi service;
    private Call<MyinfoResponse> infodata;

    //public static String ;
    // public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypuppy);

        //MoneyEdit.context = getApplicationContext();
        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffD6336B));
        getSupportActionBar().setTitle("댕댕이어리");
        actionBar.setIcon(R.drawable.white_puppy);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        service = RetrofitClient.getClient().create(ServiceApi.class);


        TextView textView = findViewById(R.id.textView);
        SpannableString content = new SpannableString("우리 집 댕댕이는요");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);

        Button calen = findViewById(R.id.calendar);
        calen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_calendar = new Intent(getApplicationContext(), CalendarTab.class); //일단 바로 검색결과 띄음
                startActivity(intent_calendar);
            }
        });

        Button kg = findViewById(R.id.kg);
        kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_kg = new Intent(getApplicationContext(), KgTab.class); //일단 바로 검색결과 띄음
                startActivity(intent_kg);
            }
        });

        Button money = findViewById(R.id.account);
        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_money = new Intent(getApplicationContext(), MoneyTab.class); //일단 바로 검색결과 띄음
                startActivity(intent_money);
            }
        });

        Button puppy = findViewById(R.id.puppy);
        puppy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_puppy = new Intent(getApplicationContext(), MypuppyTab.class); //일단 바로 검색결과 띄음
                startActivity(intent_puppy);
            }
        });


        // 설정아이콘 누르면 설정으로 넘어가게
        ImageView set_button = findViewById(R.id.set_button);
        set_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SetPuppy.class);
                startActivity(intent);
            }
        });

        // 사람 아이콘 누르면 비밀번호 재설정으로 넘어가게
        ImageView person = findViewById(R.id.pwd_set);
        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Pwd.class);
                startActivity(intent);
            }
        });

        final EditText puppy_name = findViewById(R.id.name_input);
        final EditText age_ = findViewById(R.id.age_input);
        final EditText birth_ = findViewById(R.id.bd_input);
        final RadioButton option_male = (RadioButton) findViewById(R.id.male);
        final RadioButton option_female = (RadioButton) findViewById(R.id.female);

        //mypuppyInfo();

        final Call<MyinfoResponse> getCall = service.Getmyinfo();
        getCall.enqueue(new Callback<MyinfoResponse>() {
            @Override
            public void onResponse(Call<MyinfoResponse> call, Response<MyinfoResponse> response) {
                if(response.isSuccessful()){
                    MyinfoResponse myinfo = response.body();
                    List<MyinfoResponse.Myinfo> my = myinfo.getData();

                    String result = "";

                    for(MyinfoResponse.Myinfo myinfo1 : my) {
                        puppy_name.setText(myinfo1.getPuppyname());
                        age_.setText("" + myinfo1.getAge());
                        birth_.setText(myinfo1.getBirth());

                        int gender = myinfo1.getGender();
                        if ( gender ==1 ){
                            option_male.setChecked(true);
                        }
                        else if ( gender == 2) {
                            option_female.setChecked(true);
                        }

                        // result = myinfo1.getPuppyname();
                        // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();;
                    }

                }
            }

            @Override
            public void onFailure(Call<MyinfoResponse> call, Throwable t) {

            }
        });
    }

}