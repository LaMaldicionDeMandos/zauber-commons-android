package com.zauberlabs.android.image_paginator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.net.MalformedURLException;
import java.util.List;
import java.net.URL;

import static com.google.common.base.Preconditions.*;

/**
 * Created by marcelo on 7/10/13.
 */
public abstract class ImagePagerAdapter<T> extends FragmentPagerAdapter {
    private final List<T> photos;
    private int placeholderResource;
    public ImagePagerAdapter(final FragmentManager fm, final List<T> photos) {
        super(fm);
        checkArgument(photos != null, "The photo list can't be null");
        this.photos = photos;
    }




    @Override
    final public Fragment getItem(final int position) {
        final URL url;
        try {
            url = getUrl(photos.get(position));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(String.format("The url of item in position $s is malformed", position));
        }
        return ImageFragment.newInstance(url, placeholderResource);
    }

    abstract protected URL getUrl(T item) throws MalformedURLException;

    @Override
    final public int getCount() {
        return photos.size();
    }

    final protected void setPlaceholderResource(int placeholderResource) {
        this.placeholderResource = placeholderResource;
    }
}
