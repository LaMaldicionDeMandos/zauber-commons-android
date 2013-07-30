package com.zauberlabs.android.examples;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.zauberlabs.android.image_paginator.ImagePagerAdapter;
import com.zauberlabs.android.image_paginator.ImagePaginator;

public class PaginatorActivity extends FragmentActivity {

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
        setContentView(R.layout.paginator);
        List<String> urls = Arrays.asList(
                "https://github.com/Proyecto-UTN-2012/Proyecto-UTN-2012/blob/master/integrar-t-android/res/drawable/cali1.png?raw=true",
                "https://github.com/Proyecto-UTN-2012/Proyecto-UTN-2012/blob/master/integrar-t-android/res/drawable/cali2.png?raw=true",
                "https://github.com/Proyecto-UTN-2012/Proyecto-UTN-2012/blob/master/integrar-t-android/res/drawable/cali3.png?raw=true",
                "https://github.com/Proyecto-UTN-2012/Proyecto-UTN-2012/blob/master/integrar-t-android/res/drawable/cali4.png?raw=true",
                "https://github.com/Proyecto-UTN-2012/Proyecto-UTN-2012/blob/master/integrar-t-android/res/drawable/cali5.png?raw=true",
                "https://github.com/Proyecto-UTN-2012/Proyecto-UTN-2012/blob/master/integrar-t-android/res/drawable/cali6.png?raw=true"
                );
        ImagePaginator paginator = (ImagePaginator) findViewById(R.id.paginator);
        //paginator.setPlaceholder(R.drawable.icon);
        ImagePagerAdapter<String> adapter = new ImagePagerAdapter<String>( getSupportFragmentManager(), urls){
            @Override
            protected URL getUrl(String item) throws MalformedURLException {
                return new URL(item);
            }
        };
        paginator.setAdapter(adapter);
    }

}

