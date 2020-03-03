package com.example.musicwebserviceclient;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtResults;
    EditText etxArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnDownload = (Button)findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(this);
        txtResults = (TextView)findViewById(R.id.txt_results);
        etxArtist = (EditText)findViewById(R.id.etx_artist);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_download) {
            if (etxArtist != null) {
                GetSongs getSongs = new GetSongs();
                getSongs.execute(etxArtist.getText().toString());
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_hit) {
            try {
                startActivityForResult(new Intent(this, AddNewHit.class), 0);
            } catch (Exception e) {
                new AlertDialog.Builder(this).setMessage("Exception: " + e.toString()).setPositiveButton("Ok", null).show();
            }
        }
        return false;
    }

    // <Input type into AsyncTask, Type that can track progress of task, Type for returned message from AsyncTask>
    public class GetSongs extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {}

        @Override
        protected String doInBackground(String... artistName) {

            String message = "";
            HttpURLConnection connection = null;
            try {
                // artistName[0] should be validated before being passed as a query string.
                URL url = new URL("http://www.free-map.org.uk/course/ws/hits.php?artist=" + artistName[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String results = "", line;
                    while ((line = bufferedReader.readLine()) != null) {
                        results += line;
                    }
                    if ("".equals(results)) {
                        results = "No results found.";
                    }
                    return results;
                } else {
                    message += "HTTP ERROR: " + responseCode;
                }
            } catch(IOException e) {
                message += e.toString();
            } catch(Exception e) {
                message += e.toString();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return message;
        }

        @Override
        protected void onPostExecute(String results) {
            txtResults.setText(results);
        }
    }
}
