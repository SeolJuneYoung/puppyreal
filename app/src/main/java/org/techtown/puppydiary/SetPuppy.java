package org.techtown.puppydiary;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.techtown.puppydiary.calendarmenu.CalendarTab;
import org.techtown.puppydiary.network.Data.RegisterData;
import org.techtown.puppydiary.network.Response.MyinfoResponse;
import org.techtown.puppydiary.network.Response.RegisterResponse;
import org.techtown.puppydiary.network.RetrofitClient;
import org.techtown.puppydiary.network.ServiceApi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
//import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SetPuppy extends AppCompatActivity {
    private  static final int REQUEST_CODE = 0;
    de.hdodenhof.circleimageview.CircleImageView imageView;
    ActionBar actionBar;
    private ServiceApi service;
    Button button;
    EditText b_day;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpuppy);

        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffD6336B));
        getSupportActionBar().setTitle("댕댕이어리");
        actionBar.setIcon(R.drawable.white_puppy) ;
        actionBar.setDisplayUseLogoEnabled(true) ;
        actionBar.setDisplayShowHomeEnabled(true) ;


        HashMap<String, String>header = new HashMap<>();
        service = RetrofitClient.getClient().create(ServiceApi.class);

        TextView textView = findViewById(R.id.textView);
        SpannableString content = new SpannableString("우리 집 댕댕이는요");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);

        Calendar cal = Calendar.getInstance();
        b_day = findViewById(R.id.bd_input);

        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);


        b_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(
                        SetPuppy.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month = month + 1;
                                String date = year + "/ " + month + "/ " + day;
                                b_day.setText(date);
                            }
                        }, year, month, day);
                dialog.show();
            }
        });


        imageView = findViewById(R.id.profile);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        final EditText puppy_name = findViewById(R.id.name_input);
        final EditText age_ = findViewById(R.id.age_input);
        final EditText birth_ = findViewById(R.id.bd_input);
        final RadioButton option_male = (RadioButton) findViewById(R.id.male);
        final RadioButton option_female = (RadioButton) findViewById(R.id.female);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = sp.getString("TOKEN", "");
        final Call<MyinfoResponse> getCall = service.Getmyinfo(token);
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
                        if (age_.getText().equals("0")){
                            age_.setText(null);
                        }

                        birth_.setText(myinfo1.getBirth());

                        int gender = myinfo1.getGender();
                        if ( gender ==1 ){
                            option_male.setChecked(true);
                        }
                        else if ( gender == 2 ) {
                            option_female.setChecked(true);
                        }

                        // result = myinfo1.getPuppyname();
                        // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();;
                    }

                }
            }

            @Override
            public void onFailure(Call<MyinfoResponse> call, Throwable t) {
                Log.e("실패", "했지롱");
            }
        });
        button = findViewById(R.id.finish_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setBackgroundColor( Color.parseColor("#D6336B"));

                if( !(puppy_name.getText().equals("")) && !(age_.getText().equals("")) ) {
                    String puppyname = puppy_name.getText().toString();
                    Integer age = Integer.parseInt("" + age_.getText());
                    String birth = birth_.getText().toString();
                    int gender = 0; // 1이 남자, 2가 여자

                    if (option_male.isChecked() && (!option_female.isChecked())) {
                        gender = 1;
                    } else if ((!option_male.isChecked()) && option_female.isChecked()) {
                        gender = 2;
                    }

                    infoInputCheck(new RegisterData(puppyname, age, birth, gender));
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "인자가 입력되지 않았습니다", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String filename = "";

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    // img를 bitmap으로 받아옴
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap bitmap = BitmapFactory.decodeStream(in);

                    bitmap = rotateImage(bitmap, 90);

                    in.close();

                    imageView.setImageBitmap(bitmap);

                } catch (Exception e) {

                }
            }
            else if(resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

/*
    private void uploadImageUsingRetrofit(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String image = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        String name = String.valueOf(Calendar.getInstance().getTimeInMillis());

        service = RetrofitClient.getClient().create(ServiceApi.class);

        Call<String> call = myImageInterface.getImageData(name,image);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Toast.makeText(getApplicationContext(), "Image Uploaded Successfully!!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "No response from the server", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Response not successful "+response.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }

*/

    private void infoInputCheck(final RegisterData data){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = sp.getString("TOKEN", "");
        service.registerinfo(token, data).enqueue(new Callback<RegisterResponse>() {

            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                RegisterResponse result = response.body();
                //result.getData();

                //SharedPreferences sharedPref = getSharedPreferences("token", Context.MODE_PRIVATE);
                // HashMap<String, String> header = new HashMap<>();
                //String token = sharedPref.getString("token", "token");
                //header.put("tokenjwt", token);

                //("Context-Type = application/jason");

                Toast.makeText(SetPuppy.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if(result.getMessage().equals("강아지 정보 등록 성공")){
                    Intent intent = new Intent(getApplicationContext(), CalendarTab.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(SetPuppy.this, "이메일 중복 에러 발생", Toast.LENGTH_SHORT).show();
                //Log.e("강아지 정보 등록 에러 발생", t.getMessage());
            }
        });
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


}