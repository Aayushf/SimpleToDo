package com.simpletodo.aayushf.simpletodo;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity implements  RecyclerFragment.OnFragmentInteractionListener, ViewPopulator.ViewPopulatorInterface, AdderDialog.AdderListener, ColourSelectionDialog.ColourSelectionDialogListener, NotifTimeSetterDialog.NotifTimeSetterListener, NavigationView.OnNavigationItemSelectedListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     *
     */
    DialogFragment ad = new AdderDialog();
    private SectionsPagerAdapter mSectionsPagerAdapter;
    FloatingActionButton fab;
    ActionBarDrawerToggle toggle;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    boolean small = false;
    BottomSheetBehavior bsb;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public interface Main3Interface{
        public void refreshLayout();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Intent i = getIntent();
        if (i!=null){
            int primknotif = i.getIntExtra("primknotif", -1);
            int notifid = i.getIntExtra("notifid", 0);
            setDoneAndRemove(primknotif, notifid);

        }





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(Main3Activity.this.getDrawable(R.drawable.ic_pending));
        tabLayout.getTabAt(1).setIcon(Main3Activity.this.getDrawable(R.drawable.ic_done));
        NestedScrollView nsview = (NestedScrollView)findViewById(R.id.bsnsvmain3);
        if (nsview != null){
            bsb = BottomSheetBehavior.from(nsview);
            bsb.setPeekHeight(100);
            bsb.setState(BottomSheetBehavior.STATE_HIDDEN);

        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if(drawer != null){
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();
        navigationView.bringToFront();




        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        ad.show(getFragmentManager(), "Add a Task");
            }
        });
        FloatingActionButton fabbsdone = (FloatingActionButton)findViewById(R.id.bottomsheetdonefab);
        fabbsdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TasksDBHelper helper = new TasksDBHelper(Main3Activity.this);
                RecyclerFragment rf = (RecyclerFragment)mSectionsPagerAdapter.getItem(mViewPager.getCurrentItem());
                rf.refreshMyLayout();
                helper.setDone(Integer.parseInt(((TextView)findViewById(R.id.tvprimkbs)).getText().toString()), !helper.getDone(Integer.parseInt(((TextView)findViewById(R.id.tvprimkbs)).getText().toString())));
            }
        });
        FloatingActionButton fabbsdelete = (FloatingActionButton)findViewById(R.id.bottomsheetdeletefab);
        fabbsdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TasksDBHelper helper = new TasksDBHelper(Main3Activity.this);
                helper.deleteTaskFromDB(Integer.parseInt(((TextView)findViewById(R.id.tvprimkbs)).getText().toString()));
                RecyclerFragment rf = (RecyclerFragment)mSectionsPagerAdapter.getItem(mViewPager.getCurrentItem());
                rf.refreshMyLayout();
            }
        });
        FloatingActionButton notifyfab = (FloatingActionButton)findViewById(R.id.notifyfab);
        notifyfab.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent i = new Intent(Main3Activity.this, Main3Activity.class);
                i.putExtra("primknotif", Integer.parseInt(((TextView)findViewById(R.id.tvprimkbs)).getText().toString()));
                i.putExtra("notifid", 01);
                PendingIntent pi = PendingIntent.getActivity(Main3Activity.this,0,i, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder notifbuilder = new NotificationCompat.Builder(Main3Activity.this).setSmallIcon(R.drawable.ic_check_black_24dp).setContentTitle(((TextView)findViewById(R.id.bottomsheettasktv)).getText().toString()).setContentText(((TextView)findViewById(R.id.bottomsheettag)).getText().toString()).setOngoing(true).addAction(R.drawable.ic_check_black_24dp, "Done", pi);

                NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                manager.notify(01, notifbuilder.build());
                NotifTimeSetterDialog d = new NotifTimeSetterDialog();
                d.setTaskPK(Integer.parseInt(((TextView)findViewById(R.id.tvprimkbs)).getText().toString()));
                d.show(getFragmentManager(), "Pick Time");

            }
        });


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
        getMenuInflater().inflate(R.menu.menu_main3, menu);
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
        }else if (id == R.id.smallornot){
            small = !small;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void changed(int position) {

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
        TasksDBHelper helper = new TasksDBHelper(Main3Activity.this);
        helper.addTaskToDB(t);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment d) {
        AdderDialog add = (AdderDialog)ad;
        add.changeColour(((ColourSelectionDialog)d).getColourSelected());


    }

    @Override
    public void timeSet(long time, int taskpk) {
        NotificationsDBHelper helper = new NotificationsDBHelper(Main3Activity.this);
        long l = helper.addNotifToDb(new TaskNotification(taskpk, time));
        Intent i = new Intent(Main3Activity.this , Notifier.class);
        i.putExtra("notifprimk", l);

        PendingIntent pi = PendingIntent.getBroadcast(Main3Activity.this , 0,i, 0);
        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, time, pi);

    }

    /**
     * A placeholder fragment containing a simple view.
     */
   public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return RecyclerFragment.newInstance(position>0, small);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Pending";
                case 1:
                    return "Done";
                }
            return null;
        }
    }
    public void setDoneAndRemove(int primk, int notifid){
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(notifid);
        TasksDBHelper helper = new TasksDBHelper(Main3Activity.this);
        helper.setDone(primk, true);



    }
}
