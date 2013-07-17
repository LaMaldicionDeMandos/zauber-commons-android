package com.zauberlabs.android.image_paginator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
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
    private static final int DEFAULT_BULLET_MARGIN = 4;
    private static final int DEFAULT_BULLET_SIZE = 60;
    private static final int DEFAULT_BACKGROUND = R.layout.checkbox;
    private static final int DEFAULT_BULLET = -1;
    private static final int DEFAULT_BULLET_COLOR_ON = 0xffffffff;
    private static final int DEFAULT_BULLET_Color_OFF = 0x75ffffff;

    private ViewPager pager;
    private MultilineRadioGroup container;
    private RadioButton[] buttons;

    private int bulletMargin = DEFAULT_BULLET_MARGIN;
    private int bulletSize = DEFAULT_BULLET_SIZE;
    private int bullet = DEFAULT_BULLET;
    private int bulletColorOn = DEFAULT_BULLET_COLOR_ON;
    private int bulletColorOff = DEFAULT_BULLET_Color_OFF;

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
        bulletMargin = ta.getDimensionPixelSize(R.styleable.ImagePaginator_bullet_margin, DEFAULT_BULLET_MARGIN);
        bulletSize = ta.getDimensionPixelSize(R.styleable.ImagePaginator_bullet_size, DEFAULT_BULLET_SIZE);
        bullet = ta.getResourceId(R.styleable.ImagePaginator_bullet, DEFAULT_BULLET);
        bulletColorOn = ta.getColor(R.styleable.ImagePaginator_bullet_color_on, DEFAULT_BULLET_COLOR_ON);
        bulletColorOff = ta.getColor(R.styleable.ImagePaginator_bullet_color_off, DEFAULT_BULLET_Color_OFF);
    }

    private void init(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.image_paginator, null);
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setOnPageChangeListener(this);
        container = (MultilineRadioGroup)view.findViewById(R.id.container);
        container.setRadioResource(DEFAULT_BACKGROUND);
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
        final int margin = bulletMargin;
        final int size = bulletSize;

        final RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(size, size);
        params.setMargins(margin, margin, margin, margin);
        checkBox.setClickable(false);
        checkBox.setLayoutParams(params);
        if (bullet != DEFAULT_BULLET) {
            checkBox.setBackgroundResource(bullet);
        }
        else {
            checkBox.setBackgroundDrawable(createBulletByDefault());
        }
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

    private Drawable createBulletByDefault() {
        StateListDrawable bullet = new StateListDrawable();
        ShapeDrawable on = createBulletShape(bulletSize, bulletColorOn);
        ShapeDrawable off = createBulletShape(bulletSize, bulletColorOff);
        bullet.addState(new int[]{android.R.attr.state_checked}, on);
        bullet.addState(new int[0], off);
        bullet.addState(new int[]{android.R.attr.state_checked, android.R.attr.state_pressed}, on);
        bullet.addState(new int[]{android.R.attr.state_pressed}, off);
        return bullet;
    }

    private ShapeDrawable createBulletShape(int size, int color) {
        ShapeDrawable bullet = new StrokedShapeDrawable(new RoundRectShape(new float[]{5,5,5,5,5,5,5,5}, null, null));
        bullet.getPaint().setColor(color);
        bullet.setIntrinsicHeight(size);
        bullet.setIntrinsicWidth(size);
        return bullet;
    }

    class StrokedShapeDrawable extends ShapeDrawable {
        private Paint strokepaint;

        public StrokedShapeDrawable(Shape shape) {
            super(shape);
            strokepaint = new Paint(getPaint());
            strokepaint.setStyle(Paint.Style.STROKE);
            strokepaint.setStrokeWidth(1);
            strokepaint.setColor(0xff000000);
        }

        @Override
        protected void onDraw(Shape shape, Canvas canvas, Paint paint) {
            shape.draw(canvas, paint);
            shape.draw(canvas, strokepaint);
        }
    }
}
