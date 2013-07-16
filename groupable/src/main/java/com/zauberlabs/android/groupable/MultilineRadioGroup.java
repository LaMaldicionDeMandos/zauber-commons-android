package com.zauberlabs.android.groupable;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class MultilineRadioGroup extends LinearLayout implements
        Groupable<CheckBox> {
    private final static int DEFAUTL_BULLET_MARGIN = 6; //4dp
    private final static int DEFAUTL_BULLET_SIZE = 12;  //8dip

    private final List<CheckBoxGroup> checkBoxGroupList = newArrayList();
    private int bulletsPerGroup;


    public MultilineRadioGroup(final Context context) {
        super(context);
        initialize(context);
    }

    public MultilineRadioGroup(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MultilineRadioGroup(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(final Context context) {
        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(MATCH_PARENT,WRAP_CONTENT));
        addNewcheckBoxGroup(context);
        bulletsPerGroup = getBulletsPerGroup(context);
    }

    int getBulletsPerGroup() {
        return bulletsPerGroup;
    }

    private int getBulletsPerGroup(final Context context) {
        final int bulletSize = DEFAUTL_BULLET_SIZE;
        final int bulletMargin = DEFAUTL_BULLET_MARGIN;
        final int screenWidth = getScreenWidth(context);
        return getBulletsPerGroup(screenWidth, bulletSize, bulletMargin);
    }

    int getBulletsPerGroup(final int screenWidth, final int bulletSize, final int bulletMargin) {
        return screenWidth / (bulletMargin * 2 + bulletSize);
    }

    @Override
    public CheckBox addItem(final CharSequence text, final Object tag) {
        return addItem(text, tag, false);
    }

    @Override
    public CheckBox addItem(final CharSequence text, final Object tag, final boolean checked) {
        checkNotNull(text, "Must supply a text");
        checkNotNull(tag, "Must supply a tag");
        final int position = size() / getBulletsPerGroup();
        if (checkBoxGroupList.size() <= position) {
            addNewcheckBoxGroup(getContext());
        }
        final CheckBox item = checkBoxGroupList.get(position).addItem(text, tag);
        setChecked(tag, checked);
        return item;
    }

    @Override
    public CheckBox findWithTag(final Object tag) {
        checkNotNull(tag, "Must supply a tag");
        return (CheckBox) findViewWithTag(tag);
    }

    @Override
    public int size() {
        int size = 0;
        for (final CheckBoxGroup checkBoxGroup : checkBoxGroupList) {
            size += checkBoxGroup.size();
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void clear() {
        for (final CheckBoxGroup checkBoxGroup : checkBoxGroupList) {
            checkBoxGroup.clear();
        }
    }

    @Override
    public List<CheckBox> getAllChecked() {
        final List<CheckBox> checkedOnes = newArrayList();
        for (final CheckBoxGroup checkBoxGroup : checkBoxGroupList) {
            checkedOnes.addAll(checkBoxGroup.getAllChecked());
        }
        return checkedOnes;
    }

    @Override
    public <F> void populate(final List<F> items, final Groupable.Predicate<F> predicate) {
        clear();
        for (final F item : items) {
            addItem(predicate.applyToText(item), predicate.applyToTag(item));
        }
        if (!items.isEmpty()) {
            setChecked(predicate.applyToTag(items.get(0)), true);
        }
    }

    public int getCheckedRadioButtonId() {
        final List<CheckBox> allChecked = getAllChecked();
        return allChecked.isEmpty() ? View.NO_ID : allChecked.get(0).getId();
    }

    List<CheckBoxGroup> getcheckBoxGroupList() {
        return checkBoxGroupList;
    }

    @Override
    public void setChecked(final Object tag, final boolean checked) {
        checkNotNull(tag);
        final CheckBox item = findWithTag(tag);
        checkArgument(item != null, "No item found with tag: " + tag);
        final int checkedRadioButtonId = getCheckedRadioButtonId();
        if (checked && checkedRadioButtonId != View.NO_ID) {
            final CheckBox checkedItem = (CheckBox) findViewById(checkedRadioButtonId);
            checkedItem.setChecked(!checked);
        }
        item.setChecked(checked);
    }

    private void addNewcheckBoxGroup(final Context context) {
        final CheckBoxGroup checkBoxGroup = new CheckBoxGroup(context);
        checkBoxGroup.setOrientation(CheckBoxGroup.HORIZONTAL);
        checkBoxGroup.setGravity(Gravity.CENTER_HORIZONTAL);
        addView(checkBoxGroup, MATCH_PARENT, WRAP_CONTENT);
        checkBoxGroupList.add(checkBoxGroup);
    }

    @SuppressWarnings("deprecation")
    public static int getScreenWidth(final Context context) {
        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        final Point size = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            display.getSize(size);
        } else {
            size.x = display.getWidth();
        }
        return size.x;
    }

}
