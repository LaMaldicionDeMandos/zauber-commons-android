package com.zauberlabs.android.image_paginator;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by marcelo on 7/10/13.
 */
public class ImageFragment extends Fragment implements BitmapRequestListener {
    private String url;
    private BitmapRequestProvider provider;
    private ImageView view;
    private Bitmap bitmap;

    public ImageFragment(){}
    public static ImageFragment newInstance(String url, BitmapRequestProvider provider){
        ImageFragment f	= new ImageFragment();
        f.url = url;
        f.provider = provider;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ImageView buffer = new ImageView(getActivity());
        buffer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        buffer.setScaleType(ImageView.ScaleType.FIT_XY);
        if(view!=null){
            buffer.setImageDrawable(view.getDrawable());
        }
        else{
            //TODO como pongo el placeholder?
            //buffer.setImageResource(R.drawable.placeholder_photo);
            provider.provide(url, this);
        }
        view = buffer;
        return view;
    }

    @Override
    public void onDetach(){
        if(bitmap!=null){
            bitmap.recycle();
        }
        super.onDetach();
    }

    @Override
    public void onBitmap(final Bitmap bitmap) {
        if(!isDetached() && getActivity()!=null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    view.setImageBitmap(bitmap);
                }
            });
        }
    }
}