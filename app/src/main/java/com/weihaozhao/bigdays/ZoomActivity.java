package com.weihaozhao.bigdays;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.weihaozhao.bigdays.db.Events;

import org.litepal.crud.DataSupport;

import java.util.Calendar;


/**
 * Created by Administrator on 2017/3/30.
 */

public class ZoomActivity extends AppCompatActivity {

    public static final String EVENTS_NAME="events_name";

    public static final String DAYS_LEFT="days_left";

    public static final String EVENTS_ID="events_id";

    private int eventsIdZoom;

    private String eventsNameZoom;

    private long daysLeftZoom;

    private TextView eventsName;

//    private TextView eventsId;

    private TextView daysLeft;

   private Calendar calendar;

    private TextView beforeDay;

    private int year,month,day;

    private RelativeLayout relativeLayout;

//    private ImageView imageView;

    private TextView showDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoom_data);

        Intent intent=getIntent();
        eventsNameZoom=intent.getStringExtra(EVENTS_NAME);
        daysLeftZoom=intent.getLongExtra(DAYS_LEFT,0);
        eventsIdZoom=intent.getIntExtra(EVENTS_ID,0);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar_zoom);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        eventsName= (TextView) findViewById(R.id.events_name_zoom);
//        eventsId= (TextView) findViewById(R.id.events_id);
        daysLeft= (TextView) findViewById(R.id.days_left_zoom);
        beforeDay= (TextView) findViewById(R.id.before_day_zoom);
        showDate= (TextView) findViewById(R.id.show_date);
        relativeLayout= (RelativeLayout) findViewById(R.id.zoom_layout);
//        imageView= (ImageView) findViewById(R.id.zoom_bg);
//        Glide.with(this).load(R.drawable.bg).into(imageView);
        calendar=Calendar.getInstance();
       year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH)+1;
                day=calendar.get(Calendar.DAY_OF_MONTH);

        Events events = DataSupport.find(Events.class, eventsIdZoom);
        showDate.setText(String.valueOf(events.getYear())+"-"+String.valueOf(events.getMonth())+"-"+String.valueOf(events.getDay()));

        eventsName.setText(eventsNameZoom);
//        eventsId.setText(String.valueOf(eventsIdZoom));
        if(daysLeftZoom>0){
            beforeDay.setText("还有");
            daysLeft.setText(String.valueOf(daysLeftZoom));
            relativeLayout.setBackgroundColor(Color.parseColor("#3184CF"));

        }else if(daysLeftZoom==0){
            beforeDay.setText("就是");
            daysLeft.setText("今天");
            relativeLayout.setBackgroundColor(Color.parseColor("#3184CF"));
        }else{
            beforeDay.setText("过去");
            daysLeft.setText(String.valueOf(0-daysLeftZoom));
            relativeLayout.setBackgroundColor(Color.parseColor("#F69429"));
        }


//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DataSupport.delete(Events.class, eventsIdZoom);
//                finish();
//            }
//        });
//        FloatingActionButton update= (FloatingActionButton) findViewById(R.id.update_btn);
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent toUpdate=new Intent(ZoomActivity.this,UpdateDataActivity.class);
//                toUpdate.putExtra(UpdateDataActivity.EVENTS_NAME,eventsNameZoom);
//                toUpdate.putExtra(UpdateDataActivity.EVENTS_ID,eventsIdZoom);
//                startActivity(toUpdate);
//                finish();
//
//            }
//        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Events events=DataSupport.find(Events.class, eventsIdZoom);
        eventsName.setText(events.getEventsname());
        showDate.setText(String.valueOf(events.getYear())+"-"+String.valueOf(events.getMonth())+"-"+String.valueOf(events.getDay()));
//        eventsId.setText(String.valueOf(events.getId()));
        long days=dayLeft(events.getYear(),events.getMonth(),events.getDay());
        if(days>0){
            beforeDay.setText("还有");
            daysLeft.setText(String.valueOf(days));
            relativeLayout.setBackgroundColor(Color.parseColor("#3184CF"));

        }else if(days==0){
            beforeDay.setText("就是");
            daysLeft.setText("今天");
            relativeLayout.setBackgroundColor(Color.parseColor("#3184CF"));
        }else{
            beforeDay.setText("过去");
            daysLeft.setText(String.valueOf(0-days));
            relativeLayout.setBackgroundColor(Color.parseColor("#F69429"));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_zoom,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                DataSupport.delete(Events.class, eventsIdZoom);
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.update:
                Intent toUpdate=new Intent(ZoomActivity.this,UpdateDataActivity.class);
                toUpdate.putExtra(UpdateDataActivity.EVENTS_NAME,eventsNameZoom);
                toUpdate.putExtra(UpdateDataActivity.EVENTS_ID,eventsIdZoom);
                startActivity(toUpdate);
                break;
            case android.R.id.home:
                finish();
                break;
                default:
        }
        return true;
    }

    public long dayLeft(int y,int m,int d){


        calendar.set(y,m-1,d);
        long time2=calendar.getTimeInMillis();
        calendar.set(year,month-1,day);
        long time1=calendar.getTimeInMillis();
        long subDay=(time2-time1)/(1000*60*60*24);
        return subDay;

    }
}
