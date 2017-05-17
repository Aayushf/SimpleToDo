package com.simpletodo.aayushf.simpletodo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by ayfadia on 5/15/17.
 */

public class TabAdapter extends FragmentStatePagerAdapter {
    boolean small;
    String tagtodisplay;

    @Override
    public int getItemPosition(Object object) {
        RecyclerFragment rf = (RecyclerFragment)object;
        rf.refreshFragment(tagtodisplay);
        return super.getItemPosition(object);
    }

    public String getTagtodisplay() {

        return tagtodisplay;
    }

    public void setTagtodisplay(String tagtodisplay) {
        TabAdapter.this.notifyDataSetChanged();
        if (tagtodisplay == null){
            this.tagtodisplay = " ";
        }else{
            this.tagtodisplay = tagtodisplay;
        }


    }

    public void setSmall(boolean small) {
        this.small = small;
    }

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d("TABADAPTER", "GETITEM CALLED");
        return RecyclerFragment.newInstance(position>0, small, tagtodisplay);


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
