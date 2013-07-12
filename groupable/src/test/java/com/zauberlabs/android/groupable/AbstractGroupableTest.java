package com.zauberlabs.android.groupable;


import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.widget.CompoundButton;

@RunWith(GroupableTestRunner.class)
public abstract class AbstractGroupableTest<G extends Groupable<T>, T extends CompoundButton> {
    protected G groupable;

    @Before
    public void setUp() {

        groupable = newGroupable();
    }

    protected abstract G newGroupable();

    @Test
    public void testAddItemWithDefaultCheckedValue() {
        final T item = groupable.addItem("Foo", 1);
        assertItem(item, false);
    }

    @Test
    public void testAddItemWithCheckedTrue() {
        final T item = groupable.addItem("Foo", 1, true);
        assertItem(item, true);
    }

    @Test
    public void testAddItemWithCheckedFalse() {
        final T item = groupable.addItem("Foo", 1, false);
        assertItem(item, false);
    }

    @Test(expected = NullPointerException.class)
    public void testAddItemFailWithNullText() {
        groupable.addItem(null, 1);
    }

    @Test(expected = NullPointerException.class)
    public void testAddItemFailWithNullTag() {
        groupable.addItem("Foo", null);
    }

    @Test
    public void testGetAllCheckedIsEmpty() {
        assertTrue(groupable.getAllChecked().isEmpty());
    }

    @Test
    public abstract void testGetAllChecked();

    @Test
    public void testFindWithTag() {
        final T item = groupable.addItem("Foo", 1, false);
        final T foundItem = groupable.findWithTag(1);
        assertThat(item.getText(), is(foundItem.getText()));
        assertThat(item.getTag(), is(foundItem.getTag()));
    }

    @Test(expected = NullPointerException.class)
    public void testFindWithTagNull() {
        groupable.findWithTag(null);
    }

    @Test
    public void testFindWithInvalidTag() {
        final T item = groupable.findWithTag("any tag");
        assertNull(item);
    }

    @Test
    public void testClear() {
        groupable.addItem("Foo", 1);
        groupable.addItem("Bar", 2);
        groupable.addItem("Baz", 1);
        assertThat(groupable.size(), is(3));
        groupable.clear();
        assertThat(groupable.size(), is(0));
    }

    @Test
    public void testSize() {
        groupable.addItem("Foo", 1);
        groupable.addItem("Bar", 2);
        assertThat(groupable.size(), is(2));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(groupable.isEmpty());
    }

    @Test
    public void testIsNotEmpty() {
        groupable.addItem("Foo", 1);
        assertFalse(groupable.isEmpty());
    }

    @Test
    public void testSetChecked() {
        groupable.addItem("Foo", 1);
        assertTrue(groupable.getAllChecked().isEmpty());
        groupable.setChecked(1, true);
        assertFalse(groupable.getAllChecked().isEmpty());
        groupable.setChecked(1, false);
        assertTrue(groupable.getAllChecked().isEmpty());
    }

    @Test(expected = NullPointerException.class)
    public void testSetCheckedWithNullTag() {
        groupable.setChecked(null, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetCheckedWithInvalidTag() {
        groupable.setChecked(42, true);
    }

    @Test
    public void testPopulate() {
        final List<String> items = newArrayList("Foo", "Bar", "Bazz");
        groupable.populate(items, new StringPredicate());
        assertThat(groupable.size(), is(3));
    }

    @Test
    public void testPopulateWithEmptyList() {
        final List<String> newArrayList = newArrayList();
        groupable.populate(newArrayList, new StringPredicate());
        assertTrue(groupable.isEmpty());
    }

    @Test
    public void testPopulateWithEmptyListHavingElements() {
        final List<String> items = newArrayList("Foo", "Bar");
        groupable.populate(items, new StringPredicate());
        assertThat(groupable.size(), is(2));
        final List<String> newArrayList = newArrayList();
        groupable.populate(newArrayList, new StringPredicate());
        assertTrue(groupable.isEmpty());
    }

    private void assertItem(final T item, final boolean isChecked) {
        assertThat(item.getId(), is(new Integer(1).hashCode()));
        assertThat(item.getText().toString(), is("Foo"));
        assertThat((Integer) item.getTag(), is(1));
        assertThat(item.isChecked(), is(isChecked));
        final T foundItem = groupable.findWithTag(1);
        assertThat(item.getId(), is(foundItem.getId()));
        assertThat(item.getText(), is(foundItem.getText()));
        assertThat(item.getTag(), is(foundItem.getTag()));
        assertThat(item.isChecked(), is(foundItem.isChecked()));
    }

    class StringPredicate implements Groupable.Predicate<String> {

        @Override
        public CharSequence applyToText(final String item) {
            return item;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <K> K applyToTag(final String item) {
            return (K)item;
        }

    }


}
