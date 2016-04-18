package com.example.android.popularmovies;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Objects;


public class MainActivity extends AppCompatActivity implements MovieFragment.MovieInterface {


    public Toolbar toolbar;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);


        mTwoPane = findViewById(R.id.detail_container) != null;


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DecideLayout();


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        DecideLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        DecideLayout();
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public Boolean getPref(String string) {


        String online = getString(R.string.pref_access_online);
        String favorites = getString(R.string.pref_access_favorites);
        boolean isItTrue = true;

        if (Objects.equals(string, online)) {
            isItTrue = true;
        }
        if (Objects.equals(string, favorites)) {
            isItTrue = false;
        }
        return isItTrue;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        DecideLayout();
    }

    public void DecideLayout() {

        SharedPreferences accessMethod = PreferenceManager.getDefaultSharedPreferences(this);
        String access = accessMethod.getString(getString(R.string.pref_access_key),
                getString(R.string.pref_access_online));

        Toast.makeText(this, access, Toast.LENGTH_LONG).show();


        if (getPref(access)) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new MovieFragment()).commitAllowingStateLoss();
            toolbar.setTitle("The Movies Database");
        } else {

            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new MovieFragmentFavorite()).commitAllowingStateLoss();
            toolbar.setTitle("Favorites");
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //  Return true to display menu
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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMovieDetail(Intent intent) {

        if (mTwoPane) {

            //  If we're in two pane mode start the new instance of MovieDetailFragment with the
            // passed in Intent
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_container,
                    MovieDetailFragment.newInstance(intent), DETAILFRAGMENT_TAG).commit();


        } else {

            startActivity(intent);
        }

    }

}
