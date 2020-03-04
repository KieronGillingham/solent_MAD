package com.example.localpointsofinterest;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.widget.Toast;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationListener {

    MapView mv;
    ItemizedIconOverlay<OverlayItem> items;
    ItemizedIconOverlay.OnItemGestureListener<OverlayItem> markerGestureListener;

    // Local file to read data from
    File localPOIFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/restaurants.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Required to display osmdroid maps
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        // Required to use location tracking
        LocationManager locMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        setContentView(R.layout.activity_main);

        // Set default position for map
        mv = (MapView) findViewById(R.id.mainMap);
        mv.getController().setZoom(14);
        mv.getController().setCenter(new GeoPoint(50.901, -1.404));

        markerGestureListener = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                Toast.makeText(MainActivity.this, item.getTitle() + ", " + item.getSnippet(), Toast.LENGTH_SHORT).show();
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
        southamptonCC.setMarker(this.getDrawable(R.drawable.marker));
        items.addItem(southamptonCC);

        // Get additional items from local file

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(localPOIFile.getAbsolutePath()));

            String poiString;
            while ((poiString = reader.readLine()) != null) {
                String[] poiComp = poiString.split(",");
                if (poiComp.length == 5) {
                    String title = poiComp[0],
                            type = poiComp[1],
                            town = poiComp[2];
                    Double lon = Double.parseDouble(poiComp[3]),
                            lat = Double.parseDouble(poiComp[4]);
                    OverlayItem poi = new OverlayItem(title + ", " + town, type, new GeoPoint(lat, lon));

                    switch (type) {
                        case "Chinese":
                            poi.setMarker(this.getDrawable(R.drawable.teashop));
                            break;
                        case "Pizza":
                            poi.setMarker(this.getDrawable(R.drawable.restaurant));
                            break;
                        case "pub":
                            poi.setMarker(this.getDrawable(R.drawable.pub));
                            break;
                        case "General":
                            poi.setMarker(this.getDrawable(R.drawable.cafe));
                            break;
                        default:
                            poi.setMarker(this.getDrawable(R.drawable.marker));
                            break;
                    }

                    items.addItem(poi);
                } else {
                    throw new IOException("File format incorrect.");
                }
            }

            new AlertDialog.Builder(this).setMessage(items.size() + " items(s) read from file.").setPositiveButton("Ok", null).show();

        } catch (FileNotFoundException e) {
            new AlertDialog.Builder(this).setMessage("FileNotFoundException: " + e.toString()).setPositiveButton("Ok", null).show();

        } catch (IOException e) {
            new AlertDialog.Builder(this).setMessage("IOException: " + e.toString()).setPositiveButton("Ok", null).show();

        } catch (Exception e) {
            new AlertDialog.Builder(this).setMessage("Exception: " + e.toString()).setPositiveButton("Ok", null).show();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                new AlertDialog.Builder(this).setMessage("IOException: " + e.toString()).setPositiveButton("Ok", null).show();
            } catch (Exception e) {
                new AlertDialog.Builder(this).setMessage("Exception: " + e.toString()).setPositiveButton("Ok", null).show();
            }
        }

        mv.getOverlays().add(items);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
