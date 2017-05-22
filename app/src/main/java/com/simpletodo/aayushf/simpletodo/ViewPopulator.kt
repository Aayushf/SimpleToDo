package com.simpletodo.aayushf.simpletodo

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView

import java.util.ArrayList

/**
 * Created by ayfadia on 4/19/17.
 */

class ViewPopulator(internal var c: Context, done: Boolean, internal var small: Boolean?, tagtodisplay: String) : RecyclerView.Adapter<ViewPopulator.Holder>() {
    internal var t: ArrayList<Task>
    internal var context: Context? = null
    internal var tagtodisplay :String = ""
    internal lateinit var myInterface: ViewPopulatorInterface

    init {
        val helper = TasksDBHelper(c)
        if (tagtodisplay == null) {
            this.tagtodisplay = ""
        } else {
            this.tagtodisplay = tagtodisplay
        }

        if (done) {
            t = helper.getDoneTasks(tagtodisplay)
        } else {
            t = helper.getPendingTasks(tagtodisplay)

        }
        Log.d("VP", "ADAPTER CREATED")
    }

    fun setTagtodisplay(tagtodisplay: String) {

        this.tagtodisplay = tagtodisplay
        val helper = TasksDBHelper(c)
        this@ViewPopulator.notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v: View
        if (small!!) {
            v = LayoutInflater.from(c).inflate(R.layout.smalllistitem, null)
        } else {
            v = LayoutInflater.from(c).inflate(R.layout.listitemcardstaggered, null)
        }

        val h = Holder(v)
        Log.d("VP", "ONCREATEVIEWHOLDER")

        return h


    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvtask.setText(t[position].task)
        val sdf = SimpleDateFormat("EEE - dd MMM")


        holder.tvcat.setText(t[position].category)
        holder.tvprimk.setText((t[position].primk).toString())
        holder.cvrl.visibility = View.GONE
        holder.visible = false
        holder.tvdateadded.text = sdf.format(t[position].dateadded)
        holder.tvdatepending.text = sdf.format(t[position].datepending)
        if (holder.tvpoints != null) {
            holder.tvpoints!!.setText((t[position].points).toString())
        }

        val colour = t[position].colour
        holder.rl.setBackgroundColor(Task.colours[colour])
        Log.d("BOUND", (t[position].colour).toString() + " " + Task.colours[colour].toString())
        myInterface = c as ViewPopulatorInterface

        holder.rl.setOnClickListener { myInterface.clicked(t[position]) }

        Log.d("VP", "BOUND" + position.toString())


    }

    override fun getItemCount(): Int {
        return t.size
    }


    interface ViewPopulatorInterface {
        fun changed(position: Int)

        fun clicked(t: Task)
    }

    inner class Holder(v: View) : RecyclerView.ViewHolder(v) {
        internal var cv: CardView
        internal var tvtask: TextView
        internal var tvcat: TextView
        internal var tvprimk: TextView
        internal var tvdateadded: TextView
        internal var tvdatepending: TextView
        internal var tvpoints: TextView? = null
        internal var bdelete: ImageButton
        internal var bdone: ImageButton
        internal var visible: Boolean = false
        internal var rl: RelativeLayout
        internal var cvrl: RelativeLayout


        init {
            cv = v.findViewById(R.id.cv) as CardView
            tvtask = v.findViewById(R.id.tvtask) as TextView
            tvcat = v.findViewById(R.id.tvcat) as TextView
            tvprimk = v.findViewById(R.id.primktv) as TextView
            bdelete = v.findViewById(R.id.deletebutton) as ImageButton
            bdone = v.findViewById(R.id.donebutton) as ImageButton
            rl = v.findViewById(R.id.relativel) as RelativeLayout
            cvrl = v.findViewById(R.id.cvrl) as RelativeLayout
            tvdateadded = v.findViewById(R.id.tvdateadded) as TextView
            tvdatepending = v.findViewById(R.id.tvdatepending) as TextView
            tvpoints = v.findViewById(R.id.pointstv) as TextView
        }


    }
}
