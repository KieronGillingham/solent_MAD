package com.example.texteditor;

import android.app.AlertDialog;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    EditText textPage;
    File root = android.os.Environment.getExternalStorageDirectory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textPage = findViewById(R.id.text_page);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            try {
                File location = new File (root.getAbsolutePath() + "/texteditor");
                location.mkdirs();
                File file = new File(location, "textedit.txt");

                FileOutputStream f = new FileOutputStream(file);

                PrintWriter pw = new PrintWriter(f);

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
            // Load
            return true;
        }
        return false;
    }
}
