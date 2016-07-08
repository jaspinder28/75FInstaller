package com.x75f.installer.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;

import com.x75f.installer.Adapters.CcuListAdapter;

import com.x75f.installer.R;
import com.x75f.installer.Utils.Generic_Methods;
import com.x75f.installer.Utils.Otp_List;
import com.x75f.installer.Utils.UsersData;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class CcuListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ArrayList<UsersData> users;
    @InjectView(R.id.SearchCcuNameAddress)
    SearchView SearchCcuNameAddress;
    @InjectView(R.id.CcuList)
    ListView CcuList;
    @InjectView(R.id.tool_bar)
    Toolbar tool_bar;
    ArrayList<String> search;
    private static CcuListActivity _singleton;
//    private SQLliteAdapter sqLlite;
    Filter filter;

    public static CcuListActivity getSingletonContext() {
        return _singleton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ccu_search_list);
        ButterKnife.inject(this);
        _singleton = this;
        //database to store otp verification status
//        sqLlite = new SQLliteAdapter(CcuListActivity.getSingletonContext());

        tool_bar.setTitle("Welcome " + getIntent().getStringExtra("email"));
        setSupportActionBar(tool_bar);

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(CcuListActivity.this, Manifest.permission.GET_ACCOUNTS)
                    != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(CcuListActivity.this, android.Manifest.permission.GET_ACCOUNTS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CcuListActivity.this,
                        new String[]{android.Manifest.permission.GET_ACCOUNTS, android.Manifest.permission.GET_ACCOUNTS},
                        1);
            }
        }
//        if (Generic_Methods.otpLists != null){
//            Generic_Methods.getToast(CcuListActivity.getSingletonContext(),Generic_Methods.otpLists.size()+"");
//        }

        users = new ArrayList<>();
        users = (ArrayList<UsersData>) getIntent().getSerializableExtra("ccudata");

    }

    private void setupSearchView() {
        SearchCcuNameAddress.setIconifiedByDefault(false);
        SearchCcuNameAddress.setOnQueryTextListener(this);
        SearchCcuNameAddress.setSubmitButtonEnabled(true);
        SearchCcuNameAddress.setQueryHint("Search Here");
    }

    @Override
    protected void onResume() {
        super.onResume();
        _singleton = this;
        search = new ArrayList<>();
        if (!search.isEmpty()) {
            search.clear();
        }
        for (int i = 0; i < users.size(); i++) {
            search.add(users.get(i).getCcuName());
//            sqLlite.insertdata(users.get(i).getUsername(), 0);
        }
        if (Generic_Methods.otpLists != null) {
            if (Generic_Methods.otpLists.isEmpty()) {
                for (int i = 0; i < users.size(); i++) {
                    if (Generic_Methods.otpLists != null) {
                        Generic_Methods.otpLists.add(new Otp_List(users.get(i).getUsername(),0,""));
                    } else {
                        Generic_Methods.otpLists = new ArrayList<>();
                    }
                }
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(CcuListActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, search);
        filter = adapter.getFilter();
//        SearchCcuNameAddress.setAdapter(adapter);

//        SearchCcuNameAddress.setOnItemClickListener(this);
        if (users.size() != 0) {
            CcuListAdapter ccuListAdapter = new CcuListAdapter(this, users);
            CcuList.setAdapter(ccuListAdapter);
            CcuList.setTextFilterEnabled(true);
            filter = ccuListAdapter.getFilter();

        }

        setupSearchView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SearchCcuNameAddress.setQuery("", false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
//                Thread h = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        sqLlite.updateEntire();
//                    }
//                });
//                h.run();
                Generic_Methods.otpLists.clear();
                SharedPreferences prefs = CcuListActivity.this.getSharedPreferences("login", Context.MODE_PRIVATE);
                prefs.edit().clear().apply();
                Intent i1 = new Intent(CcuListActivity.this, MainActivity.class);
                startActivity(i1);
                finish();
                return true;
            case R.id.cPassword:

                return true;
            case R.id.Call_Support:

                return true;
            case R.id.Help:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////        Toast.makeText(CcuListActivity.this,parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
//        for (int i = 0; i < users.size(); i++) {
//            if (users.get(i).getAddress().equals(parent.getItemAtPosition(position).toString()) ||
//                    users.get(i).getCcuName().equals(parent.getItemAtPosition(position).toString())) {
//                Intent i1 = new Intent(CcuListActivity.this, CCU_Details.class);
//                i1.putExtra("CcuData", users.get(i).getCcuName());
//                i1.putExtra("ccu_id", users.get(i).getUsername());
//                startActivity(i1);
//            }
//        }
//
//
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

//        if (TextUtils.isEmpty(newText)) {
//            CcuList.clearTextFilter();
//        } else {
//            CcuList.setFilterText(newText);
//        }
//        return true;
        if (TextUtils.isEmpty(newText)) {
            filter.filter(null);
        } else {
            filter.filter(newText);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Thread h = new Thread() {
            @Override
            public void run() {
                users = null;
                search = null;
//                sqLlite.updateEntire();
                Generic_Methods.createEditSummarySharedPreference(CcuListActivity.getSingletonContext(), "");
                Generic_Methods.unbindDrawables(findViewById(R.id.main_layout));

            }
        };
        h.run();


    }


}
