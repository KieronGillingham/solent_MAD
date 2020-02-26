package com.example.musicwebserviceclient;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnDownload = (Button)findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_download) {
            GetSongs getSongs = new GetSongs();
            getSongs.execute("http://www.free-map.org.uk/index.php");
        }
    }

    // <Input type into AsyncTask, Type that can track progress of task, Type for returned message from AsyncTask>
    public class GetSongs extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {}

        @Override
        protected String doInBackground(String... urls) {
            String message = "";
            try {
                message += "Attempting to connect to " + urls[0] + ". ";
                message += "Error: Connection feature not implemented!";
            } catch(Exception e) {
                message += e.toString();
            }
            return message;
        }

        protected void onPostExecute(String message) {
            new AlertDialog.Builder(MainActivity.this).setMessage(message).setPositiveButton("OK",null).show();
        }
    }
}
