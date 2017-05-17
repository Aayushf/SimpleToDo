package com.simpletodo.aayushf.simpletodo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecyclerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecyclerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecyclerFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";



    // TODO: Rename and change types of parameters
    private boolean done;
    private boolean small;
    View v;
    private String tagtodisplay;


    public RecyclerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecyclerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecyclerFragment newInstance(boolean done, boolean small, String tagtodisplay) {
        RecyclerFragment fragment = new RecyclerFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, done);
        args.putBoolean(ARG_PARAM2, small);
        args.putString(ARG_PARAM3, tagtodisplay);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            done = getArguments().getBoolean(ARG_PARAM1);
            small = getArguments().getBoolean(ARG_PARAM2);
            tagtodisplay = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.recycler, null);
        RecyclerView rv = (RecyclerView)v.findViewById(R.id.fragmentrecyclerview);
        if (small)
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        else
            rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        ViewPopulator vp = new ViewPopulator(getActivity(), done, small, null);
        vp.setTagtodisplay(tagtodisplay);
        rv.setAdapter(vp);



        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    public void refreshFragment(String tagtodisplayupdated){
        RecyclerView rv = (RecyclerView)v.findViewById(R.id.fragmentrecyclerview);
        if (small)
            rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        else
            rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        ViewPopulator vp = new ViewPopulator(getActivity(), done, small, tagtodisplayupdated);
        vp.setTagtodisplay(tagtodisplayupdated);
        rv.setAdapter(vp);
        Log.d("RECYCLERFRAGMENT", "REFRESHED");




    }


}





