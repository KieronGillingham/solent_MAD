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

    EditText etx_lat;
    EditText etx_lon;
    EditText etx_zoom;

    Button btn_go;
    MapView mv;


    /** Called when the activity is first created. */
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

        etx_lat = (EditText) findViewById(R.id.etx_lat);
        etx_lon = (EditText) findViewById(R.id.etx_lon);
        etx_zoom = (EditText) findViewById(R.id.etx_zoom);

        btn_go = (Button) findViewById(R.id.btn_go);

        btn_go.setOnClickListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        try {
            double lat = Double.parseDouble(etx_lat.getText().toString());
            double lon = Double.parseDouble(etx_lon.getText().toString());
            double zoom = Double.parseDouble(etx_zoom.getText().toString());

            mv.getController().setZoom(zoom);
            mv.getController().setCenter(new GeoPoint(lat, lon));

        } catch (Exception e){
            System.out.println ("An error occurred: " + e);
        }

    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.choose_map)
        {
            Intent intent = new Intent(this, MapChooseActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}

