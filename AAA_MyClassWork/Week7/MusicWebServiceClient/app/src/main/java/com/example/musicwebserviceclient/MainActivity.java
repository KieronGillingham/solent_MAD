package com.example.musicwebserviceclient;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnDownload = (Button)findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(this);
        txtResults = (TextView)findViewById(R.id.txt_results);

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
            HttpURLConnection connection = null;
            try {
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String result = "", line;
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    return result;
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

        protected void onPostExecute(String results) {
            txtResults.setText(results);
        }
    }
}
