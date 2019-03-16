package com.example.qualitastest.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.qualitastest.R;
import com.example.qualitastest.SQLite.SqliteHelper;
import com.example.qualitastest.adapter.CanadaAdapter;
import com.example.qualitastest.model.CanadaBean;
import com.example.qualitastest.model.CanadaDeatilsPojo;
import com.example.qualitastest.model.CanadaModel;
import com.example.qualitastest.presenter.CanadaPresenter;

import java.util.ArrayList;
import java.util.List;

public class AboutCanada extends BaseActivity implements CanadaView {

    private SwipeRefreshLayout srl_id;
    private RecyclerView canada_recycler;
    private ProgressBar progressBar;
    private CanadaPresenter canadaPresenter;
    private Toolbar toolbar;
    private CanadaAdapter canadaAdapter;
    private SqliteHelper sqliteHelper;
    private ArrayList<CanadaDeatilsPojo> canadaDeatilsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_canada);
        init();
        if (isNetworkConnected()) {
            getAndInsertData();
        } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Please check internet connection.", Toast.LENGTH_SHORT).show();
        }

    }

    private void getAndInsertData() {
        canadaPresenter.getCandaData();
    }

    private void init() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        canada_recycler = findViewById(R.id.canada_recycler_id);
        srl_id = findViewById(R.id.srl_id);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        canada_recycler.setLayoutManager(linearLayoutManager);
        canada_recycler.setHasFixedSize(true);
        progressBar = findViewById(R.id.progressbar_id);

        canadaPresenter = new CanadaModel(this);
        sqliteHelper = new SqliteHelper(this);


        srl_id.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNetworkConnected()) {
                    refresh();
                } else {
                    Toast.makeText(AboutCanada.this, "Please check internet connection.", Toast.LENGTH_SHORT).show();
                }


                srl_id.setRefreshing(false);
            }
        });

        canada_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int position = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (position > 0) {
                    srl_id.setEnabled(false);

                } else {
                    srl_id.setEnabled(true);
                }
            }
        });
    }

    private void refresh() {

        canadaPresenter.getCandaData();
        if (canadaAdapter != null) {
            canadaAdapter.clear();
            canadaDeatilsList.clear();
            canada_recycler.setVisibility(View.GONE);

        }

    }

    @Override
    public void isSuccess(String title, List<CanadaBean.Row> rows) {


        /*first check if my title table is empty then insert data into table*/
        if (sqliteHelper.isEmpty() == 0) {

            long id = sqliteHelper.inserTitle(title);
            CanadaDeatilsPojo canadaDeatilsPojo = sqliteHelper.getTitleAndId(id);
            int title_id = canadaDeatilsPojo.getTitle_id();
            toolbar.setTitle(canadaDeatilsPojo.getTitle());

            for (int i = 0; i < rows.size(); i++) {
                sqliteHelper.insertRowData(title_id, rows.get(i).getTitle(), rows.get(i).getDescription(), rows.get(i).getImageHref());
            }

            callAdapter(title_id);

        } else {
            toolbar.setTitle(title);
            /*in case row array size is increses then add extra elements into the row table*/
            int size = sqliteHelper.rowTableSize();//get size of row table
            if (rows.size() > size) {
                //here title_id is always 1 so i pass it static
                for (int i = size; i < rows.size(); i++) {
                    sqliteHelper.insertRowData(1, rows.get(i).getTitle(), rows.get(i).getDescription(), rows.get(i).getImageHref());
                }
                callAdapter(1);

            } else {

                callAdapter(1);
            }


        }


    }

    private void callAdapter(int title_id) {

        canadaDeatilsList = sqliteHelper.getRowTableData(title_id);
        canadaAdapter = new CanadaAdapter(AboutCanada.this, canadaDeatilsList);
        Log.d("canadaDeatilsList", "" + canadaDeatilsList.size());
        canada_recycler.setAdapter(canadaAdapter);

    }

    @Override
    public void onFailure(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        canada_recycler.setVisibility(View.GONE);
    }

    @Override
    public void hideProgrssBar() {
        canada_recycler.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            if (isNetworkConnected()) {
                refresh();
            } else {
                Toast.makeText(AboutCanada.this, "Please check internet connection.", Toast.LENGTH_SHORT).show();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
