package com.zauberlabs.android.image_paginator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by marcelo on 7/10/13.
 */
public class ImagePaginator extends FrameLayout implements ViewPager.OnPageChangeListener {
    private ViewPager pager;
    private RadioGroup container;
    private RadioButton[] buttons;

    public ImagePaginator(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    public ImagePaginator(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public ImagePaginator(final Context context) {
        super(context);
        init();
    }

    private void init(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.image_paginator, null);
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setOnPageChangeListener(this);
        container = (RadioGroup)view.findViewById(R.id.container);
        this.addView(view);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int position) {
        for(int i=0;i<buttons.length;i++){
            buttons[i].setChecked(i==position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int position) {}
}
