package org.techtown.puppydiary.kgmenu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import org.techtown.puppydiary.kgmenu.KgTab;
import org.techtown.puppydiary.R;
import org.techtown.puppydiary.kgmenu.DBHelper_kg;
import org.techtown.puppydiary.network.Data.KgupdateData;
import org.techtown.puppydiary.network.Data.UpdatepwData;
import org.techtown.puppydiary.network.Response.KgupdateResponse;
import org.techtown.puppydiary.network.RetrofitClient;
import org.techtown.puppydiary.network.ServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.sql.DriverManager.println;
import static org.techtown.puppydiary.kgmenu.KgTab.kg_month;
import static org.techtown.puppydiary.kgmenu.KgTab.yearr;

public class KgPopup extends AppCompatActivity {
    String monthname;
    private  static Context context;
    ActionBar actionBar;
    //public static double puppykg; public으로 설정을 해주어야 다른 클래스에서 사용 가능
    public static double puppykg;
    String kgStr;
    EditText weight;
    Button okay;
    Button close;
    int year;
    private ServiceApi service;
    public int userIdx = 31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //puppykg = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_kg_upload);

        final DBHelper_kg dbHelper = new DBHelper_kg(getApplicationContext(), "KG.db", null, 1);

        // MoneyEdit.context = getApplicationContext();
        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffD6336B));
        getSupportActionBar().setTitle("댕댕이어리");
        actionBar.setIcon(R.drawable.white_puppy) ;
        actionBar.setDisplayUseLogoEnabled(true) ;
        actionBar.setDisplayShowHomeEnabled(true) ;

        service = RetrofitClient.getClient().create(ServiceApi.class);

        monthname = kg_month;
        TextView Month = (TextView) findViewById(R.id.kgmonth);
        Month.setText(monthname);  //클릭한 달의 이름으로 setText

        year = yearr;

        okay = findViewById(R.id.kg_confirm);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //button.setBackground(R.drawable.button_pressed);
                okay.setBackgroundResource(R.drawable.button_pressed); //확인 버튼 클릭시 color changed -> 다른 버튼들에도 추가해주면 좋음
                Intent intent_kgconfirm = new Intent(getApplicationContext(), KgTab.class);
                startActivity(intent_kgconfirm);

                weight = (EditText)findViewById(R.id.kg_weight); //weight edittext 가져오기, kgTab에서 사용해야 하는 것이므로 이 클래스에서는 public 설정
                kgStr = weight.getText().toString();
                //만약 jan의 kg 이 3.5 였다면 tostring으로 "3.5"=kgStr


                //final Intent intent = new Intent(getIntent());
                //final int year = intent.getIntExtra("year", 2020);

                //Toast.makeText(getApplicationContext(), year + "입니다", Toast.LENGTH_LONG).show();

                if(monthname.equals("January")) {
                    puppykg = Double.parseDouble(kgStr);
                    //dbHelper.insert(userIdx, year, 1, puppykg);
                    //double.parsedouble을 이용해서 string이었던 "3.5"를 double로 형변환시켜주어 저장 -> 저장 후 kgTab으로 가져감 (이 코드는 kgTab에 있음)
                }
                else if(monthname.equals("February")) {
                    puppykg = Double.parseDouble(kgStr);
                    // dbHelper.insert(userIdx, year, 2, puppykg);
                }
                else if(monthname.equals("March")) {
                    puppykg = Double.parseDouble(kgStr);
                    // dbHelper.insert(userIdx, year, 3, puppykg);
                }
                else if(monthname.equals("April")) {
                    puppykg = Double.parseDouble(kgStr);
                    //dbHelper.insert(userIdx, year, 4, puppykg);
                }
                else if(monthname.equals("May")) {
                    puppykg = Double.parseDouble(kgStr);
                    // dbHelper.insert(userIdx, year, 5, puppykg);
                }
                else if(monthname.equals("June")) {
                    puppykg = Double.parseDouble(kgStr);
                    //dbHelper.insert(userIdx, year, 6, puppykg);
                }
                else if(monthname.equals("July")) {
                    puppykg = Double.parseDouble(kgStr);
                    //dbHelper.insert(userIdx, year, 7, puppykg);
                }
                else if(monthname.equals("August")) {
                    puppykg = Double.parseDouble(kgStr);
                    //dbHelper.insert(userIdx, year, 8, puppykg);
                }
                else if(monthname.equals("September")) {
                    puppykg = Double.parseDouble(kgStr);
                    //dbHelper.insert(userIdx, year, 9, puppykg);
                }
                else if(monthname.equals("October")) {
                    puppykg = Double.parseDouble(kgStr);
                    //dbHelper.insert(userIdx, year, 10, puppykg);
                }
                else if(monthname.equals("November")) {
                    puppykg = Double.parseDouble(kgStr);
                    //dbHelper.insert(userIdx, year, 11, puppykg);
                }
                else if(monthname.equals("December")) {
                    puppykg = Double.parseDouble(kgStr);
                    //dbHelper.insert(userIdx, year, 12, puppykg);
                }

                UpdateKg(new KgupdateData(year, monthname, puppykg));

                Intent intent_kgclose = new Intent(KgPopup.this, KgTab.class); //년도 전달함
                intent_kgclose.putExtra("year", year);
                startActivity(intent_kgclose);

                //Log.d("kg1", "kg1" + kgStr);
                //Log.d("kg2", "kg2" + ((EditText)findViewById(R.id.kg_weight)).getText().toString());
                //Log.d("kgpopup", "kgpopup ." + puppykg);
            }
        });

        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close.setBackgroundColor( Color.parseColor("#D6336B"));
                Intent intent_kgclose = new Intent(getApplicationContext(), KgTab.class); //일단 바로 검색결과 띄음
                startActivity(intent_kgclose);
            }
        });
    }

    private void UpdateKg(KgupdateData data){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = sp.getString("TOKEN", "");
        service.kgupdate(token, data).enqueue(new Callback<KgupdateResponse>() {

            @Override
            public void onResponse(Call<KgupdateResponse> call, Response<KgupdateResponse> response) {
                KgupdateResponse result = response.body();
                Toast.makeText(KgPopup.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                if(result.getSuccess() == true){
                    Intent intent_start = new Intent(getApplicationContext(), KgTab.class);
                    startActivityForResult(intent_start, 2000);
                }
            }

            @Override
            public void onFailure(Call<KgupdateResponse> call, Throwable t) {
                Toast.makeText(KgPopup.this, "체중 업데이트 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("체중 업데이트 에러 발생", t.getMessage());
            }
        });
    }
}