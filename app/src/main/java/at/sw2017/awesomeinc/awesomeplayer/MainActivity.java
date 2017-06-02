package at.sw2017.awesomeinc.awesomeplayer;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    Fragment init_fragment = null;
    Fragment song_fragment = null;
    String search_query = null;
    String search_selection = "A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Database.init(this.getApplicationContext());
        song_fragment = new Songs();
    }

    @Override
    public void onBackPressed() {
        if (init_fragment != null && init_fragment.isVisible()) {
            Intent start = new Intent(MainActivity.this, MainActivity.class);
            startActivity(start);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search for awesome artists...");
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {

        /*search_query = query;

        init_fragment = new Songs();
        Bundle bundl = new Bundle();
        bundl.putString("search_item", search_query);
        init_fragment.setArguments(bundl);

        if(init_fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, init_fragment);
            ft.commit();
        }*/

        // TODO: check if playlist view is selected and react accordingly

        if(init_fragment == null)
        {
            displaySelectedScreen(R.id.nav_songs);
        }
        else if(init_fragment != null)
        {
            RecyclerView view = ((Songs)init_fragment).getRecyclerView();
            MusicListAdapter test = (MusicListAdapter) view.getAdapter();
            test.filterSongsByAttributes(query, search_selection);
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.select_search_title) {
            if (item.isChecked()) {
                item.setChecked(false);
                search_selection = search_selection.replace("A", "");
            } else {
                item.setChecked(true);
                search_selection = search_selection + "A";
            }
        } else if (id == R.id.select_search_album) {
            if (item.isChecked()) {
                item.setChecked(false);
                search_selection = search_selection.replace("B", "");
            } else {
                item.setChecked(true);
                search_selection = search_selection + "B";
            }
        } else if (id == R.id.select_search_artist) {
            if (item.isChecked()) {
                item.setChecked(false);
                search_selection = search_selection.replace("C", "");
            } else {
                item.setChecked(true);
                search_selection = search_selection + "C";
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public String getSearchSelection() {
        return search_selection;
    }

    private void displaySelectedScreen(int id) {
        search_query = null;
        Boolean flag = true;
        switch (id) {
            case R.id.nav_songs:
                if(init_fragment == song_fragment)
                {
                    flag = false;
                    break;
                }
                init_fragment = song_fragment;
                Bundle bundl = new Bundle();
                bundl.putString("search_item", search_query);
                init_fragment.setArguments(bundl);
                break;
            case R.id.nav_playlists:
                break;
        }

        if(init_fragment != null && flag == true) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, init_fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        displaySelectedScreen(id);
        return true;
    }
}
