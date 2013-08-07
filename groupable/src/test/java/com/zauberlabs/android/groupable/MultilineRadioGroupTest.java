package com.zauberlabs.android.groupable;

import android.widget.CheckBox;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.DEFAULT)
public class MultilineRadioGroupTest extends AbstractGroupableTest<MultilineRadioGroup, CheckBox> {

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
             "Foo4",
             "Foo5",
             "Foo6",
             "Foo7",
             "Foo8",
             "Foo9",
             "Foo10",
             "Foo11",
             "Foo12",
             "Foo13",
             "Foo14",
             "Foo15",
             "Foo16",
             "Foo17",
             "Foo18",
             "Foo19",
             "Ba20");
        groupable.populate(items, new StringPredicate());
        assertThat(groupable.size(), is(24));
        final List<CheckBoxGroup> radioGroupList = groupable.getcheckBoxGroupList();
        assertThat(radioGroupList.size(), is(2));
        final CheckBoxGroup radioGroup1 = radioGroupList.get(0);
        assertThat(radioGroup1.size(), is(20));
        CheckBox radioButton = radioGroup1.findWithTag("Foo2");
        assertNotNull(radioButton);
        assertThat((String) radioButton.getTag(), is("Foo2"));
        assertThat(radioButton.getText().toString(), is("Foo2"));
        assertFalse(radioButton.isChecked());
        final CheckBoxGroup radioGroup2 = radioGroupList.get(1);
        assertThat(radioGroup2.size(), is(4));
        radioButton = radioGroup2.findWithTag("Ba20");
        assertNotNull(radioButton);
        assertThat((String) radioButton.getTag(), is("Ba20"));
        assertThat(radioButton.getText().toString(), is("Ba20"));
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
