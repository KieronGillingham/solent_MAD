package com.example.mapping;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.ITileSource;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MainActivity extends AppCompatActivity {

    MapView mv;
    SharedPreferences prefs;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This line sets the user agent, a requirement to download OSM maps
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        setContentView(R.layout.activity_main);

        mv = findViewById(R.id.map1);

        //mv.setMultiTouchControls(true);
        //mv.getController().setZoom(16L);
        //mv.getController().setCenter(new GeoPoint(51.05, -0.72));

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.preferences) {
            Intent intent = new Intent(this, MapPreferenceActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

    }

    @Override
    public void onResume() {
        super.onResume();

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        try {
            ITileSource tileSource;
            String mapType = prefs.getString("map_type", "MAPNIK");
            if ("HIKEBIKEMAP".equals(mapType)) {
                mv.setTileSource(TileSourceFactory.HIKEBIKEMAP);
            } else if ("MAPNIK".equals(mapType)) {
                mv.setTileSource(TileSourceFactory.MAPNIK);
            } else {
                throw new Exception("Tile Source: " + mapType + " not recognised.");
            }

            Double lat = Double.parseDouble(prefs.getString("lat", "50.9"));
            Double lon = Double.parseDouble(prefs.getString("lon", "-1.3"));
            Double zoom = Double.parseDouble(prefs.getString("zoom", "16L"));

            mv.getController().setZoom(zoom);
            mv.getController().setCenter(new GeoPoint(lat, lon));

        } catch (
                Exception e) {
            System.out.println(e);
        }





    }

}

