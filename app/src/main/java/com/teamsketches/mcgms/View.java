package com.teamsketches.mcgms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class View extends AppCompatActivity {
    final String category = "Category";
    final String issue = "Issue";
    final String complain = "Complain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
    }
}