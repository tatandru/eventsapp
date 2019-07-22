package com.example.eventsapp;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView drawerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_menu);


        initUI();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomepageFragment()).commit();
            drawerMenu.setCheckedItem(R.id.homepage);
        }
        setMenuListener();
    }
    @Override
    public void passData(String data) {
        UpcomingEventsFragment fragmentB = new UpcomingEventsFragment ();
        Bundle args = new Bundle();
        args.putString(UpcomingEventsFragment.DATA_RECEIVE, data);
        fragmentB .setArguments(args);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragmentB )
                .commit();
    }
    private void initUI() {
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerMenu = findViewById(R.id.nv_drawer_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    private void setMenuListener() {
        drawerMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.homepage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomepageFragment()).addToBackStack(null).commit();
                        break;
                    case R.id.calendar:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CalendarFragment()).addToBackStack(null).commit();
                        //Toast.makeText(MainActivity.this, "Calendar", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.favorites:
                        //Toast.makeText(MainActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
                         getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavoritesFragment()).addToBackStack(null).commit();
                        break;
                    case R.id.feedback:
                        Toast.makeText(MainActivity.this, "Feedback", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.settings:
                        //Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                         getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).addToBackStack(null).commit();

                        break;
                    default:
                        drawerLayout.closeDrawers();
                        return true;
                }
                drawerLayout.closeDrawers();

                return true;
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            getFragmentManager().popBackStackImmediate();
            super.onBackPressed();
        }
    }


}
