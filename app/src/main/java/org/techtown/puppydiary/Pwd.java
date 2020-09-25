package org.techtown.puppydiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.techtown.puppydiary.accountmenu.MoneyTab;
import org.techtown.puppydiary.calendarmenu.CalendarTab;
import org.techtown.puppydiary.kgmenu.KgTab;
import org.techtown.puppydiary.network.Data.UpdatepwData;
import org.techtown.puppydiary.network.Response.EmailResponse;
import org.techtown.puppydiary.network.Response.UpdatepwResponse;
import org.techtown.puppydiary.network.RetrofitClient;
import org.techtown.puppydiary.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pwd extends AppCompatActivity {

    ActionBar actionBar;
    private ServiceApi service;
    Button finish;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd);

        //MoneyEdit.context = getApplicationContext();
        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffed426e));
        getSupportActionBar().setTitle("댕댕이어리");
        actionBar.setIcon(R.drawable.white_puppy);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        TextView textView = findViewById(R.id.textView);
        SpannableString content = new SpannableString("내 정보 수정");
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

        service = RetrofitClient.getClient().create(ServiceApi.class);


        final EditText email1 = findViewById(R.id.show_id);
        final EditText old_pwd = findViewById(R.id.old_pwd);
        final EditText pwd_new = findViewById(R.id.new_pwd);
        final EditText pwd_ck = findViewById(R.id.new_chk);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = sp.getString("TOKEN", "");
        final Call<EmailResponse> getCall = service.getEmail(token);
        getCall.enqueue(new Callback<EmailResponse>() {
            @Override
            public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {

                EmailResponse emailResponse = response.body();
                if(response.isSuccessful()){
                    email = emailResponse.getData();
                    email1.setText(email);
                }
            }

            @Override
            public void onFailure(Call<EmailResponse> call, Throwable t) {

            }
        });

        finish = findViewById(R.id.pwd_finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish.setBackgroundColor( Color.parseColor("#D6336B"));
                String email_add = email1.getText().toString();
                String old1 = old_pwd.getText().toString();
                String new1 = pwd_new.getText().toString();
                String new2 = pwd_ck.getText().toString();
                ChangePassword(new UpdatepwData(old1, new1, new2));
            }
        });

        // 완료 버튼 누르면 새로운 비밀번호 확인함.

        // 기존 비밀번호 맞는지 확인하고 새로운 비밀번호로 업데이트 하는 거 적어야함
/*
        final EditText old_pwd = findViewById(R.id.old_pwd);
        final EditText pwd_new = findViewById(R.id.new_pwd);
        final EditText pwd_ck = findViewById(R.id.new_chk);


        Button new_fsh = findViewById(R.id.pwd_finish);
        new_fsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old1 = old_pwd.getText().toString();
                String new1 = pwd_new.getText().toString();
                String new2 = pwd_ck.getText().toString();

                if ( old1.equals("") ){
                    Toast.makeText(getApplicationContext(), "기존 비밀번호를 입력하세요", Toast.LENGTH_LONG).show();
                }
                else if ( new1.equals("") ) {
                    Toast.makeText(getApplicationContext(), "새로운 비밀번호를 입력하세요", Toast.LENGTH_LONG).show();
                }
                else if ( new2.equals("") ) {
                    Toast.makeText(getApplicationContext(), "새로운 비밀번호 확인칸을 입력하세요", Toast.LENGTH_LONG).show();
                }
                else{
                    if (new1.equals(new2)) {
                        Intent intent = new Intent(getApplicationContext(), MypuppyTab.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "새로운 비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

 */
    }

    public void ChangePassword(UpdatepwData data){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String token = sp.getString("TOKEN", "");
        service.updatepw(token, data).enqueue(new Callback<UpdatepwResponse>() {
            @Override
            public void onResponse(Call<UpdatepwResponse> call, Response<UpdatepwResponse> response) {
                UpdatepwResponse result = response.body();
                finish.setBackgroundColor( Color.parseColor("#FDFAFA"));
                Toast.makeText(Pwd.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if(result.getSuccess() == true){
                    Intent intent_mypage = new Intent(getApplicationContext(), MypuppyTab.class);
                    startActivityForResult(intent_mypage, 2000);
                }
            }

            @Override
            public void onFailure(Call<UpdatepwResponse> call, Throwable t) {
                finish.setBackgroundColor( Color.parseColor("#FDFAFA"));
                Toast.makeText(Pwd.this, "비밀번호 변경 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("비밀번호 변경 에러 발생", t.getMessage());
            }
        });
    }
}