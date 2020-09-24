package org.techtown.puppydiary;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.techtown.puppydiary.network.Data.FindpwData;
import org.techtown.puppydiary.network.Response.FindpwResponse;
import org.techtown.puppydiary.network.Response.SigninResponse;
import org.techtown.puppydiary.network.Response.SignupResponse;
import org.techtown.puppydiary.network.RetrofitClient;
import org.techtown.puppydiary.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Findpwd extends AppCompatActivity {

    ActionBar actionBar;

    private ServiceApi service;
    TextView tv_email;
    Button sendemail;
    Button finish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpwd);

        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffD6336B));
        getSupportActionBar().setTitle("댕댕이어리");
        actionBar.setIcon(R.drawable.white_puppy);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        service = RetrofitClient.getClient().create(ServiceApi.class);

        tv_email = (TextView) findViewById(R.id.show_id);
        sendemail = findViewById(R.id.btn_sendemail);
        finish = findViewById(R.id.pwd_finish);


        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = tv_email.getText().toString();
                SendEmail(new FindpwData(email));
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void SendEmail(FindpwData data){
        service.findpw(data).enqueue(new Callback<FindpwResponse>() {
            @Override
            public void onResponse(Call<FindpwResponse> call, Response<FindpwResponse> response) {
                FindpwResponse result = response.body();
                Toast.makeText(Findpwd.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<FindpwResponse> call, Throwable t) {
                Toast.makeText(Findpwd.this, "이메일 발송 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("이메일 발송 에러 발생", t.getMessage());
            }
        });
    }

}