package com.liguang.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.liguang.app.R;
import com.liguang.app.adapter.DemoVideoCategoryPagerAdapter;
import com.liguang.app.adapter.YoutubeVideoPageAdapter;
import com.liguang.app.fragment.YoutubeVideoFragment;
import com.liguang.app.po.ColorItem;
import com.liguang.app.po.YoutubeVideoCategoryItem;
import com.liguang.app.utils.DemoData;
import com.liguang.library.RecyclerTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,YoutubeVideoFragment.OnFragmentInteractionListener {
    @InjectView(R.id.recycler_tab_layout)
    RecyclerTabLayout recyclerTabLayout;
    @InjectView(R.id.view_pager)
    ViewPager viewPager;
    @InjectView(R.id.nav_view)
    NavigationView navView;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        List<ColorItem> colorItems = DemoData.loadDemoColorItems(this);
        List<YoutubeVideoCategoryItem> youtubeVideoCategoryItems = DemoData.loadDemoYoutubeVideoCategoryItems(this);
//        Collections.sort(items, new Comparator<ColorItem>() {
//            @Override
//            public int compare(ColorItem lhs, ColorItem rhs) {
//                return lhs.name.compareTo(rhs.name);
//            }
//        });

        DemoVideoCategoryPagerAdapter adapter = new DemoVideoCategoryPagerAdapter();
        adapter.addAll(youtubeVideoCategoryItems);


        List<Fragment> mListFragment = new ArrayList<>();
        for (int i = 0; i < youtubeVideoCategoryItems.size(); i++) {
            mListFragment.add(new YoutubeVideoFragment().newInstance(youtubeVideoCategoryItems.get(i).id,youtubeVideoCategoryItems.get(i).snippet.title));
        }
        YoutubeVideoPageAdapter youtubeVideoPageAdapter = new YoutubeVideoPageAdapter(getSupportFragmentManager(), mListFragment);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(1);
        //viewPager.setAdapter(adapter);
        viewPager.setAdapter(youtubeVideoPageAdapter);
        recyclerTabLayout.setUpWithViewPager(viewPager);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "toast被点击", Toast.LENGTH_SHORT);
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)
                    ) {
                drawerLayout.closeDrawers();
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            Intent intent = new Intent(this, ItemListActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, ScrollingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, FullscreenActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(this, ScrollingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(this, ScrollingActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
