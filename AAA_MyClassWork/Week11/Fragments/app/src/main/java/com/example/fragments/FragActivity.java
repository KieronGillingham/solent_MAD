package com.example.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class FragActivity extends AppCompatActivity {

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get fragment from manager
        fm = getFragmentManager();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        MapFragment mapFrag;
        mapFrag = (MapFragment) fm.findFragmentById(R.id.mapFrag);

        int id = item.getItemId();

        switch (id) {
            case R.id.london:
                mapFrag.setLocation(51.51, -0.1);
                break;
            case R.id.paris:
                mapFrag.setLocation(48.85, 2.34);
                break;
            case R.id.new_york:
                mapFrag.setLocation(40.75, -74);
                break;
            default:
                new AlertDialog.Builder(this).setMessage("Unknown city.").setPositiveButton("Ok", null).show();
                return false;
        }
        return true;
    }

}
