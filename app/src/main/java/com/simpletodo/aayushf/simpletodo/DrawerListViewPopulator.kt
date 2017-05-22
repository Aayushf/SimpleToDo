package com.simpletodo.aayushf.simpletodo

import android.content.Context
import android.support.annotation.LayoutRes
import android.widget.ArrayAdapter

/**
 * Created by ayfadia on 5/18/17.
 */

class DrawerListViewPopulator(context: Context, @LayoutRes resource: Int) : ArrayAdapter<TagItem>(context, resource)
