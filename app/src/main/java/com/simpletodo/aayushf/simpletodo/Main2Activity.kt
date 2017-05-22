package com.simpletodo.aayushf.simpletodo

import android.app.AlarmManager
import android.app.DialogFragment
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.NotificationCompat
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.app_bar_main2.*
import kotlinx.android.synthetic.main.content_main2.*

import java.util.Random

class Main2Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, ViewPopulator.ViewPopulatorInterface, AdderDialog.AdderListener, ColourSelectionDialog.ColourSelectionDialogListener, NotifTimeSetterDialog.NotifTimeSetterListener, TagsViewPopulator.TagsInterface {
    internal lateinit var  viewPager: ViewPager
    internal lateinit var tabLayout: TabLayout
    internal var bsb: BottomSheetBehavior<*>? = null
    internal var ad: DialogFragment = AdderDialog()
    internal lateinit var adp: TabAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)


        viewPager = vp as ViewPager
        tabLayout = tl as TabLayout
        adp = TabAdapter(supportFragmentManager)
        viewPager.adapter = adp
        tabLayout.setupWithViewPager(viewPager)
        val nsview = findViewById(R.id.bsnsvmain3) as NestedScrollView
        if (nsview != null) {
            bsb = BottomSheetBehavior.from(nsview)
            bsb!!.peekHeight = 100
            bsb!!.state = BottomSheetBehavior.STATE_HIDDEN

        }
        updatePoints()
        val tvquote = findViewById(R.id.quotetvnav) as TextView
        val r = Random()
        tvquote.text = Task.quotes[r.nextInt(1)]
        val fabbsdone = findViewById(R.id.bottomsheetdonefab) as FloatingActionButton
        fabbsdone.setOnClickListener {
            val helper = TasksDBHelper(this@Main2Activity)
            helper.setDone(Integer.parseInt((findViewById(R.id.tvprimkbs) as TextView).text.toString()), !helper.getDone(Integer.parseInt((findViewById(R.id.tvprimkbs) as TextView).text.toString())))
            updatePoints()
            adp.notifyDataSetChanged()
        }
        val fabbsdelete = findViewById(R.id.bottomsheetdeletefab) as FloatingActionButton
        fabbsdelete.setOnClickListener {
            val helper = TasksDBHelper(this@Main2Activity)
            helper.deleteTaskFromDB(Integer.parseInt((findViewById(R.id.tvprimkbs) as TextView).text.toString()))
            updatePoints()
            adp.notifyDataSetChanged()
        }
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            ad.show(fragmentManager, "Add a Task")
            updatePoints()
            adp.notifyDataSetChanged()
        }
        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_pending)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_done)
        val notifyfab = findViewById(R.id.notifyfab) as FloatingActionButton
        notifyfab.setOnClickListener {
            val i = Intent(this@Main2Activity, Main2Activity::class.java)
            i.putExtra("primknotif", Integer.parseInt((findViewById(R.id.tvprimkbs) as TextView).text.toString()))
            i.putExtra("notifid", 1)
            val pi = PendingIntent.getActivity(this@Main2Activity, 0, i, PendingIntent.FLAG_UPDATE_CURRENT)
            val notifbuilder = NotificationCompat.Builder(this@Main2Activity).setSmallIcon(R.drawable.ic_check_black_24dp).setContentTitle((findViewById(R.id.bottomsheettasktv) as TextView).text.toString()).setContentText((findViewById(R.id.bottomsheettag) as TextView).text.toString()).setOngoing(true).addAction(R.drawable.ic_check_black_24dp, "Done", pi)

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(1, notifbuilder.build())
            val d = NotifTimeSetterDialog()
            d.setTaskPK(Integer.parseInt((findViewById(R.id.tvprimkbs) as TextView).text.toString()))
            d.show(fragmentManager, "Pick Time")
        }


        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val helper = TasksDBHelper(this@Main2Activity)
        val adapter = TagsViewPopulator(this@Main2Activity)
        val rvdrawer = findViewById(R.id.rvindrawer) as RecyclerView
        rvdrawer.layoutManager = LinearLayoutManager(this@Main2Activity)
        rvdrawer.adapter = adapter
        //         NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //        navigationView.setNavigationItemSelectedListener(this);
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main2, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    fun setDoneAndRemove(primk: Int, notifid: Int) {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancel(notifid)
        val helper = TasksDBHelper(this@Main2Activity)
        helper.setDone(primk, true)


    }

    override fun clicked(t: Task) {
        val format = java.text.SimpleDateFormat("EEE - dd MMM ")
        (findViewById(R.id.bottomsheetcv) as CardView).setCardBackgroundColor(Task.colours[t.colour])
        (findViewById(R.id.bottomsheettasktv) as TextView).text = t.task
        (findViewById(R.id.bottomsheettag) as TextView).text = t.category
        (findViewById(R.id.bottomsheettvdateadded) as TextView).text = "Added on " + format.format(t.dateadded)
        (findViewById(R.id.bottomsheettvdatepending) as TextView).text = "Due on " + format.format(t.datepending)
        (findViewById(R.id.tvprimkbs) as TextView).text = t.primk.toString()

        if (bsb != null) {
            bsb!!.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }


    override fun Done(t: Task) {
        val helper = TasksDBHelper(this@Main2Activity)
        helper.addTaskToDB(t)
        viewPager.adapter.notifyDataSetChanged()
    }

    override fun onDialogPositiveClick(d: DialogFragment) {
        val add = ad as AdderDialog
        add.changeColour((d as ColourSelectionDialog).colourSelected)


    }

    override fun timeSet(time: Long, taskpk: Int) {
        val helper = NotificationsDBHelper(this@Main2Activity)
        val l = helper.addNotifToDb(TaskNotification(taskpk, time))
        val i = Intent(this@Main2Activity, Notifier::class.java)
        i.putExtra("notifprimk", l)

        val pi = PendingIntent.getBroadcast(this@Main2Activity, 0, i, 0)
        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.set(AlarmManager.RTC_WAKEUP, time, pi)

    }

    override fun changed(position: Int) {

    }

    fun updatePoints() {
        val tvpoints = findViewById(R.id.pointstvdrawer) as TextView

        val h = TasksDBHelper(this@Main2Activity)
        tvpoints.text = h.totalPoints.toString()

    }


    override fun tagclicked(tag: String) {
        adp.setTagtodisplay(tag)
        adp.notifyDataSetChanged()
        title = tag
        tabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_pending)
        tabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_done)

    }
}




