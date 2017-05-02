package com.simpletodo.aayushf.simpletodo;

import android.app.DialogFragment;
import android.net.Uri;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
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

public class Main3Activity extends AppCompatActivity implements RecyclerFragment.OnFragmentInteractionListener, ViewPopulator.ViewPopulatorInterface, AdderDialog.AdderListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    DialogFragment d = new AdderDialog();
    private SectionsPagerAdapter mSectionsPagerAdapter;
    FloatingActionButton fab;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    boolean small = false;
    BottomSheetBehavior bsb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

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
        NestedScrollView nsview = (NestedScrollView)findViewById(R.id.bsv);
        if (nsview != null){
            bsb = BottomSheetBehavior.from(nsview);
            bsb.setPeekHeight(20);
            bsb.setState(BottomSheetBehavior.STATE_HIDDEN);

        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.bringToFront();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        d.show(getFragmentManager(), "Add a Task");
            }
        });

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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void changed(int position) {

    }

    @Override
    public void clicked(Task t) {
        ((RelativeLayout)findViewById(R.id.bottomsheetrl)).setBackgroundColor(Task.colours[t.colour]);
        ((TextView)findViewById(R.id.bottomsheettasktv)).setText(t.task);
        ((TextView)findViewById(R.id.bottomsheettag)).setText(t.category);
        if (bsb !=null) {
            bsb.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void Done(Task t) {
        TasksDBHelper helper = new TasksDBHelper(Main3Activity.this);
        helper.addTaskToDB(t);
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
            // Show 3 total pages.
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
}
