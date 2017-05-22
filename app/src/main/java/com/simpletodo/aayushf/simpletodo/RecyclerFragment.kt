package com.simpletodo.aayushf.simpletodo

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RecyclerFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RecyclerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecyclerFragment : Fragment() {
    internal lateinit var v: View
    // TODO: Rename and change types of parameters
    private var done: Boolean = false
    private var small: Boolean = false
    private var tagtodisplay: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            done = arguments.getBoolean(ARG_PARAM1)
            small = arguments.getBoolean(ARG_PARAM2)
            tagtodisplay = arguments.getString(ARG_PARAM3)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        v = inflater!!.inflate(R.layout.recycler, null)
        val rv = v.findViewById(R.id.fragmentrecyclerview) as RecyclerView
        if (small)
            rv.layoutManager = LinearLayoutManager(activity)
        else
            rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val vp = ViewPopulator(activity, done, small, "")
        vp.setTagtodisplay(tagtodisplay)
        rv.adapter = vp


        return v
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }

    fun refreshFragment(tagtodisplayupdated: String) {
        val rv = v.findViewById(R.id.fragmentrecyclerview) as RecyclerView
        if (small)
            rv.layoutManager = LinearLayoutManager(activity)
        else
            rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val vp = ViewPopulator(activity, done, small, tagtodisplayupdated)
        vp.setTagtodisplay(tagtodisplayupdated)
        rv.adapter = vp
        Log.d("RECYCLERFRAGMENT", "REFRESHED")


    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"
        private val ARG_PARAM3 = "param3"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @param param1 Parameter 1.
         * *
         * @param param2 Parameter 2.
         * *
         * @return A new instance of fragment RecyclerFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(done: Boolean, small: Boolean, tagtodisplay: String): RecyclerFragment {
            val fragment = RecyclerFragment()
            val args = Bundle()
            args.putBoolean(ARG_PARAM1, done)
            args.putBoolean(ARG_PARAM2, small)
            args.putString(ARG_PARAM3, tagtodisplay)
            fragment.arguments = args
            return fragment
        }
    }


}// Required empty public constructor





