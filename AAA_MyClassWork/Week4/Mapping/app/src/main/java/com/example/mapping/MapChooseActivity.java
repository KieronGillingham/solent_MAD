package com.example.mapping;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.view.View;

public class MapChooseActivity extends AppCompatActivity implements View.OnClickListener
{

    Button btn_regular;
    Button btn_hike_bike_map;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_choose);

        btn_regular = (Button) findViewById(R.id.btn_regular);
        btn_regular.setOnClickListener(this);

        btn_hike_bike_map = (Button) findViewById(R.id.btn_hike_bike_map);
        btn_hike_bike_map.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            boolean hikebikemap = false;
            if(view.getId() == R.id.btn_hike_bike_map) {
                hikebikemap = true;
            }

            bundle.putBoolean("com.example.hikebikemap", hikebikemap);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();

        } catch (Exception e){
            System.out.println ("An error occurred: " + e);
        }

    }


}