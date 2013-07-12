package com.zauberlabs.android.groupable;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class RadioGroup extends android.widget.RadioGroup implements Groupable<RadioButton> {
    public RadioGroup(final Context context) {
        super(context);
    }

    public RadioGroup(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public RadioButton addItem(final CharSequence text, final Object tag) {
        return addItem(text, tag, false);
    }

    @Override
    public RadioButton addItem(final CharSequence text, final Object tag, final boolean checked) {
        checkNotNull(text, "Must supply a text");
        checkNotNull(tag, "Must supply a tag");
        int checkedRadioButtonId = getCheckedRadioButtonId();
        clearCheck();
        final RadioButton radioButton = new RadioButton(getContext());
        radioButton.setId(tag.hashCode());
        radioButton.setText(text);
        radioButton.setTag(tag);
        radioButton.setChecked(checked);
        addView(radioButton, new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        if (checked) {
            checkedRadioButtonId = radioButton.getId();
        }
        if (checkedRadioButtonId != View.NO_ID) {
            check(checkedRadioButtonId);
        }
        return radioButton;
    }

    @Override
    public RadioButton findWithTag(final Object tag) {
        checkNotNull(tag, "Must supply a tag");
        return (RadioButton) findViewWithTag(tag);
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
    public List<RadioButton> getAllChecked() {
        final List<RadioButton> checkedOnes = newArrayList();
        final int checkedRadioButtonId = getCheckedRadioButtonId();
        final RadioButton radioButton = (RadioButton) findViewById(checkedRadioButtonId);

        if (checkedRadioButtonId != View.NO_ID && radioButton.isChecked()) {
            checkedOnes.add(radioButton);
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

    @Override
    public void setChecked(final Object tag, final boolean checked) {
        checkNotNull(tag);
        final RadioButton item = findWithTag(tag);
        checkArgument(item != null, "No item found with tag: " + tag);
        item.setChecked(checked);
    }


}
