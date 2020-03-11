package com.example.fragments;

import androidx.appcompat.app.AppCompatActivity;
import android.app.FragmentManager;

import android.os.Bundle;

public class FragActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get fragment from manager
        FragmentManager fm = getFragmentManager();
        PersonDetailsFragment personDetailsFrag;
        personDetailsFrag = (PersonDetailsFragment) fm.findFragmentById(R.id.personDetailsFrag);

        // Call methods of the fragment
        personDetailsFrag.setText("Details will appear here!");

    }
}
