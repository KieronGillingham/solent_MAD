package com.example.mapping;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;

public class TileListActivity extends ListActivity
{
    String[] tileTypes;
    String[] tileTypeDesc;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        tileTypes = new String[] {"Default", "Hikebikemap"};
        tileTypeDesc = new String[] {"A standard map showing roads.", "A map that includes walking and biking paths."};
        TileTypeAdapter adapter = new TileTypeAdapter();
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
            // The array passed in here (tileTypes) is overwritten (as is simple_list_item_1) in getView().
            // The length of the array, however, determines how many items are on the overwritten list.
            super(TileListActivity.this, android.R.layout.simple_list_item_1, tileTypes);
        }

        public View getView(int index, View convertView, ViewGroup parent) {
            // convertView is used to recycle view elements that are offscreen for improved performance
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
               view = inflater.inflate(R.layout.tiletypelistentry, parent, false);
            }

            TextView title = (TextView) view.findViewById(R.id.tiletype_name),
                     desc = (TextView) view.findViewById(R.id.tiletype_desc);
            title.setText(tileTypes[index]);
            desc.setText(tileTypeDesc[index]);
            return view;
        }

    }
}