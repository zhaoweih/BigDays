package com.weihaozhao.bigdays;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weihaozhao.bigdays.db.Events;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Zhao Weihao on 2017/4/2.
 * Updated by Zhao Weihao on 2017/5/1
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>{

    private Context mContext;

    private List<Events> mEventsList;

    final Calendar calendar=Calendar.getInstance();

    final int year=calendar.get(Calendar.YEAR),
            month=calendar.get(Calendar.MONTH)+1,
            day=calendar.get(Calendar.DAY_OF_MONTH);

    static class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;

        TextView eventsName;

        TextView daysleft;

        TextView beforeDay;

        RelativeLayout eventsLayout;

        public ViewHolder(View view){

            super(view);

            cardView=(CardView)view;

            eventsName= (TextView) view.findViewById(R.id.events_name);

            daysleft= (TextView) view.findViewById(R.id.days_left);

            beforeDay= (TextView) view.findViewById(R.id.before_day);

            eventsLayout= (RelativeLayout) view.findViewById(R.id.events_color_layout);


        }
    }

    public EventsAdapter(List<Events> eventsList){
        mEventsList=eventsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){

            mContext=parent.getContext();

        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.events_new_layout,parent,false);

        final ViewHolder holder=new ViewHolder(view);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position=holder.getAdapterPosition();

                Events events=mEventsList.get(position);

                Intent intent=new Intent(mContext,ZoomActivity.class);

                intent.putExtra(ZoomActivity.EVENTS_ID,events.getId());

                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Events events=mEventsList.get(position);

        holder.eventsName.setText(events.getEventsname());

        long days=dayLeft(events.getYear(),events.getMonth(),events.getDay());

        if(days>0){
            holder.beforeDay.setText(R.string.still_text);
            holder.daysleft.setText(String.valueOf(days));
            holder.eventsLayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        }else if(days==0){
            holder.beforeDay.setText(R.string.is_text);
            holder.daysleft.setText(R.string.today_text);
            holder.eventsLayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        }else {
            holder.beforeDay.setText(R.string.past_text);
            holder.daysleft.setText(String.valueOf(0-days));
            holder.eventsLayout.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
        }


    }

    @Override
    public int getItemCount() {
        return mEventsList.size();
    }

    private long dayLeft(int y,int m,int d){

        calendar.set(y,m-1,d);

        long time2=calendar.getTimeInMillis();

        calendar.set(year,month-1,day);

        long time1=calendar.getTimeInMillis();

        long subDay=(time2-time1)/(1000*60*60*24);

        return subDay;

    }



}
