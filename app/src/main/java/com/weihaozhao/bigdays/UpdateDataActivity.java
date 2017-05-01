package com.weihaozhao.bigdays;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.weihaozhao.bigdays.db.Events;

/**
 * Created by Zhao Weihao on 2017/4/2.
 * Updated by Zhao Weihao on 2017/5/1
 */

public class UpdateDataActivity extends AppCompatActivity {

    private DatePicker datePicker;

    private EditText editText;

    private Toolbar toolbar;

    private int year,month,day;

    public static final String EVENTS_ID="events_id";

    public static final String EVENTS_NAME="events_name";

    public static final String EVENTS_YEAR="events_year";

    public static final String EVENTS_MONTH="events_month";

    public static final String EVENTS_DAY="events_day";

    private int eventsId,eventsYear,eventsMonth,eventsDay;

    private String eventsName;

    private Intent intent;

    private Events updateEvents;

    private AlertDialog.Builder dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.update_data);

        initViews();

        getDays();



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
//                    Toast.makeText(this, R.string.title_isEmpty, Toast.LENGTH_SHORT).show();
                    Snackbar.make(editText,R.string.title_isEmpty,Snackbar.LENGTH_SHORT).show();
                    break;
                }else{
                    buildDialog();



                break;}
            case R.id.warn_btn:
//                Toast.makeText(this, R.string.warn_text, Toast.LENGTH_SHORT).show();
                Snackbar.make(editText,R.string.warn_text,Snackbar.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                finish();
                break;
            default:

        }
        return true;
    }

    private void initViews(){

        intent=getIntent();

        eventsName=intent.getStringExtra(EVENTS_NAME);

        eventsId=intent.getIntExtra(EVENTS_ID,0);

        eventsYear=intent.getIntExtra(EVENTS_YEAR,0);

        eventsMonth=intent.getIntExtra(EVENTS_MONTH,0);

        eventsDay=intent.getIntExtra(EVENTS_DAY,0);

        datePicker= (DatePicker) findViewById(R.id.myDatePicker_update);

        editText= (EditText) findViewById(R.id.edit_text_update);

        toolbar= (Toolbar) findViewById(R.id.toolbar_update);

        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        editText.setText(eventsName);

    }

    private void getDays(){

        datePicker.init(eventsYear, eventsMonth-1, eventsDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int yearOfPick, int monthOfYear, int dayOfMonth) {
                year=yearOfPick;
                month=monthOfYear+1;
                day=dayOfMonth;
            }
        });

    }

    private void updateDays(){

        updateEvents = new Events();
        updateEvents.setEventsname(editText.getText().toString());
        updateEvents.setYear(year);
        updateEvents.setMonth(month);
        updateEvents.setDay(day);
        updateEvents.update(eventsId);

    }

    private void buildDialog(){
        dialog=new AlertDialog.Builder(UpdateDataActivity.this);
        dialog.setTitle(R.string.ok_text);
        dialog.setMessage(R.string.sure_update_text);
        dialog.setCancelable(false);
        dialog.setPositiveButton(R.string.ok_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                updateDays();

                Toast.makeText(UpdateDataActivity.this, R.string.update_successfully, Toast.LENGTH_SHORT).show();

                finish();

            }
        });
        dialog.setNegativeButton(R.string.cancel_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        dialog.show();
    }
}
