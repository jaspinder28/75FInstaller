package com.x75f.installer.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.x75f.installer.Adapters.CcuListAdapter;
import com.x75f.installer.DB_Local.SQLliteAdapter;
import com.x75f.installer.R;
import com.x75f.installer.Utils.UsersData;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;




public class CcuListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ArrayList<UsersData> users;
    @InjectView(R.id.SearchCcuNameAddress)
    AutoCompleteTextView SearchCcuNameAddress;
    @InjectView(R.id.CcuList)
    ListView CcuList;
    @InjectView(R.id.tool_bar)
    Toolbar tool_bar;
    ArrayList<String> search;
    private static Context _singleton;
    private SQLliteAdapter sqLlite;

    public static Context getSingletonContext() {
        return _singleton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ccu_search_list);
        ButterKnife.inject(this);
        _singleton = this;
        //database to store otp verification status
        sqLlite = new SQLliteAdapter(CcuListActivity.getSingletonContext());

        tool_bar.setTitle("Welcome " + getIntent().getStringExtra("email"));
        setSupportActionBar(tool_bar);
        search = new ArrayList<>();
        if (!search.isEmpty()) {
            search.clear();
        }
        users = new ArrayList<>();
        users = (ArrayList<UsersData>) getIntent().getSerializableExtra("ccudata");

        for (int i = 0; i < users.size(); i++) {
            search.add(users.get(i).getCcuName());
            search.add(users.get(i).getAddress());
            sqLlite.insertdata(users.get(i).getUsername(), 0);


        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(CcuListActivity.this, android.R.layout.simple_list_item_1, search);
        SearchCcuNameAddress.setAdapter(adapter);

        SearchCcuNameAddress.setOnItemClickListener(this);
        if (users.size() != 0) {
            CcuListAdapter ccuListAdapter = new CcuListAdapter(this, users);
            CcuList.setAdapter(ccuListAdapter);

        }

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(CcuListActivity.this,parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getAddress().equals(parent.getItemAtPosition(position).toString()) ||
                    users.get(i).getCcuName().equals(parent.getItemAtPosition(position).toString())) {
                Intent i1 = new Intent(CcuListActivity.this, CCU_Details.class);
                i1.putExtra("CcuData", users.get(i).getCcuName());
                i1.putExtra("ccu_id", users.get(i).getUsername());
                startActivity(i1);
            }
        }


    }
}
