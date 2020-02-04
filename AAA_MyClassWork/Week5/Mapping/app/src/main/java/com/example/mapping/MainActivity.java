package com.example.mapping;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MapView mv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This line sets the user agent, a requirement to download OSM maps
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        setContentView(R.layout.activity_main);

        mv = findViewById(R.id.map1);

        mv.setMultiTouchControls(true);
        mv.getController().setZoom(16L);
        mv.getController().setCenter(new GeoPoint(51.05, -0.72));

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onClick(View view) {

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.choose_map) {
            Intent intent = new Intent(this, TileListActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }
        return false;
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
                default:
                    return;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

