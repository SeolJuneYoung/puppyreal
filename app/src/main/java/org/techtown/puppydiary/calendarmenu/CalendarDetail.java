package org.techtown.puppydiary.calendarmenu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.techtown.puppydiary.R;
import org.techtown.puppydiary.accountmenu.MoneyEdit;
import org.techtown.puppydiary.accountmenu.MoneyTab;
import org.techtown.puppydiary.network.Data.calendar.CalendarPhotoData;
import org.techtown.puppydiary.network.Data.calendar.CalendarUpdateData;
import org.techtown.puppydiary.network.Response.calendar.CalendarPhotoResponse;
import org.techtown.puppydiary.network.Response.calendar.CalendarUpdateResponse;
import org.techtown.puppydiary.network.Response.calendar.ShowDayResponse;
import org.techtown.puppydiary.network.RetrofitClient;
import org.techtown.puppydiary.network.ServiceApi;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarDetail extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;

    int year = 0;
    int month = 0;
    int date = 0;
    int state_waterdrop = -1;
    int state_injection = -1;
    int showmonth_pos = 0;
    String memo;
    String photo;

    ImageView image_upload;
    byte[] image_byte = null;
    Bitmap upload_bitmap = null;

    ActionBar actionBar;
    Button waterdrop_btn;
    Button waterdrop_btn2;
    Button injection_btn;
    Button injection_btn2;
    Button cancel_btn;
    Button save_btn;

    EditText memo_et;

    TextView tv_date;

    private ServiceApi service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_calendar_detail);

        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffD6336B));
        getSupportActionBar().setTitle("댕댕이어리");
        actionBar.setIcon(R.drawable.white_puppy) ;
        actionBar.setDisplayUseLogoEnabled(true) ;
        actionBar.setDisplayShowHomeEnabled(true) ;

        //final DBHelper_cal dbHelper = new DBHelper_cal(getApplicationContext(), "caltest.db", null, 1);
        final Intent intent = new Intent(getIntent());
        year = intent.getIntExtra("year", 0);
        month = intent.getIntExtra("month", 0);
        date = intent.getIntExtra("date", 0);

        //final int useridx = userinfo.load(getApplicationContext());
        final int useridx = 0;

        service = RetrofitClient.getClient().create(ServiceApi.class);

        tv_date = (TextView) findViewById(R.id.tv_date);
        waterdrop_btn = findViewById(R.id.waterdrop_detail);
        waterdrop_btn2 = findViewById(R.id.waterdrop_color);
        injection_btn = findViewById(R.id.injection_detail);
        injection_btn2 = findViewById(R.id.injection_color);

        cancel_btn = findViewById(R.id.btn_canceldetail);
        save_btn = findViewById(R.id.btn_savedetail);

        memo_et = (EditText) findViewById(R.id.edittext_memo);

        image_upload = (ImageView) findViewById(R.id.image_upload);

        //기본세팅
        waterdrop_btn2.setVisibility(View.INVISIBLE);
        waterdrop_btn.setVisibility(View.VISIBLE);
        injection_btn2.setVisibility(View.INVISIBLE);
        injection_btn.setVisibility(View.VISIBLE);

        Call<ShowDayResponse> showday = service.showday(year, month, date);
        showday.enqueue(new Callback<ShowDayResponse>() {
            @Override
            public void onResponse(Call<ShowDayResponse> call, Response<ShowDayResponse> response) {
                if(response.isSuccessful()) {
                    ShowDayResponse showday = response.body();
                    List<ShowDayResponse.ShowDay> my = showday.getData();
                    if (my != null) {
                        if (my.get(0).getMemo() != null) {
                            memo = my.get(0).getMemo();
                            memo_et.setText(memo);
                        }
                        if (my.get(0).getPhoto() != null) {
                            photo = my.get(0).getPhoto();
                            //dd
                        }
                        state_waterdrop = my.get(0).getWater();
                        state_injection = my.get(0).getInject();
                        if (state_waterdrop == 1 && state_injection == 0){
                            // 물방울만 색깔 있을 때
                            waterdrop_btn2.setVisibility(View.VISIBLE);
                            waterdrop_btn.setVisibility(View.INVISIBLE);
                            injection_btn2.setVisibility(View.INVISIBLE);
                            injection_btn.setVisibility(View.VISIBLE);
                        } else if (state_waterdrop == 0 && state_injection == 1){
                            // 주사기만 색깔 있을 때
                            waterdrop_btn2.setVisibility(View.INVISIBLE);
                            waterdrop_btn.setVisibility(View.VISIBLE);
                            injection_btn2.setVisibility(View.VISIBLE);
                            injection_btn.setVisibility(View.INVISIBLE);
                        } else if (state_waterdrop == 1 && state_injection == 1){
                            waterdrop_btn2.setVisibility(View.VISIBLE);
                            waterdrop_btn.setVisibility(View.INVISIBLE);
                            injection_btn2.setVisibility(View.VISIBLE);
                            injection_btn.setVisibility(View.INVISIBLE);
                        } else {
                            waterdrop_btn2.setVisibility(View.INVISIBLE);
                            waterdrop_btn.setVisibility(View.VISIBLE);
                            injection_btn2.setVisibility(View.INVISIBLE);
                            injection_btn.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ShowDayResponse> call, Throwable t) {
                Toast.makeText(CalendarDetail.this, "getcall 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("getcall 에러 발생", t.getMessage());
            }
        });

        tv_date.setText(year + ". " + (month+1) + ". " + date);


       // image_byte = dbHelper.getResultimg(useridx, pos, year, month);
        if (image_byte != null) {
            BitmapFactory.decodeByteArray(image_byte, 0, image_byte.length);
            image_upload.setImageBitmap(upload_bitmap);
        } else {
            image_upload.setImageResource(R.drawable.camera_imageview);
        }


        // on
        waterdrop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waterdrop_btn2.setVisibility(View.VISIBLE);
                waterdrop_btn.setVisibility(View.INVISIBLE);
                state_waterdrop = 1;
            }
        });

        // off
        waterdrop_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waterdrop_btn.setVisibility(View.VISIBLE);
                waterdrop_btn2.setVisibility(View.INVISIBLE);
                state_waterdrop = 0;
            }
        });

        // on
        injection_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                injection_btn2.setVisibility(View.VISIBLE);
                injection_btn.setVisibility(View.INVISIBLE);
                state_injection = 1;
            }
        });

        // off
        injection_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                injection_btn.setVisibility(View.VISIBLE);
                injection_btn2.setVisibility(View.INVISIBLE);
                state_injection = 0;
            }
        });

        memo_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_btn.setBackgroundColor( Color.parseColor("#D6336B"));
                memo = memo_et.getText().toString();
                /*if (image_byte == null){
                    dbHelper.insert(useridx, pos, year, month, text, null, waterdrop, injection);
                } else {
                    dbHelper.insert(useridx, pos, year, month, text, image_byte, waterdrop, injection);
                }*/
                CalendarUpdate(new CalendarUpdateData(year, month, date, memo, state_injection, state_waterdrop));
                //Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_LONG).show();

            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel_btn.setBackgroundColor( Color.parseColor("#D6336B"));
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    //DBHelper dbHelper = new DBHelper(getApplicationContext(), "EDITMEMO.db", null, 1);
                    InputStream stream = getContentResolver().openInputStream(data.getData());
                    upload_bitmap = BitmapFactory.decodeStream(stream);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    upload_bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    image_byte = baos.toByteArray();

                    image_upload.setImageBitmap(upload_bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void CalendarUpdate(CalendarUpdateData data){
        service.calendarupdate(data).enqueue(new Callback<CalendarUpdateResponse>() {
            @Override
            public void onResponse(Call<CalendarUpdateResponse> call, Response<CalendarUpdateResponse> response) {
                CalendarUpdateResponse result = response.body();
                Toast.makeText(CalendarDetail.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if(result.getSuccess() == true) {
                    Intent intent_month = new Intent(getApplicationContext(), CalendarTab.class);
                    intent_month.putExtra("after_year", year);
                    intent_month.putExtra("after_month", month);
                    startActivityForResult(intent_month, 2000);
                }
            }

            @Override
            public void onFailure(Call<CalendarUpdateResponse> call, Throwable t) {
                Toast.makeText(CalendarDetail.this, "달력 업데이트 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("달력 업데이트 에러 발생", t.getMessage());
            }
        });
    }

//    private void CalendarPhoto(CalendarPhotoData data){
//        service.calendarphoto(data).enqueue(new Callback<CalendarUpdateResponse>() {
//            @Override
//            public void onResponse(Call<CalendarPhotoResponse> call, Response<CalendarPhotoResponse> response) {
//                CalendarUpdateResponse result = response.body();
//                Toast.makeText(CalendarDetail.this, result.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<CalendarUpdateResponse> call, Throwable t) {
//                Toast.makeText(CalendarDetail.this, "달력 업데이트 에러 발생", Toast.LENGTH_SHORT).show();
//                Log.e("달력 업데이트 에러 발생", t.getMessage());
//            }
//        });
//    }

}
