package com.example.mapping;

import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
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
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MainActivity extends AppCompatActivity {

    MapView mv;
    SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // This line sets the user agent, a requirement to download OSM maps
        Configuration.getInstance().load(this, prefs);

        setContentView(R.layout.activity_main);

        mv = findViewById(R.id.map1);
        Double lat = Double.parseDouble(prefs.getString("lat", "0"));
        Double lon = Double.parseDouble(prefs.getString("lon", "0"));
        Double zoom = Double.parseDouble(prefs.getString("zoom", "1.0"));

        mv.setMultiTouchControls(true);
        mv.getController().setZoom(zoom);
        mv.getController().setCenter(new GeoPoint(lat, lon));

        try {
            String mapType = prefs.getString("map_type", "MAPNIK");

            if ("HIKEBIKE".equals(mapType)) {
                mv.setTileSource(TileSourceFactory.HIKEBIKEMAP);
            } else if ("MAPNIK".equals(mapType)) {
                mv.setTileSource(TileSourceFactory.MAPNIK);
            } else {
                throw new Exception("Tile Source: " + mapType + " not recognised.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {
            case R.id.choose_map:
                intent = new Intent(this, TileListActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.set_loc:
                intent = new Intent(this, SetLocationActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.preferences:
                intent = new Intent(this, MapPreferenceActivity.class);
                startActivityForResult(intent, 2);
                break;
            default:
                return false;
        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        try {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK) {
                        Bundle extras = intent.getExtras();
                        String mapType = extras.getString("com.example.mapping.tiletype");
                        if ("HIKEBIKE".equals(mapType)) {
                            mv.setTileSource(TileSourceFactory.HIKEBIKEMAP);
                        } else if ("MAPNIK".equals(mapType)) {
                            mv.setTileSource(TileSourceFactory.MAPNIK);
                        } else {
                            throw new Exception("Tile Source: " + mapType + " not recognised.");
                        }
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK) {
                        Bundle extras = intent.getExtras();
                        double lat = extras.getDouble("com.example.mapping.latitude");
                        double lon = extras.getDouble("com.example.mapping.longitude");
                        double zoom = extras.getDouble("com.example.mapping.zoom");

                        mv.getController().setZoom(zoom);
                        mv.getController().setCenter(new GeoPoint(lat, lon));
                    }
                    break;
                case 2:
                    // Do nothing
                    break;
                default:
                    return;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

