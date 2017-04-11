package com.weihaozhao.bigdays;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.weihaozhao.bigdays.db.Events;

import org.litepal.crud.DataSupport;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Events> eventsList;

    private  EventsAdapter adapter;

    private RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        eventsList= DataSupport.findAll(Events.class);
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);

        refresh();
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        GridLayoutManager layoutManager=new GridLayoutManager(this,2);
//        recyclerView.setLayoutManager(layoutManager);
//        adapter=new EventsAdapter(eventsList);
//        recyclerView.setAdapter(adapter);
//        final Calendar calendar=Calendar.getInstance();
//        calendar.setTime(new Date());
//        final int year=calendar.get(Calendar.YEAR),
//                month=calendar.get(Calendar.MONTH)+1,
//                day=calendar.get(Calendar.DAY_OF_MONTH);
        FloatingActionButton addData= (FloatingActionButton) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddDataActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refresh();
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
            Toast.makeText(this, "点击右下方按钮添加事件", Toast.LENGTH_SHORT).show();
        }
        Collections.sort(eventsList);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new EventsAdapter(eventsList);
        recyclerView.setAdapter(adapter);
    }
}
