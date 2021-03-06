package at.sw2017.awesomeinc.awesomeplayer;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    Fragment init_fragment = null;
    Fragment song_fragment = null;
    String search_query = "";
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //Database.resetVisibleSongs();
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search for awesome songs ...");
        searchView.setOnQueryTextListener(this);
        renewSearch();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        search_query = query;

        if(init_fragment == null)
        {
            displaySelectedScreen(R.id.nav_songs);
        }
        else if (init_fragment.getClass() == Songs.class)
        {
            RecyclerView view = ((Songs)init_fragment).getRecyclerView();
            MusicListAdapter adapter = (MusicListAdapter) view.getAdapter();
            adapter.filterSongsByAttributes(query, search_selection);
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return onQueryTextChange(query);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.select_search_title) {
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

    public void renewSearch() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final MenuItem item_title = toolbar.getMenu().findItem(R.id.select_search_title);
        if (search_selection.contains("A")) {
            item_title.setChecked(true);
        } else {
            item_title.setChecked(false);
        }

        final MenuItem item_album = toolbar.getMenu().findItem(R.id.select_search_album);
        if (search_selection.contains("B")) {
            item_album.setChecked(true);
        } else {
            item_album.setChecked(false);
        }

        final MenuItem item_artist = toolbar.getMenu().findItem(R.id.select_search_artist);
        if (search_selection.contains("C")) {
            item_artist.setChecked(true);
        } else {
            item_artist.setChecked(false);
        }

        final MenuItem search_item = toolbar.getMenu().findItem(R.id.action_search);
        final SearchView search_view = (SearchView) MenuItemCompat.getActionView(search_item);

        if (search_query.equals("")) {

            search_view.setInputType(InputType.TYPE_CLASS_TEXT);
            ImageView close_button = (ImageView) search_view.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
            close_button.setEnabled(true);

            if (!search_view.isIconified()) {
                search_view.clearFocus();
                search_view.setQuery("", true);
                search_view.setIconified(true);
            }

        } else {
            if (search_view.isIconified()) {
                search_view.setIconified(false);
            }

            search_view.setQuery(search_query, false);
            search_view.setInputType(InputType.TYPE_NULL);
            ImageView close_button = (ImageView) search_view.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
            close_button.setEnabled(false);

            search_view.clearFocus();

            if (init_fragment.getClass() == Songs.class) {
                RecyclerView view = ((Songs) init_fragment).getRecyclerView();
                MusicListAdapter adapter = (MusicListAdapter) view.getAdapter();
                adapter.filterSongsByAttributes(search_query, search_selection);
            }
        }
    }

    public void setSongSearch(String query, String selection) {
        search_selection = selection;
        search_query = query;
    }

    public void displaySelectedScreen(int id) {
        Boolean flag = true;

        switch (id) {
            case R.id.nav_songs:
                init_fragment = new Songs();
                setSongSearch("", "A");
                invalidateOptionsMenu();
                break;
            case R.id.nav_playlists:
                init_fragment = new Playlists();
                break;
            case R.id.nav_album:
                init_fragment = new Album();
                break;
        }

        if(init_fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, init_fragment);
            if (id == R.id.nav_songs) {
                ft.addToBackStack(null);
            }
            ft.commit();
            getSupportFragmentManager().executePendingTransactions();
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
