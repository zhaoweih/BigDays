package com.weihaozhao.bigdays.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weihaozhao.bigdays.R;
import com.weihaozhao.bigdays.adapter.EventsAdapter;
import com.weihaozhao.bigdays.db.Events;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by Zhao Weihao on 2017/4/2.
 * Updated by Zhao Weihao on 2017/5/1
 */

public class MainActivity extends AppCompatActivity {

    private List<Events> eventsList;

    private EventsAdapter adapter;

    private RecyclerView recyclerView;

    private FloatingActionButton addData;

    private Toolbar toolbar;

    private GridLayoutManager gridLayoutManager;

    private TextView topEventsTitle,topEventsdays,topBeforeDay;

    private RelativeLayout topRelativeLayout;

    private int year,month,day;

    private Calendar calendar;

    private CardView cardView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initViews();

        getDays();

        refresh();

        if(!eventsList.isEmpty()) {

            cardView.setVisibility(View.VISIBLE);

            initTopViews();

        }else {
            cardView.setVisibility(View.INVISIBLE);
        }









    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refresh();
        if(!eventsList.isEmpty()) {

            cardView.setVisibility(View.VISIBLE);

            initTopViews();

        }else {
            cardView.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                Intent intent=new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }



    protected void refresh(){

        eventsList= DataSupport.findAll(Events.class);

        if(eventsList.isEmpty()){

//            Toast.makeText(this, R.string.add_events, Toast.LENGTH_SHORT).show();

            Snackbar.make(addData,R.string.add_events,Snackbar.LENGTH_SHORT).show();

        }

        Collections.sort(eventsList);

        gridLayoutManager=new GridLayoutManager(this,1);

        recyclerView.setLayoutManager(gridLayoutManager);

        adapter=new EventsAdapter(eventsList);

        recyclerView.setAdapter(adapter);
    }

    private void initViews(){

        topEventsTitle= (TextView) findViewById(R.id.top_events_name);

        topEventsdays= (TextView) findViewById(R.id.top_days_left);

        topBeforeDay= (TextView) findViewById(R.id.top_before_day);

        topRelativeLayout= (RelativeLayout) findViewById(R.id.top_events_color_layout);

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);

        toolbar= (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        addData= (FloatingActionButton) findViewById(R.id.add_data);

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddDataActivity.class);
                startActivity(intent);
            }
        });

        cardView= (CardView) findViewById(R.id.top_view);


    }

    private long dayLeft(int y,int m,int d){


        calendar.set(y,m-1,d);

        long time2=calendar.getTimeInMillis();

        calendar.set(year,month-1,day);

        long time1=calendar.getTimeInMillis();

        long subDay=(time2-time1)/(1000*60*60*24);

        return subDay;

    }

    private void getDays(){

        calendar=Calendar.getInstance();

        year=calendar.get(Calendar.YEAR);

        month=calendar.get(Calendar.MONTH)+1;

        day=calendar.get(Calendar.DAY_OF_MONTH);

    }

    private void initTopViews(){

        topEventsTitle.setText(eventsList.get(0).getEventsname());

        long days=dayLeft(eventsList.get(0).getYear(),eventsList.get(0).getMonth(),eventsList.get(0).getDay());

        if(days>0){
            topBeforeDay.setText(R.string.still_text);
            topEventsdays.setText(String.valueOf(days));
            topRelativeLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        }else if(days==0){
            topBeforeDay.setText(R.string.is_text);
            topEventsdays.setText(R.string.today_text);
            topRelativeLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }else{
            topBeforeDay.setText(R.string.past_text);
            topEventsdays.setText(String.valueOf(0-days));
            topRelativeLayout.setBackgroundColor(getResources().getColor(R.color.orange));
        }

    }

}
