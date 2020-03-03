package com.example.musicwebserviceclient;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddNewHit extends AppCompatActivity implements View.OnClickListener {

    EditText etxTitle, etxArtist, etxYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_hit_activity);

        Button btnUpload = (Button)findViewById(R.id.btn_upload);
        btnUpload.setOnClickListener(this);
        Button btnBack = (Button)findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);

        etxTitle = (EditText)findViewById(R.id.etx_song_title);
        etxArtist = (EditText)findViewById(R.id.etx_artist);
        etxYear = (EditText)findViewById(R.id.etx_year);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_upload) {
            if (etxArtist != null) {
                AddSong addSong = new AddSong();
                String songTitle = etxTitle.getText().toString(),
                        artistName = etxArtist.getText().toString(),
                        chartYear = etxYear.getText().toString();
                addSong.execute(songTitle, artistName, chartYear);
            }
        } else if (v.getId() == R.id.btn_back) {
            finish();
        }
    }

    // <Input type into AsyncTask, Type that can track progress of task, Type for returned message from AsyncTask>
    public class AddSong extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {}

        @Override
        protected String doInBackground(String... inputs) {

            String message = "";
            HttpURLConnection connection = null;
            try {
                URL url = new URL("http://www.free-map.org.uk/course/ws/addhit.php");
                connection = (HttpURLConnection) url.openConnection();

                String postData = "song=" + inputs[0] + "&artist=" + inputs[1] + "&year=" + inputs[2];
                connection.setDoOutput(true);
                connection.setFixedLengthStreamingMode(postData.length());
                OutputStream out = connection.getOutputStream();
                out.write(postData.getBytes());

                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String results = "", line;
                    while ((line = bufferedReader.readLine()) != null) {
                        results += line;
                    }
                    if ("".equals(results)) {
                        results = "No results found.";
                    }
                    */
                    message = "Hit received successfully.";
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
        protected void onPostExecute(String message) {
            new AlertDialog.Builder(AddNewHit.this).setPositiveButton("OK",null).setMessage(message).show();
        }
    }
}
