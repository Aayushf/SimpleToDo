package com.simpletodo.aayushf.simpletodo

import android.content.Context
import android.nfc.Tag
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView

import java.util.ArrayList

/**
 * Created by ayfadia on 5/18/17.
 */

class TagsViewPopulator(internal var c: Context) : RecyclerView.Adapter<TagsViewPopulator.Holder>() {
    internal var h: TasksDBHelper
    internal var t: ArrayList<TagItem>

    interface TagsInterface {
        fun tagclicked(tag: String)
    }

    init {
        this.h = TasksDBHelper(c)
        Log.d("TAGSVP", "CREATED")
        this.t = h.tagsList
        Log.d("TAGSVP", t.size.toString())


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v: View
        v = LayoutInflater.from(c).inflate(R.layout.simpledrawerlistitem, null)
        val holder = Holder(v)
        return holder
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tag.text = t[position].tag
        holder.ptasks.text = t[position].pendingt.toString()
        Log.d("TAGSVP", t.size.toString())
        val i = c as TagsInterface
        holder.rl.setOnClickListener { i.tagclicked(t[position].tag) }
    }


    override fun getItemCount(): Int {
        return t.size
    }

    inner class Holder(v: View) : RecyclerView.ViewHolder(v) {
        internal var tag: TextView
        internal var ptasks: TextView
        internal var rl: RelativeLayout

        init {
            tag = v.findViewById(R.id.drawerlisttv) as TextView
            ptasks = v.findViewById(R.id.drawerlistpendingtasks) as TextView
            rl = v.findViewById(R.id.rlinsidedraweritem) as RelativeLayout

        }
    }
}
