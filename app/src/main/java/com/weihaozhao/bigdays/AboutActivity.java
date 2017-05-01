package com.weihaozhao.bigdays;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

/**
 * Created by Zhao Weihao on 2017/4/2.
 * Updated by Zhao Weihao on 2017/5/1
 */

public class AboutActivity extends AppCompatActivity {

    private TextView textView;

    private Toolbar toolbar;

    private Calendar calendar;

    private int year,month,day;

    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        initViews();

        createAboutPage();




    }

    public long dayLeft(int y,int m,int d){


        calendar.set(y,m-1,d);
        long time2=calendar.getTimeInMillis();
        calendar.set(year,month-1,day);
        long time1=calendar.getTimeInMillis();
        long subDay=(time2-time1)/(1000*60*60*24);
        return subDay;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:

        }
        return true;
    }

    private void initViews(){

        toolbar= (Toolbar) findViewById(R.id.toolbar_about);

        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();

        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        textView= (TextView) findViewById(R.id.about_day);

        calendar=Calendar.getInstance();

        year=calendar.get(Calendar.YEAR);

        month=calendar.get(Calendar.MONTH)+1;

        day=calendar.get(Calendar.DAY_OF_MONTH);

        long aboutDay=dayLeft(2017,4,2);

        textView.setText(String.valueOf(0-aboutDay));

        relativeLayout= (RelativeLayout) findViewById(R.id.about_layout);

    }

    private void createAboutPage(){

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setDescription(getString(R.string.description))
                .setImage(R.mipmap.ic_launcher)
                .addItem(new Element().setTitle("Version 2.0"))
                .addGroup(getString(R.string.contact))
                .addWebsite("https://zhaoweihaochina.github.io/")
                .addPlayStore("com.weihaozhao.bigdays")
                .addGitHub("zhaoweihaoChina")
                .create();

        relativeLayout.addView(aboutPage);

    }
}
