package com.example.mapping;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;

public class TileListActivity extends ListActivity
{
    String[] data;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        data = new String[] {"Default", "Hikebikemap"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        setListAdapter(adapter);
    }

    public void onListItemClick(ListView lv, View view, int index, long id)
    {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();

        try {
            String tileType;
            switch (index) {
                case 0:
                    tileType = "MAPNIK";
                    break;
                case 1:
                    tileType = "HIKEBIKE";
                    break;
                default:
                    tileType = "UNKNOWN";
                    break;
            }

            bundle.putString("com.example.mapping.tiletype", tileType);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();

        } catch (Exception e){
            System.out.println ("An error occurred: " + e);
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    }

    public class TileTypeAdapter extends ArrayAdapter<String> {
        public TileTypeAdapter() {
            super(TileListActivity.this, android.R.layout.simple_list_item_1, data);
        }

        public View getView(int index, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);

        }

    }
}