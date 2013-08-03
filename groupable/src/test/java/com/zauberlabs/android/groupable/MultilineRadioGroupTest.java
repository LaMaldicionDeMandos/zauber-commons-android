package com.zauberlabs.android.groupable;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.widget.CheckBox;

@RunWith(RobolectricTestRunner.class)
@Ignore
public class MultilineRadioGroupTest extends AbstractGroupableTest<MultilineRadioGroup, CheckBox> {
    @Override
    public void setUp() {
        super.setUp();
        groupable = spy(groupable);
        when(groupable.getBulletsPerGroup()).thenReturn(5);
    }

    @Override
    protected MultilineRadioGroup newGroupable() {
        return new MultilineRadioGroup(Robolectric.getShadowApplication().getApplicationContext());
    }

    @Override
    @Test
    public void testGetAllChecked() {
        groupable.addItem("Foo", 4, true);
        groupable.addItem("Bar", 5, false);
        groupable.addItem("Bazz", 6, true);
        assertThat(groupable.getAllChecked().size(), is(1));
        assertThat(groupable.getCheckedRadioButtonId(), is(6));
    }

    @Test
    public void testGetBulletsPerGroup() {
        assertThat(groupable.getBulletsPerGroup(100, 1, 2), is(20));
        assertThat(groupable.getBulletsPerGroup(100, 5, 4), is(7));
        assertThat(groupable.getBulletsPerGroup(250, 10, 4), is(13));
    }

    @Test
    public void testPopulateWithMoreThanBulletsPerGroup() {
        final List<String> items = newArrayList("Foo",
             "Bar",
             "Bazz",
             "Foo2",
             "Bar2",
             "Bazz2",
             "Foo3",
             "Bar3");
        groupable.populate(items, new StringPredicate());
        assertThat(groupable.size(), is(8));
        final List<CheckBoxGroup> radioGroupList = groupable.getcheckBoxGroupList();
        assertThat(radioGroupList.size(), is(2));
        final CheckBoxGroup radioGroup1 = radioGroupList.get(0);
        assertThat(radioGroup1.size(), is(5));
        CheckBox radioButton = radioGroup1.findWithTag("Foo2");
        assertNotNull(radioButton);
        assertThat((String) radioButton.getTag(), is("Foo2"));
        assertThat(radioButton.getText().toString(), is("Foo2"));
        assertFalse(radioButton.isChecked());
        final CheckBoxGroup radioGroup2 = radioGroupList.get(1);
        assertThat(radioGroup2.size(), is(3));
        radioButton = radioGroup2.findWithTag("Bar3");
        assertNotNull(radioButton);
        assertThat((String) radioButton.getTag(), is("Bar3"));
        assertThat(radioButton.getText().toString(), is("Bar3"));
        assertFalse(radioButton.isChecked());
    }

    @Test
    public void testSizeWithMoreThanBulletsPerGroup() {
        final List<String> items = newArrayList("Foo",
             "Bar",
             "Bazz",
             "Foo2",
             "Bar2",
             "Bazz2",
             "Foo3",
             "Bar3");
        groupable.populate(items, new StringPredicate());
        assertThat(groupable.size(), is(8));
    }

    @Test
public void testGetCheckedRadioButtonId() {
        groupable.addItem("Foo", 4, true);
        assertThat(groupable.getCheckedRadioButtonId(), is(4));
        groupable.addItem("Bar", 5, false);
        assertThat(groupable.getCheckedRadioButtonId(), is(4));
        groupable.addItem("Bazz", 6, true);
        assertThat(groupable.getCheckedRadioButtonId(), is(6));
        groupable.addItem("Bart", 7);
        groupable.addItem("Lisa", 8);
        assertThat(groupable.getCheckedRadioButtonId(), is(6));
}
}
