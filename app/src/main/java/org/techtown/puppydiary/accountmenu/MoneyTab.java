package org.techtown.puppydiary.accountmenu;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.techtown.puppydiary.calendarmenu.CalendarTab;
import org.techtown.puppydiary.kgmenu.KgTab;
import org.techtown.puppydiary.MypuppyTab;
import org.techtown.puppydiary.R;
import org.techtown.puppydiary.network.Data.account.AccountUpdateData;
import org.techtown.puppydiary.network.Data.account.InsertAccountData;
import org.techtown.puppydiary.network.Response.account.AccountUpdateResponse;
import org.techtown.puppydiary.network.Response.account.CheckAccountResponse;
import org.techtown.puppydiary.network.Response.account.InsertAccountResponse;
import org.techtown.puppydiary.network.Response.account.ShowAccountResponse;
import org.techtown.puppydiary.network.RetrofitClient;
import org.techtown.puppydiary.network.ServiceApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoneyTab extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "MainActivity";
    ActionBar actionBar;
    public static Context context;

    Button calendar;
    Button kg;
    Button money;
    Button puppy;


    Button moneycalendar;
    Button plus;

    TextView tv_date;
    TextView tv_total;
    EditText et_price;
    EditText et_memo;
    int price = 0;
    String memo = null;

    Calendar myCalendar = Calendar.getInstance();

    int year_money = 0;
    int month_money = 0;
    int day_money = 0;

    int idx = 0;

    int position = 0;

    Cursor cursor;
    EditText[] EditTexts;
    MoneytabItem items;
    ArrayList<MoneytabItem> itemArray = null;
    ListAdapter adapter;
    ListView listview;

    private ServiceApi service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);


        actionBar = getSupportActionBar();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffD6336B));
        getSupportActionBar().setTitle("댕댕이어리");
        actionBar.setIcon(R.drawable.white_puppy) ;
        actionBar.setDisplayUseLogoEnabled(true) ;
        actionBar.setDisplayShowHomeEnabled(true) ;


        calendar = findViewById(R.id.calendar);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_calendar = new Intent(getApplicationContext(), CalendarTab.class); //일단 바로 검색결과 띄음
                startActivity(intent_calendar);
            }
        });

        kg = findViewById(R.id.kg);
        kg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_kg = new Intent(getApplicationContext(), KgTab.class); //일단 바로 검색결과 띄음
                startActivity(intent_kg);
            }
        });

        money = findViewById(R.id.account);
        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_money = new Intent(getApplicationContext(), MoneyTab.class); //일단 바로 검색결과 띄음
                startActivity(intent_money);
            }
        });

        puppy = findViewById(R.id.puppy);
        puppy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_puppy = new Intent(getApplicationContext(), MypuppyTab.class); //일단 바로 검색결과 띄음
                startActivity(intent_puppy);
            }
        });


        service = RetrofitClient.getClient().create(ServiceApi.class);

        listview = findViewById(R.id.accountlist);
        listview.setOnItemClickListener(this);

        tv_date = findViewById(R.id.date);
        tv_total = findViewById(R.id.total_sum);
        et_memo = (EditText) findViewById(R.id.edit_context);
        et_price = (EditText) findViewById(R.id.edit_price);

        itemArray = new ArrayList<MoneytabItem>();
        adapter = new ListAdapter(this, itemArray);

        EditTexts = new EditText[]{
                (EditText) findViewById(R.id.edit_context),
                (EditText) findViewById(R.id.edit_price)
        };


        // 아이템 수정, 삭제 후 해당 날짜로 돌아오기
        Intent intent_after = new Intent(getIntent());
        int after_year = intent_after.getIntExtra("after_year", 0);
        int after_month = intent_after.getIntExtra("after_month", 0);
        int after_day = intent_after.getIntExtra("after_day", 0);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d", Locale.KOREA);
        if(after_year != 0){
            tv_date.setText(after_year+"/"+after_month+"/"+after_day);
            year_money = after_year;
            month_money = after_month;
            day_money = after_day;
        } else {
            tv_date.setText(sdf.format(myCalendar.getTime()));
            year_money = myCalendar.get(Calendar.YEAR);
            month_money = myCalendar.get(Calendar.MONTH) + 1;
            day_money = myCalendar.get(Calendar.DAY_OF_MONTH);
        }
        ShowAccount();


        // 날짜 선택 -> 날짜에 해당하는 아이템들 보여줌
        final DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d", Locale.KOREA);
                tv_date.setText(sdf.format(myCalendar.getTime()));
                year_money = myCalendar.get(Calendar.YEAR);
                month_money = myCalendar.get(Calendar.MONTH) + 1;
                day_money = myCalendar.get(Calendar.DAY_OF_MONTH);
                ShowAccount();
            }
        };

        // 달력 버튼 (날짜 선택)
        moneycalendar = findViewById(R.id.money_calendar);
        moneycalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MoneyTab.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // + 버튼 (아이템 추가)
        plus = findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memo = et_memo.getText().toString();
                price = Integer.parseInt(et_price.getText().toString());
                InsertAccount(new InsertAccountData(year_money, month_money, day_money, memo, price));
                //adapter.notifyDataSetChanged();
                et_memo.getText().clear();
                et_price.getText().clear();
                // 저장 버튼 누른 후 키보드 안보이게 하기
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_price.getWindowToken(), 0);
            }

        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        memo = itemArray.get(i).memo;
        price = itemArray.get(i).price;
        CheckAccount(memo, price);

    }


    public static class MoneytabItem {

        String memo;
        int price;

        public MoneytabItem(String memo, int price) {
            this.memo = memo;
            this.price = price;
        }

    }


    public class ListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<MoneytabItem> itemList;
        private ViewHolder viewHolder;

        public ListAdapter(Context c, ArrayList<MoneytabItem> array) {
            inflater = LayoutInflater.from(c);
            itemList = array;
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int position, View convertview, ViewGroup parent) {

            View v = convertview;

            if (v == null) {
                viewHolder = new ViewHolder();
                v = inflater.inflate(R.layout.fragment_itemlist, null);
                viewHolder.memo = (TextView) v.findViewById(R.id.item_context);
                viewHolder.price = (TextView) v.findViewById(R.id.item_price);
                v.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) v.getTag();
            }

            viewHolder.memo.setText(itemList.get(position).memo);
            viewHolder.price.setText(Integer.toString(itemList.get(position).price));

            return v;
        }

        public void setArrayList(ArrayList<MoneytabItem> arrays) {
            this.itemList = arrays;
        }

        public ArrayList<MoneytabItem> getArrayList() {
            return itemList;
        }


        class ViewHolder {
            TextView memo;
            TextView price;
        }
    }

    private void ShowAccount() {
        service.showaccount(year_money, month_money, day_money).enqueue(new Callback<ShowAccountResponse>() {
            @Override
            public void onResponse(Call<ShowAccountResponse> call, Response<ShowAccountResponse> response) {
                ShowAccountResponse showaccount = response.body();
                int sum = 0 ;
                if (response.isSuccessful()) {
                    List<ShowAccountResponse.ShowAccount> my = showaccount.getData();
                    itemArray.clear();
                    if(my != null) {
                        for (int i = 0; i < my.size(); i++) {
                            memo = my.get(i).getItem();
                            price = my.get(i).getPrice();
                            items = new MoneytabItem(memo, price);
                            itemArray.add(items);
                        }
                    }
                    adapter.setArrayList(itemArray);
                    for(int i=0; i<itemArray.size(); i++) {
                        sum += itemArray.get(i).price;
                    }
                    tv_total.setText(sum + "원");
                    listview.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ShowAccountResponse> call, Throwable t) {
                Toast.makeText(MoneyTab.this, "getcall 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("getcall 에러 발생", t.getMessage());
            }
        });
    }


    private void InsertAccount(InsertAccountData data){
        service.insertaccount(data).enqueue(new Callback<InsertAccountResponse>() {
            @Override
            public void onResponse(Call<InsertAccountResponse> call, Response<InsertAccountResponse> response) {
                InsertAccountResponse result = response.body();
                ShowAccount();
                Toast.makeText(MoneyTab.this, result.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<InsertAccountResponse> call, Throwable t) {
                Toast.makeText(MoneyTab.this, "가계부 업데이트 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("가계부 업데이트 에러 발생", t.getMessage());
            }
        });
    }

    private void CheckAccount(String item, final int price) {
        service.checkaccount(year_money, month_money, day_money, item, price).enqueue(new Callback<CheckAccountResponse>() {
            @Override
            public void onResponse(Call<CheckAccountResponse> call, Response<CheckAccountResponse> response) {
                CheckAccountResponse checkaccount = response.body();
                if(response.isSuccessful()){
                    idx = checkaccount.getData();
                    Intent intent = new Intent(MoneyTab.this, MoneyEdit.class);
                    intent.putExtra("idx", idx);
                    intent.putExtra("year", year_money);
                    intent.putExtra("month", month_money);
                    intent.putExtra("day", day_money);
                    intent.putExtra("memo", memo);
                    intent.putExtra("price", price);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<CheckAccountResponse> call, Throwable t) {
                Toast.makeText(MoneyTab.this, "check idx 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("check idx 에러 발생", t.getMessage());
            }
        });
    }

}