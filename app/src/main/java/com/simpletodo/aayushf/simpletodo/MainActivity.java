package com.simpletodo.aayushf.simpletodo;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ColourSelectionDialog.ColourSelectionDialogListener, ViewPopulator.ViewPopulatorInterface, AdderDialog.AdderListener {
    RecyclerView rv;
    TasksDBHelper helper;
    ViewPopulator vppending;
    ViewPopulator vpdone;
    public static int colour;
    boolean done;
    final DialogFragment d = new AdderDialog();
    boolean small = true;
    public static final String[] quotes = {"It isn't over till you succeed.", "The fact that you are not where you want to be should be enough motivation.", ""};
    LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
    StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_done:

                    ((RecyclerView)findViewById(R.id.rv)).setVisibility(View.VISIBLE);
                    if (small)
                        ((RecyclerView)findViewById(R.id.rv)).setLayoutManager(llm);
                    else
                        ((RecyclerView)findViewById(R.id.rv)).setLayoutManager(sglm);

                    ((RecyclerView)findViewById(R.id.rv)).setAdapter(new ViewPopulator(MainActivity.this, true, small));

                    done = true;

                    return true;
                case R.id.navigation_pending:
                    ((RecyclerView)findViewById(R.id.rv)).setVisibility(View.VISIBLE);
                    if (small)
                        ((RecyclerView)findViewById(R.id.rv)).setLayoutManager(llm);
                    else
                        ((RecyclerView)findViewById(R.id.rv)).setLayoutManager(sglm);

                    ((RecyclerView)findViewById(R.id.rv)).setAdapter(new ViewPopulator(MainActivity.this, false, small));
                    done = false;
                    return true;
                }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        rv = (RecyclerView)findViewById(R.id.rv);
        rv.setAdapter(new ViewPopulator(MainActivity.this, true, small));

        if (small)
            rv.setLayoutManager(llm);
        else
            rv.setLayoutManager(sglm);









        FloatingActionButton fabadd = (FloatingActionButton)findViewById(R.id.addfab);
        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.show(getFragmentManager(), "");

            }
        });
        FloatingActionButton fabchangeadapter = (FloatingActionButton)findViewById(R.id.changeadapterfab);
        fabchangeadapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                small = !small;

            }
        });








    }


    @Override
    public void onDialogPositiveClick(DialogFragment df) {
        ColourSelectionDialog dia = (ColourSelectionDialog)df;
        colour =dia.getColourSelected();
        Log.d("MainActivity", "ONPOSITIVE");
        AdderDialog ad = (AdderDialog)d;
        ad.changeColour(colour);


    }

    @Override
    public void changed(int position) {
        rv.setAdapter(new ViewPopulator(MainActivity.this, done, small));

    }

    @Override
    public void Done(Task t) {
        TasksDBHelper helper = new TasksDBHelper(MainActivity.this);
        helper.addTaskToDB(t);
        AdderDialog ad = (AdderDialog)d;
        ad.dismiss();

    }

    @Override
    public void clicked(Task t) {

    }
}
