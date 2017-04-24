package com.simpletodo.aayushf.simpletodo;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ColourSelectionDialog.ColourSelectionDialogListener, ViewPopulator.ViewPopulatorInterface {
    RecyclerView rv;
    TasksDBHelper helper;
    ViewPopulator vppending;
    ViewPopulator vpdone;
    int colour;
    boolean done;
    public static final String[] quotes = {"It isn't over till you succeed.", "The fact that you are not where you want to be should be enough motivation.", ""};




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_done:
                    ((RelativeLayout)findViewById(R.id.rl)).setVisibility(View.INVISIBLE);
                    ((RecyclerView)findViewById(R.id.rv)).setVisibility(View.VISIBLE);
                    ((RecyclerView)findViewById(R.id.rv)).setAdapter(new ViewPopulator(MainActivity.this, true));
                    done = true;
                    return true;
                case R.id.navigation_pending:
                    ((RecyclerView)findViewById(R.id.rv)).setVisibility(View.VISIBLE);
                    ((RelativeLayout)findViewById(R.id.rl)).setVisibility(View.INVISIBLE);
                    ((RecyclerView)findViewById(R.id.rv)).setAdapter(new ViewPopulator(MainActivity.this, false));
                    done = false;
                    return true;
                case R.id.navigation_add:
                    ((RelativeLayout)findViewById(R.id.rl)).setVisibility(View.VISIBLE);
                    ((RecyclerView)findViewById(R.id.rv)).setVisibility(View.GONE);

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
        Button b = (Button)findViewById(R.id.button);
        final EditText ettask = (EditText)findViewById(R.id.ettask);
        final EditText etcat = (EditText)findViewById(R.id.etcat);
        rv = (RecyclerView)findViewById(R.id.rv);
        ArrayList<Task> t = new ArrayList<Task>();
        helper = new TasksDBHelper(this);
        t = helper.getAllTasks();
        rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        vppending = new ViewPopulator(this,false );
        vpdone = new ViewPopulator(this, true);
        rv.setAdapter(vpdone);

        CardView cvcolour = (CardView)findViewById(R.id.cvcolours);
        cvcolour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment d = new ColourSelectionDialog();
                d.show(getFragmentManager(), "Choose Colour");

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task t = new Task(ettask.getText().toString(), 0, etcat.getText().toString(), colour, false);
                TasksDBHelper helper = new TasksDBHelper(MainActivity.this);
                helper.addTaskToDB(t);
            }
        });







    }


    @Override
    public void onDialogPositiveClick(DialogFragment d) {
        ColourSelectionDialog dia = (ColourSelectionDialog)d;
        colour =dia.getColourSelected();
    }

    @Override
    public void changed(int position) {
        rv.setAdapter(new ViewPopulator(MainActivity.this, done));

    }
}
