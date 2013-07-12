package com.zauberlabs.android.groupable;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.widget.RadioButton;

@RunWith(GroupableTestRunner.class)
public class RadioGroupTest extends AbstractGroupableTest<RadioGroup, android.widget.RadioButton>{
    @Override
    protected RadioGroup newGroupable() {
        return groupable = new RadioGroup(null);
    }

    @Test
    public void testGetAllCheckedIsEmptyWithFalseElements() {
        groupable.addItem("Foo", 4, true);
        groupable.addItem("Bar", 5, false);
        assertFalse(groupable.getAllChecked().isEmpty());
        groupable.setChecked(4, false);
        assertTrue(groupable.getAllChecked().isEmpty());
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

    @Override
    @Test
    public void testPopulate() {
        super.testPopulate();
        final RadioButton checkedElem = groupable.findWithTag("Foo");
        assertThat(checkedElem.getText().toString(), is("Foo"));
    }
}
