package com.example.clock;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView timeDisplay;
    Button setTimeButton;
    EditText setHourField, setMinuteField, setSecondField;
    Clock clock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeDisplay = findViewById(R.id.time_display);
        setTimeButton = (Button) findViewById(R.id.btn_set_time);
        setTimeButton.setOnClickListener(this);

        setHourField = findViewById(R.id.hours_set);
        setMinuteField = findViewById(R.id.minutes_set);
        setSecondField = findViewById(R.id.seconds_set);

        clock = new Clock();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_set_time) {
            try {
                int hours = Integer.parseInt(setHourField.getText().toString());
                int minutes = Integer.parseInt(setMinuteField.getText().toString());
                int seconds = Integer.parseInt(setSecondField.getText().toString());

                if (hours < 0 || hours > 23) {
                    throw new TimeException("Hours invalid.");
                } else if (minutes < 0 || minutes > 59) {
                    throw new TimeException("Minutes invalid.");
                } else if (seconds < 0 || seconds > 59) {
                    throw new TimeException("Seconds invalid.");
                }

                clock.setTime(hours, minutes, seconds);

                String display = "Time set to: " + clock.toString();
                timeDisplay.setText(display);
            } catch (TimeException e) {
                new AlertDialog.Builder(this).
                        setPositiveButton("OK", null).
                        setMessage(e.getExceptionMessage()).show();
            } catch (Exception e) {
                new AlertDialog.Builder(this).
                        setPositiveButton("OK", null).
                        setMessage(e.toString()).show();
            }
        }
    }
}
