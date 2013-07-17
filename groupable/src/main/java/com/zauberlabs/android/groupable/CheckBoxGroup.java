package com.zauberlabs.android.groupable;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.LinearLayout;



public class CheckBoxGroup extends LinearLayout implements Groupable<CheckBox> {
    private static final int INVALID_LAYOUT = -1;
    private int checkboxResource = INVALID_LAYOUT;

    public CheckBoxGroup(final Context context) {
        super(context);
    }

    public CheckBoxGroup(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CheckBoxGroup(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public CheckBox addItem(final CharSequence text, final Object tag) {
        return addItem(text, tag, false);
    }

    @Override
    public CheckBox addItem(final CharSequence text, final Object tag, final boolean checked) {
        checkNotNull(text, "Must supply a text");
        checkNotNull(tag, "Must supply a tag");
        final CheckBox checkBox = (checkboxResource != INVALID_LAYOUT)
                ? (CheckBox) LayoutInflater.from(getContext()).inflate(checkboxResource, null)
                : new CheckBox(getContext());

        checkBox.setId(tag.hashCode());
        checkBox.setText(text);
        checkBox.setTag(tag);
        checkBox.setChecked(checked);
        addView(checkBox, new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        return checkBox;
    }

    @Override
    public List<CheckBox> getAllChecked() {
        return getAll(new com.google.common.base.Predicate<CheckBox>() {
            @Override
            public boolean apply(final CheckBox checkBox) {
                return checkBox.isChecked();
            }
        });
    }

    public List<CheckBox> getAllUnchecked() {
        return getAll(new com.google.common.base.Predicate<CheckBox>() {
            @Override
            public boolean apply(final CheckBox checkBox) {
                return !checkBox.isChecked();
            }
        });
    }

    public List<CheckBox> getAll(final com.google.common.base.Predicate<CheckBox> predicate) {
        final List<CheckBox> checkedOnes = newArrayList();
        for (int i = 0; i < getChildCount(); i++) {
            final CheckBox checkbox = (CheckBox) getChildAt(i);
            if (predicate.apply(checkbox)) {
                checkedOnes.add(checkbox);
            }
        }
        return checkedOnes;
    }

    @Override
    public CheckBox findWithTag(final Object tag) {
        checkNotNull(tag, "Must supply a tag");
        return (CheckBox) findViewWithTag(tag);
    }

    @Override
    public int size() {
        return getChildCount();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void clear() {
        removeAllViews();
    }

    @Override
    public <F> void populate(final List<F> items, final Predicate<F> predicate) {
        clear();
        for (final F item : items) {
            addItem(predicate.applyToText(item), predicate.applyToTag(item));
        }
    }

    @Override
    public void setChecked(final Object tag, final boolean checked) {
        checkNotNull(tag);
        final CheckBox item = (CheckBox) findViewWithTag(tag);
        checkArgument(item != null, "No item found with tag: " + tag);
        item.setChecked(checked);
    }

    public void setCheckboxResource(int resource) {
        checkboxResource = resource;
    }

    public int getCheckboxResource() {
        return checkboxResource;
    }


}
