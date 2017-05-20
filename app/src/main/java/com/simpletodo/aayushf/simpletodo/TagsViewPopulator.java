package com.simpletodo.aayushf.simpletodo;

import android.content.Context;
import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ayfadia on 5/18/17.
 */

public class TagsViewPopulator extends RecyclerView.Adapter<TagsViewPopulator.Holder> {
    Context c;
    TasksDBHelper h;
    ArrayList<TagItem> t;

    public interface TagsInterface{
        public void tagclicked(String tag);
    }
    public TagsViewPopulator(Context c) {
        super();
        this.c = c;
        this.h = new TasksDBHelper(c);
        Log.d("TAGSVP", "CREATED");
        this.t = h.getTagsList();
        Log.d("TAGSVP", String.valueOf(t.size()));


    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(c).inflate(R.layout.simpledrawerlistitem, null);
        Holder holder = new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.tag.setText(t.get(position).tag);
        holder.ptasks.setText(String.valueOf(t.get(position).pendingt));
        Log.d("TAGSVP", String.valueOf(t.size()));
        final TagsInterface i = (TagsInterface)c;
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.tagclicked(t.get(position).tag);

            }
        });
    }


    @Override
    public int getItemCount() {
        return t.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView tag, ptasks;
        RelativeLayout rl;
        public Holder(View v) {
            super(v);
            tag = (TextView)v.findViewById(R.id.drawerlisttv);
            ptasks = (TextView)v.findViewById(R.id.drawerlistpendingtasks);
            rl = (RelativeLayout)v.findViewById(R.id.rlinsidedraweritem);

        }
    }
}
