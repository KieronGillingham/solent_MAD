package com.example.localpointsofinterest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MapView mv;
    ItemizedIconOverlay<OverlayItem> items;
    ItemizedIconOverlay.OnItemGestureListener<OverlayItem> markerGestureListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Required to display osmdroid maps
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        setContentView(R.layout.activity_main);

        // Set default position for map
        mv = (MapView) findViewById(R.id.mainMap);
        mv.getController().setZoom(14);
        mv.getController().setCenter(new GeoPoint(50.901, -1.404));

        markerGestureListener = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                Toast.makeText(MainActivity.this, item.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return true;
            }
        };

        items = new ItemizedIconOverlay<>(this, new ArrayList<OverlayItem>(), markerGestureListener);
        OverlayItem southamptonCC = new OverlayItem("Southampton City Centre", "City Centre in Southampton", new GeoPoint(50.9014, -1.4041));
        // Set display marker for item
        southamptonCC.setMarker(this.getDrawable(R.drawable.marker_default));
        items.addItem(southamptonCC);
        mv.getOverlays().add(items);
    }
}
