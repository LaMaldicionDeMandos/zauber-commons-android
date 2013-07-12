package com.zauberlabs.android.groupable;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.widget.CheckBox;

@RunWith(GroupableTestRunner.class)
public class CheckBoxGroupTest extends AbstractGroupableTest<CheckBoxGroup, CheckBox> {

    @Override
    protected CheckBoxGroup newGroupable() {
        return new CheckBoxGroup(null);
    }

    @Test
    public void testGetAllUnchecked() {
        groupable.addItem("Foo", 1, true);
        groupable.addItem("Bar", 2);
        groupable.addItem("Baz", 1, true);
        final List<CheckBox> allChecked = groupable.getAllUnchecked();
        assertThat(allChecked.size(), is(1));
    }

    @Test
    public void testGetAll() {
        groupable.addItem("Foo", 1, true);
        groupable.addItem("Bar", 2);
        groupable.addItem("Baz", 1, true);
        final List<CheckBox> all = groupable.getAll(new com.google.common.base.Predicate<CheckBox>() {

            @Override
            public boolean apply(final CheckBox checkBox) {
                return true;
            }
        });
        assertThat(all.size(), is(3));
    }

    @Override
    @Test
    public void testGetAllChecked() {
        groupable.addItem("Foo", 1, true);
        groupable.addItem("Bar", 2);
        groupable.addItem("Baz", 1, true);
        final List<CheckBox> allChecked = groupable.getAllChecked();
        assertThat(allChecked.size(), is(2));
    }


}
