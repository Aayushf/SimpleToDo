package com.simpletodo.aayushf.simpletodo;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ayfadia on 4/19/17.
 */

public class ViewPopulator extends RecyclerView.Adapter<ViewPopulator.Holder>{
    Context c;
    ArrayList<Task> t;

    public void setTagtodisplay(String tagtodisplay) {

        this.tagtodisplay = tagtodisplay;
        TasksDBHelper helper = new TasksDBHelper(c);
        ViewPopulator.this.notifyDataSetChanged();

    }

    Context context;
    Boolean small;
    String tagtodisplay = "";
    public interface ViewPopulatorInterface{
        public void changed(int position);
        public void clicked(Task t);
    }
    ViewPopulatorInterface myInterface;



    public ViewPopulator(Context c, boolean done, boolean small, String tagtodisplay) {
        super();
        this.small = small;
        TasksDBHelper helper = new TasksDBHelper(c);
        if (tagtodisplay == null){
            this.tagtodisplay= "";
        }else{
            this.tagtodisplay = tagtodisplay;
        }

        if (done){
        t = helper.getDoneTasks(tagtodisplay);
        }else{
            t = helper.getPendingTasks(tagtodisplay);

        }

        this.c = c;
        Log.d("VP", "ADAPTER CREATED");
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (small){
            v = LayoutInflater.from(c).inflate(R.layout.smalllistitem, null);
        }else {
            v = LayoutInflater.from(c).inflate(R.layout.listitemcardstaggered, null);
        }

        Holder h = new Holder(v);
        Log.d("VP", "ONCREATEVIEWHOLDER");

        return h;



    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        holder.tvtask.setText(t.get(position).task);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE - dd MMM");


        holder.tvcat.setText(t.get(position).category);
        holder.tvprimk.setText(String.valueOf(t.get(position).primk));
        holder.cvrl.setVisibility(View.GONE);
        holder.visible = false;
        holder.tvdateadded.setText(sdf.format(t.get(position).dateadded));
        holder.tvdatepending.setText(sdf.format(t.get(position).datepending));
        if(holder.tvpoints != null ){
            holder.tvpoints.setText(String.valueOf(t.get(position).points));
        }

        int colour = t.get(position).colour;
        holder.rl.setBackgroundColor(Task.colours[colour]);
        Log.d("BOUND", String.valueOf(t.get(position).colour)+" "+ String.valueOf(Task.colours[colour]));
        myInterface = (ViewPopulatorInterface)c;

        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myInterface.clicked(t.get(position));





            }
        });

        Log.d("VP", "BOUND"+String.valueOf(position));


    }



    @Override
    public int getItemCount() {
        return t.size();
    }
    public class Holder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView tvtask, tvcat, tvprimk, tvdateadded, tvdatepending, tvpoints;
        ImageButton bdelete, bdone;
        boolean visible;
        RelativeLayout rl, cvrl;





        public Holder(View v){

            super(v);
            cv = (CardView)v.findViewById(R.id.cv);
            tvtask = (TextView)v.findViewById(R.id.tvtask);
            tvcat = (TextView)v.findViewById(R.id.tvcat);
            tvprimk = (TextView)v.findViewById(R.id.primktv);
            bdelete = (ImageButton)v.findViewById(R.id.deletebutton);
            bdone = (ImageButton)v.findViewById(R.id.donebutton);
            rl = (RelativeLayout)v.findViewById(R.id.relativel);
            cvrl = (RelativeLayout)v.findViewById(R.id.cvrl);
            tvdateadded = (TextView)v.findViewById(R.id.tvdateadded);
            tvdatepending = (TextView)v.findViewById(R.id.tvdatepending);
            tvpoints = (TextView)v.findViewById(R.id.pointstv);
        }






    }
}
