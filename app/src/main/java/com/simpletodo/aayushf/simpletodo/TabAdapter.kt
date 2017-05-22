package com.simpletodo.aayushf.simpletodo

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log

/**
 * Created by ayfadia on 5/15/17.
 */

class TabAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    internal var small: Boolean = false
    internal  var tagtodisplay = ""

    override fun getItemPosition(`object`: Any?): Int {
        val rf = `object` as RecyclerFragment?
        rf!!.refreshFragment(tagtodisplay)
        return super.getItemPosition(`object`)
    }

    fun getTagtodisplay(): String {

        return tagtodisplay
    }

    fun setTagtodisplay(tagtodisplay: String?) {
        this@TabAdapter.notifyDataSetChanged()
        if (tagtodisplay == null) {
            this.tagtodisplay = " "
        } else {
            this.tagtodisplay = tagtodisplay
        }


    }

    fun setSmall(small: Boolean) {
        this.small = small
    }

    override fun getItem(position: Int): Fragment {
        Log.d("TABADAPTER", "GETITEM CALLED")
        return RecyclerFragment.newInstance(position > 0, small, tagtodisplay)


    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return when (position) {
            0 -> "Pending"
            1 -> "Done"
            else -> {
                return null

            }
        }


    }
}
