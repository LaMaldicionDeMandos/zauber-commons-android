package com.zauberlabs.android.groupable;

import java.io.File;

import org.junit.runners.model.InitializationError;

import com.xtremelabs.robolectric.RobolectricTestRunner;

public class GroupableTestRunner extends RobolectricTestRunner {
    public GroupableTestRunner(final Class<?> testClass) throws InitializationError {
        super(testClass, new File(GroupableTestRunner.class.getResource("/").getPath()));
    }
}
