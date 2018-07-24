package com.example.andrew.midterm;

/*
        Midterm ITCS 4180 - Andrew Hartness
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private final String URL_CON = "https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json";
    ArrayList<App> appList = new ArrayList<>();
    ArrayList<App> filteredAppList = new ArrayList<>();
    Switch orderSwitch;
    ImageButton refresh;
    ProgressDialog pd;

    ListView listView;
    RecyclerView recyclerView;

    AppAdapter adapter;
    FilteredAdapter rvAdapter;

    DatabaseDataManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        orderSwitch = (Switch) findViewById(R.id.switch1);
        orderSwitch.setText("Ascending");
        refresh = (ImageButton) findViewById(R.id.imageButton);

        dm = new DatabaseDataManager(this);

        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading Apps...");
        pd.setCancelable(false);
        pd.show();

        final GetDataAsyncTask task = new GetDataAsyncTask(this);

        try{
            appList = task.execute(URL_CON).get();

            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pd.dismiss();
                }
            }, 1300);

            sortListDescending(appList);

            listView = (ListView) findViewById(R.id.listView);

            recyclerView = (RecyclerView) findViewById(R.id.rvList);
            recyclerView.setHasFixedSize(true);

            adapter = new AppAdapter(this, R.layout.app_item_layout, appList);
            rvAdapter = new FilteredAdapter(this, filteredAppList);

            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            listView.setAdapter(adapter);
            adapter.setNotifyOnChange(true);

            recyclerView.setAdapter(rvAdapter);
            //rvAdapter.setNotifyOnChange(true);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    dm.saveNote(appList.get(position));
                    filteredAppList = dm.getAllApps();
                    Log.d("array", filteredAppList.toString());
                    rvAdapter.addItemToList(appList.get(position));
                    rvAdapter.notifyDataSetChanged();
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        orderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    sortListDescending(appList);
                    orderSwitch.setText("Ascending");
                    adapter.notifyDataSetChanged();
                }else{
                    sortListAscending(appList);
                    orderSwitch.setText("Descending");
                    adapter.notifyDataSetChanged();
                }
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
                //rv
            }
        });
    }

    //small to large
    public void sortListDescending(ArrayList<App> list){
        Collections.sort(list, new Comparator<App>() {
            @Override
            public int compare(App app1, App app2)
            {
                return  app1.getPrice().compareTo(app2.getPrice());
            }
        });
    }

    public void sortListAscending(ArrayList<App> list){
        Collections.sort(list, new Comparator<App>() {
            @Override
            public int compare(App app1, App app2) {
                return app2.getPrice().compareTo(app1.getPrice());
            }
        });
    }
}
