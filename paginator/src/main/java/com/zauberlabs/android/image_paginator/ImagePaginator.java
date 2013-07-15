package com.zauberlabs.android.image_paginator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zauberlabs.android.groupable.MultilineRadioGroup;

/**
 * Created by marcelo on 7/10/13.
 */
public class ImagePaginator extends FrameLayout implements ViewPager.OnPageChangeListener {
    private static final int MARGIN = 4;

    private ViewPager pager;
    private MultilineRadioGroup container;
    private RadioButton[] buttons;

    private int placeholder = ImageFragment.INVALID_PLACEHOLDER;

    public ImagePaginator(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        setAttributes(attrs);
        init();
    }
    public ImagePaginator(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        setAttributes(attrs);
        init();
    }
    public ImagePaginator(final Context context) {
        super(context);
        init();
    }

    private void setAttributes(final AttributeSet attrs) {
        TypedArray ta = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ImagePaginator,0,0);
        placeholder = ta.getResourceId(R.styleable.ImagePaginator_placeholder, ImageFragment.INVALID_PLACEHOLDER);
    }

    private void init(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.image_paginator, null);
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setOnPageChangeListener(this);
        container = (MultilineRadioGroup)view.findViewById(R.id.container);
        this.addView(view);
    }

    public void setAdapter(final ImagePagerAdapter<?> adapter){
        addBullets(adapter);
        adapter.setPlaceholderResource(placeholder);
        pager.setAdapter(adapter);
    }

    private void addBullets(final PagerAdapter adapter){
        container.clear();
        if(checkNotEmpty(adapter)){
            addBullet(0, true);
            for (int i = 1; i < adapter.getCount(); i++) {
                addBullet(i, false);
            }
        }
    }

    private CheckBox addBullet(int tag, final boolean checked){
        final CheckBox checkBox = container.addItem("", tag, checked);
        //Esto lo tomo de la configuracion
        final int margin = 0;//getResources().getDimensionPixelSize(R.dimen.bullet_margin);
        final int size = 24;//getResources().getDimensionPixelSize(R.dimen.bullet_size);

        final RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(size, size);
        params.setMargins(margin, margin, margin, margin);
        checkBox.setClickable(false);
        checkBox.setLayoutParams(params);
        //Configurable
        //checkBox.setBackgroundResource(R.drawable.page_bullet);
        return checkBox;
    }

    private boolean checkNotEmpty(final PagerAdapter adapter){
        return adapter.getCount() > 0;
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int position) {
        container.setChecked(position, true);
    }

    @Override
    public void onPageScrollStateChanged(int position) {}

    public int getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(int placeholder) {
        this.placeholder = placeholder;
        ImagePagerAdapter<?> adapter = (ImagePagerAdapter<?>) pager.getAdapter();
        if (adapter != null) {
            adapter.setPlaceholderResource(placeholder);
        }
    }
}
