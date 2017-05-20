package com.simpletodo.aayushf.simpletodo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

/**
 * Created by ayfadia on 5/18/17.
 */

public class DrawerListViewPopulator extends ArrayAdapter<TagItem> {
    public DrawerListViewPopulator(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }
}
