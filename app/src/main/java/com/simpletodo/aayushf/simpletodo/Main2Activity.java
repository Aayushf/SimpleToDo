package com.simpletodo.aayushf.simpletodo;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,  RecyclerFragment.OnFragmentInteractionListener, ViewPopulator.ViewPopulatorInterface, AdderDialog.AdderListener, ColourSelectionDialog.ColourSelectionDialogListener, NotifTimeSetterDialog.NotifTimeSetterListener{
    ViewPager viewPager;
    TabLayout tabLayout;
    BottomSheetBehavior bsb;
    DialogFragment ad = new AdderDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager)findViewById(R.id.vp);
        tabLayout = (TabLayout)findViewById(R.id.tl);
        TabAdapter adp = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adp);
        tabLayout.setupWithViewPager(viewPager);
        NestedScrollView nsview = (NestedScrollView)findViewById(R.id.bsnsvmain3);
        if (nsview != null){
            bsb = BottomSheetBehavior.from(nsview);
            bsb.setPeekHeight(100);
            bsb.setState(BottomSheetBehavior.STATE_HIDDEN);

        }
        FloatingActionButton fabbsdone = (FloatingActionButton)findViewById(R.id.bottomsheetdonefab);
        fabbsdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TasksDBHelper helper = new TasksDBHelper(Main2Activity.this);
                helper.setDone(Integer.parseInt(((TextView)findViewById(R.id.tvprimkbs)).getText().toString()), !helper.getDone(Integer.parseInt(((TextView)findViewById(R.id.tvprimkbs)).getText().toString())));
            }
        });
        FloatingActionButton fabbsdelete = (FloatingActionButton)findViewById(R.id.bottomsheetdeletefab);
        fabbsdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TasksDBHelper helper = new TasksDBHelper(Main2Activity.this);
                helper.deleteTaskFromDB(Integer.parseInt(((TextView)findViewById(R.id.tvprimkbs)).getText().toString()));
            }
        });
        FloatingActionButton notifyfab = (FloatingActionButton)findViewById(R.id.notifyfab);
        notifyfab.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this, Main3Activity.class);
                i.putExtra("primknotif", Integer.parseInt(((TextView)findViewById(R.id.tvprimkbs)).getText().toString()));
                i.putExtra("notifid", 01);
                PendingIntent pi = PendingIntent.getActivity(Main2Activity.this,0,i, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder notifbuilder = new NotificationCompat.Builder(Main2Activity.this).setSmallIcon(R.drawable.ic_check_black_24dp).setContentTitle(((TextView)findViewById(R.id.bottomsheettasktv)).getText().toString()).setContentText(((TextView)findViewById(R.id.bottomsheettag)).getText().toString()).setOngoing(true).addAction(R.drawable.ic_check_black_24dp, "Done", pi);

                NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                manager.notify(01, notifbuilder.build());
                NotifTimeSetterDialog d = new NotifTimeSetterDialog();
                d.setTaskPK(Integer.parseInt(((TextView)findViewById(R.id.tvprimkbs)).getText().toString()));
                d.show(getFragmentManager(), "Pick Time");

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void setDoneAndRemove(int primk, int notifid){
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(notifid);
        TasksDBHelper helper = new TasksDBHelper(Main2Activity.this);
        helper.setDone(primk, true);



    }
    @Override
    public void clicked(Task t) {
        final java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("EEE - dd MMM ");

        ((RelativeLayout)findViewById(R.id.bottomsheetrl)).setBackgroundColor(Task.colours[t.colour]);
        ((TextView)findViewById(R.id.bottomsheettasktv)).setText(t.task);
        ((TextView)findViewById(R.id.bottomsheettag)).setText(t.category);
        ((TextView)findViewById(R.id.bottomsheettvdateadded)).setText("Added on "+format.format(t.dateadded));
        ((TextView)findViewById(R.id.bottomsheettvdatepending)).setText("Due on "+format.format(t.datepending));
        ((TextView)findViewById(R.id.tvprimkbs)).setText(String.valueOf(t.primk));

        if (bsb !=null) {
            bsb.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }



    @Override
    public void Done(Task t) {
        TasksDBHelper helper = new TasksDBHelper(Main2Activity.this);
        helper.addTaskToDB(t);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment d) {
        AdderDialog add = (AdderDialog)ad;
        add.changeColour(((ColourSelectionDialog)d).getColourSelected());


    }

    @Override
    public void timeSet(long time, int taskpk) {
        NotificationsDBHelper helper = new NotificationsDBHelper(Main2Activity.this);
        long l = helper.addNotifToDb(new TaskNotification(taskpk, time));
        Intent i = new Intent(Main2Activity.this , Notifier.class);
        i.putExtra("notifprimk", l);

        PendingIntent pi = PendingIntent.getBroadcast(Main2Activity.this , 0,i, 0);
        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, time, pi);

    }
    @Override
    public void changed(int position) {

    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        
    }
}




