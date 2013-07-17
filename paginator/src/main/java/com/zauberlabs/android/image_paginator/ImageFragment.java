package com.zauberlabs.android.image_paginator;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;

/**
 * Created by marcelo on 7/10/13.
 */
class ImageFragment extends Fragment {
    public static final int INVALID_PLACEHOLDER = -1;
    private String url;
    private ImageView view;
    private int placeholderResource;

    public ImageFragment(){}
    public static ImageFragment newInstance(String url, int placeholderResource){
        ImageFragment f	= new ImageFragment();
        f.url = url;
        f.placeholderResource = placeholderResource;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ImageView buffer = new ImageView(getActivity());
        buffer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        buffer.setScaleType(ImageView.ScaleType.FIT_XY);
        if (view != null) {
            buffer.setImageDrawable(view.getDrawable());
        } else {
            loadImage(buffer);
        }
        view = buffer;
        return view;
    }

    private void loadImage(final ImageView view) {
        AQuery aquery = new AQuery(getActivity());
        Bitmap placeholder =  aquery.getCachedImage(placeholderResource);
        aquery.id(view).image(url, false, true, 0, 0, placeholder, AQuery.FADE_IN);
    }

    private boolean hasPlaceholder() {
        return placeholderResource != INVALID_PLACEHOLDER;
    }
}