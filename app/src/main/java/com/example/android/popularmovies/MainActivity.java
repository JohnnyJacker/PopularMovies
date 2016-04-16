package com.example.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements MovieFragment.MovieInterface {


    public static final String LOG_TAG = MainActivity.class.getSimpleName();



    public Toolbar toolbar;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        if (findViewById(R.id.detail_container) != null) {
            mTwoPane = true;

//            if (savedInstanceState == null) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.detail_container,
//                        new MovieDetailFragment(), DETAILFRAGMENT_TAG).commit();
//            }
        } else {
            mTwoPane = false;
        }


        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
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


    public Boolean getPref(String string) {


        String online = getString(R.string.pref_access_online);
        String favorites = getString(R.string.pref_access_favorites);
        boolean isItTrue = true;

        if (string == online) {
            isItTrue = true;
        }
        if (string == favorites) {
            isItTrue = false;
        }
        return isItTrue;
    }


    public void DecideLayout() {

        SharedPreferences accessMethod = PreferenceManager.getDefaultSharedPreferences(this);
        String access = accessMethod.getString(getString(R.string.pref_access_key),
                getString(R.string.pref_access_online));

        Toast.makeText(this, access, Toast.LENGTH_LONG).show();



        if (getPref(access) == true) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new MovieFragment()).commit();
            //toolbar.setTitle("The Movies Database");
        } else {


            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new MovieFragmentFavorite()).commit();
            //toolbar.setTitle("Favorites");
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        if (mTwoPane == true) {

            getSupportFragmentManager().beginTransaction().replace(R.id.detail_container,
                    MovieDetailFragment.newInstance(intent), DETAILFRAGMENT_TAG).commit();

//            Toast.makeText(this, "Hey this is two pane mode!", Toast.LENGTH_LONG).show();

        } else {

            startActivity(intent);
        }

    }



}
