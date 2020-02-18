package com.example.texteditor;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    EditText textPage;
    File root = android.os.Environment.getExternalStorageDirectory();

    int permissionRequestCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textPage = findViewById(R.id.text_page);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, permissionRequestCode);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            try {
                PrintWriter pw = new PrintWriter(Environment.getExternalStorageDirectory().getAbsolutePath() + "/output.txt");
                pw.println(textPage.getText().toString());
                pw.flush();
                pw.close(); // close the file to ensure data is flushed to file
                new AlertDialog.Builder(this).setMessage("Save successful.").setPositiveButton("Ok", null).show();
                return true;
            } catch (IOException e) {
                new AlertDialog.Builder(this).setMessage("IOException: " + e.toString()).setPositiveButton("Ok", null).show();
            } catch (Exception e) {
                new AlertDialog.Builder(this).setMessage("Exception: " + e.toString()).setPositiveButton("Ok", null).show();
            }

        } else if (item.getItemId() == R.id.load) {
            try {
                textPage.setText("");

                BufferedReader reader = new BufferedReader( new FileReader(Environment.getExternalStorageDirectory().getAbsolutePath() + "/output.txt"));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    textPage.append(line);
                }
                new AlertDialog.Builder(this).setMessage("Save successful.").setPositiveButton("Ok", null).show();
                return true;
            } catch (IOException e) {
                new AlertDialog.Builder(this).setMessage("IOException: " + e.toString()).setPositiveButton("Ok", null).show();
            } catch (Exception e) {
                new AlertDialog.Builder(this).setMessage("Exception: " + e.toString()).setPositiveButton("Ok", null).show();
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            new AlertDialog.Builder(this).setMessage("Permissions were not successfully set.").setPositiveButton("Ok", null).show();
                            return;
                        }
                    }
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
