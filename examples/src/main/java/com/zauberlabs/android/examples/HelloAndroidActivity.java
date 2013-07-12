package com.zauberlabs.android.examples;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.zauberlabs.android.image_paginator.NameResolver;

public class HelloAndroidActivity extends Activity {

    private static String TAG = "examples";

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
        setContentView(R.layout.main);
        Toast.makeText(this, new NameResolver().toString(), Toast.LENGTH_LONG).show();
    }

}

