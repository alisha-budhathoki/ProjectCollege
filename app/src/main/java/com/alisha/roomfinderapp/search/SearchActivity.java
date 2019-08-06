package com.alisha.roomfinderapp.search;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alisha.roomfinderapp.R;
import com.alisha.roomfinderapp.utils.FirebaseHelper;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private List<String> mList;
    private FirebaseHelper mFirebaseHelper;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupToolbar();


        fragmentManager = getSupportFragmentManager();
    }


    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter_menu, menu);
//        menu.findItem(R.id.edit_post_action).setVisible(false);
//        menu.findItem(R.id.delete_post_action).setVisible(false);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_price_high:
                fragmentManager.beginTransaction()
                        .replace(R.id.main_frame, new High2LowRoomFragment())
                        .commit();

                return true;
            case R.id.action_price_low:
                fragmentManager.beginTransaction()
                        .replace(R.id.main_frame, new Low2HighRoomFragment())
                        .commit();
                return true;
            case R.id.action_price_location:
                fragmentManager.beginTransaction()
                        .replace(R.id.main_frame, new LocationRoomFragment())
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
