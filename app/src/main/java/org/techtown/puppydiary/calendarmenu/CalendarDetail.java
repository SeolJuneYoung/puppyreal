package org.techtown.puppydiary.calendarmenu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import org.apache.commons.io.IOUtils;
import org.techtown.puppydiary.R;
import org.techtown.puppydiary.SetPuppy;
import org.techtown.puppydiary.accountmenu.MoneyEdit;
import org.techtown.puppydiary.accountmenu.MoneyTab;
import org.techtown.puppydiary.network.Data.ProfileData;
import org.techtown.puppydiary.network.Data.calendar.CalendarPhotoData;
import org.techtown.puppydiary.network.Data.calendar.CalendarUpdateData;
import org.techtown.puppydiary.network.Response.ProfileResponse;
import org.techtown.puppydiary.network.Response.calendar.CalendarPhotoResponse;
import org.techtown.puppydiary.network.Response.calendar.CalendarUpdateResponse;
import org.techtown.puppydiary.network.Response.calendar.ShowDayResponse;
import org.techtown.puppydiary.network.RetrofitClient;
import org.techtown.puppydiary.network.ServiceApi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarDetail extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;

    int year = 0;
    int month = 0;
    int date = 0;
    int state_waterdrop = 0;
    int state_injection = 0;
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

    String mediaPath;
    String[] mediaColumns = {MediaStore.Video.Media._ID};

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

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = sp.getString("TOKEN", "");
        Call<ShowDayResponse> showday = service.showday(token, year, month, date);
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
                            Bitmap myBitmap = BitmapFactory.decodeFile(photo);
                            image_upload.setImageBitmap(myBitmap);
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
                        } else if (state_waterdrop == 0 && state_injection == 0){
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
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_btn.setBackgroundColor( Color.parseColor("#D6336B"));
                memo = memo_et.getText().toString();
                CalendarUpdate(new CalendarUpdateData(year, month, date, memo, state_injection, state_waterdrop));
                CalendarPhoto();

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
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = sp.getString("TOKEN", "");

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {

                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    System.out.println("!!!!!!!!!!!!!! " + mediaPath);
                    // Set the Image in ImageView for Previewing the Media
                    image_upload.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    cursor.close();

                } catch (Exception e) {

                }
            }
            else if(resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void CalendarUpdate(CalendarUpdateData data){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = sp.getString("TOKEN", "");
        service.calendarupdate(token, data).enqueue(new Callback<CalendarUpdateResponse>() {
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


    private void CalendarPhoto(){

        File file = new File(mediaPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("profile", file.getName(), requestBody);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = sp.getString("TOKEN", "");
        service.calendarphoto(fileToUpload, token, year, month, date).enqueue(new Callback<CalendarPhotoResponse>() {
            @Override
            public void onResponse(Call<CalendarPhotoResponse> call, Response<CalendarPhotoResponse> response) {
                CalendarPhotoResponse result = response.body();
                Toast.makeText(CalendarDetail.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CalendarPhotoResponse> call, Throwable t) {

            }
        });
    }
}
