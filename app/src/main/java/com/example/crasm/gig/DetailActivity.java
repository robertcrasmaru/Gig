package com.example.crasm.gig;

import android.os.Bundle;
import android.app.Activity;

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
