package com.weihaozhao.bigdays;

import android.content.Intent;
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
 * Created by Administrator on 2017/3/30.
 */

public class UpdateDataActivity extends AppCompatActivity {

    private DatePicker datePicker;

    private EditText editText;

//    private FloatingActionButton button;

    private Toolbar toolbar;

    private Calendar calendar;

    private int year,month,day;

    public static final String EVENTS_ID="events_id";

    public static final String EVENTS_NAME="events_name";

    private int eventsId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_data);
        Intent intent=getIntent();
        String eventsName=intent.getStringExtra(EVENTS_NAME);
        eventsId=intent.getIntExtra(EVENTS_ID,0);
        datePicker= (DatePicker) findViewById(R.id.myDatePicker_update);
        editText= (EditText) findViewById(R.id.edit_text_update);
//        button= (FloatingActionButton) findViewById(R.id.update_btn_update);
        toolbar= (Toolbar) findViewById(R.id.toolbar_update);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);

        editText.setText(eventsName);



        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int yearOfPick, int monthOfYear, int dayOfMonth) {
                year=yearOfPick;
                month=monthOfYear+1;
                day=dayOfMonth;
            }
        });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Events updateEvents = new Events();
//                updateEvents.setEventsname(editText.getText().toString());
//                updateEvents.setYear(year);
//                updateEvents.setMonth(month);
//                updateEvents.setDay(day);
//                updateEvents.update(eventsId);
//                finish();
//            }
//        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_update,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.update_btn:
                if(editText.getText().toString().isEmpty()){
                    Toast.makeText(this, "事件标题不能为空", Toast.LENGTH_SHORT).show();
                    break;
                }else{
                Events updateEvents = new Events();
                updateEvents.setEventsname(editText.getText().toString());
                updateEvents.setYear(year);
                updateEvents.setMonth(month);
                updateEvents.setDay(day);
                updateEvents.update(eventsId);
//                Intent intent=new Intent(UpdateDataActivity.this,MainActivity.class);
//                startActivity(intent);
                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                finish();
                break;}
            case R.id.warn_btn:
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
