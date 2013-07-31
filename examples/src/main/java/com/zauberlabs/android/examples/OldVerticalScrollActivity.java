package com.zauberlabs.android.examples;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.zauberlabs.android.image_paginator.ImagePagerAdapter;
import com.zauberlabs.android.image_paginator.ImagePaginator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by marcelo on 7/30/13.
 */
public class OldVerticalScrollActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_vertical_scroll_activity);
        List<String> urls = Arrays.asList(
                "https://github.com/Proyecto-UTN-2012/Proyecto-UTN-2012/blob/master/integrar-t-android/res/drawable/cali1.png?raw=true",
                "https://github.com/Proyecto-UTN-2012/Proyecto-UTN-2012/blob/master/integrar-t-android/res/drawable/cali2.png?raw=true",
                "https://github.com/Proyecto-UTN-2012/Proyecto-UTN-2012/blob/master/integrar-t-android/res/drawable/cali3.png?raw=true",
                "https://github.com/Proyecto-UTN-2012/Proyecto-UTN-2012/blob/master/integrar-t-android/res/drawable/cali4.png?raw=true",
                "https://github.com/Proyecto-UTN-2012/Proyecto-UTN-2012/blob/master/integrar-t-android/res/drawable/cali5.png?raw=true",
                "https://github.com/Proyecto-UTN-2012/Proyecto-UTN-2012/blob/master/integrar-t-android/res/drawable/cali6.png?raw=true"
        );
        ImagePaginator paginator = (ImagePaginator) findViewById(R.id.paginator1);
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
