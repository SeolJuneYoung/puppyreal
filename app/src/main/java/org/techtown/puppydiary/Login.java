package org.techtown.puppydiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.techtown.puppydiary.calendarmenu.CalendarTab;
import org.techtown.puppydiary.network.Data.SigninData;
import org.techtown.puppydiary.network.Response.SigninResponse;
import org.techtown.puppydiary.network.RetrofitClient;
import org.techtown.puppydiary.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    ActionBar actionBar;
    private ServiceApi service;
    private TextView emailview;
    private TextView passwordview;
    Button button_lgn;
    Button tv_findpwd;
    Button tv_join;
    String email;
    String password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffD6336B));
        getSupportActionBar().setTitle("댕댕이어리");
        actionBar.setIcon(R.drawable.name);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        final Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        emailview = (TextView) findViewById(R.id.tv_emaillogin);
        passwordview = (TextView) findViewById(R.id.tv_passwordlogin);


        // 로그인 누르면 다음 화면으로 넘어가게
        button_lgn = findViewById(R.id.btn_login);
        button_lgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_lgn.setBackgroundColor( Color.parseColor("#D6336B"));
                email = emailview.getText().toString();
                password = passwordview.getText().toString();
                startLogin(new SigninData(email, password));
            }
        });

        tv_findpwd = findViewById(R.id.tv_findpassword);
        tv_findpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_findpwd = new Intent(getApplicationContext(), Findpwd.class);
                startActivityForResult(intent_findpwd, 2000);
            }
        });

        // 회원가입 누르면 회원 가입으로 넘어가게
        tv_join = findViewById(R.id.tv_join);
        tv_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_join = new Intent(getApplicationContext(), Signup.class);
                startActivityForResult(intent_join, 2000);
            }
        });
    }

    private void startLogin(SigninData data){
        service.usersignin(data).enqueue(new Callback<SigninResponse>() {
            @Override
            public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                SigninResponse result = response.body();
                Toast.makeText(Login.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                //로그인 성공
                if(result.getSuccess()==true){

                    String jwtToken = result.getJwtToken();

                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("TOKEN", jwtToken).apply();


                    //("Context-Type = application/

                    //int useridx = result.getUserIdx();

                    //result.save(getApplicationContext());

                    //DBHelper_user dbuser = new DBHelper_user(getApplicationContext(), "usertest.db", null, 1);

                    //각 db에 insert useridx
                    //dbuser.insert(useridx);

                    //달력 탭으로 시작
                    Intent intent_start = new Intent(getApplicationContext(), CalendarTab.class);
                    startActivityForResult(intent_start, 2000);
                }
                else{
                    button_lgn.setBackgroundColor( Color.parseColor("#FDFAFA"));
                }
            }

            @Override
            public void onFailure(Call<SigninResponse> call, Throwable t) {
                button_lgn.setBackgroundColor( Color.parseColor("#FDFAFA"));
                Toast.makeText(Login.this, "로그인 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("로그인 에러 발생", t.getMessage());
            }
        });
    }
}