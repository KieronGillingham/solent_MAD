package com.example.musicwebserviceclient;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

public class GetSongs extends AsyncTask<Void, Void, String> {

    Context parent;

    public GetSongs(Context p) {
        parent = p;
    }


    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(Void... unused) {
        String message;
        try
        {
            message = "Connection feature not implemented!";
        }
        catch(Exception e)
        {
            message = e.toString();
        }
        return message;
    }

    protected void onPostExecute(String message) {
        new AlertDialog.Builder(parent).setMessage(message).setPositiveButton("OK",null).show();
    }
}
