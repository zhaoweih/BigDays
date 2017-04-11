package com.weihaozhao.bigdays;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.weihaozhao.bigdays.db.Events;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/3/29.
 */

public class AddDataActivity extends AppCompatActivity {

    private DatePicker datePicker;

    private EditText editText;

    private Calendar calendar;

    private Toolbar toolbar;

    private int year,month,day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);
        datePicker= (DatePicker) findViewById(R.id.myDatePicker);
        editText= (EditText) findViewById(R.id.edit_text);
        toolbar= (Toolbar) findViewById(R.id.toolbar_add);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);



        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int yearOfPick, int monthOfYear, int dayOfMonth) {
                year=yearOfPick;
                month=monthOfYear+1;
                day=dayOfMonth;
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_add,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                if(editText.getText().toString().isEmpty()){
                    Toast.makeText(this, "事件标题不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }else {
                Events events=new Events();
                events.setEventsname(editText.getText().toString());
                events.setYear(year);
                events.setMonth(month);
                events.setDay(day);
                events.save();
                    Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                finish();
                break;}
            case R.id.warn:
                Toast.makeText(this, "事件标题请尽量简洁，以保持主界面整体美观", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                finish();
                break;
                default:

        }
        return true;
    }
}
