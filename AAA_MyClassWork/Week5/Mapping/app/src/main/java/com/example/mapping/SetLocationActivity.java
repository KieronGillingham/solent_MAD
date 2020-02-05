package com.example.mapping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;

public class SetLocationActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText etx_lat;
    EditText etx_lon;
    EditText etx_zoom;

    Button btn_go;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_set);

        etx_lat = (EditText) findViewById(R.id.etx_lat);
        etx_lon = (EditText) findViewById(R.id.etx_lon);
        etx_zoom = (EditText) findViewById(R.id.etx_zoom);

        btn_go = (Button) findViewById(R.id.btn_go);
        btn_go.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();

            double lat = Double.parseDouble(etx_lat.getText().toString());
            double lon = Double.parseDouble(etx_lon.getText().toString());
            double zoom = Double.parseDouble(etx_zoom.getText().toString());

            bundle.putDouble("com.example.mapping.latitude", lat);
            bundle.putDouble("com.example.mapping.longitude", lon);
            bundle.putDouble("com.example.mapping.zoom", zoom);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();

        } catch (Exception e){
            System.out.println ("An error occurred: " + e);
        }

    }

}