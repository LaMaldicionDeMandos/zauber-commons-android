package com.zauberlabs.android.image_paginator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by marcelo on 7/10/13.
 */
public abstract class ImagePagerAdapter<T> extends FragmentPagerAdapter {
    private final List<T> photos;
    private int placeholderResource;
    public ImagePagerAdapter(final FragmentManager fm, final List<T> photos) {
        super(fm);
        this.photos = photos;
    }

    @Override
    final public Fragment getItem(final int position) {
        final String url = getUrl(photos.get(position));
        return ImageFragment.newInstance(url, placeholderResource);
    }

    abstract protected String getUrl(T item);

    @Override
    final public int getCount() {
        return photos.size();
    }

    final protected void setPlaceholderResource(int placeholderResource) {
        this.placeholderResource = placeholderResource;
    }
}
