package com.example.reportproblems;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ReportProblems extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button clear = (Button) findViewById(R.id.clear_btn);
        clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText report = (EditText) findViewById(R.id.report_text);
        report.getText().clear();
    }
}
