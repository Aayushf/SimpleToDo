package com.simpletodo.aayushf.simpletodo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by ayfadia on 5/15/17.
 */

public class TabAdapter extends FragmentStatePagerAdapter {
    boolean small;

    public void setSmall(boolean small) {
        this.small = small;
    }

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
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
