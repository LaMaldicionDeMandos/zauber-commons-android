package com.zauberlabs.android.image_paginator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by marcelo on 7/10/13.
 */
public class ImagePagerAdapter extends FragmentPagerAdapter {
    private final List<String> photos;
    private final BitmapRequestProvider provider = new ImageRequestProvider();
    public ImagePagerAdapter(final FragmentManager fm, final List<String> photos) {
        super(fm);
        this.photos = photos;
    }

    @Override
    public Fragment getItem(final int position) {
        final String photo = photos.get(position);
        return ImageFragment.newInstance(photo, provider);
    }

    @Override
    public int getCount() {
        return photos.size();
    }
}
