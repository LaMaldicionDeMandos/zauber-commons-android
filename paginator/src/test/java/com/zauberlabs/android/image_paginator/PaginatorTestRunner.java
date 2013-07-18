package com.zauberlabs.android.image_paginator;

import com.xtremelabs.robolectric.RobolectricTestRunner;

import org.junit.runners.model.InitializationError;

import java.io.File;

/**
 * Created by marcelo on 7/18/13.
 */
public class PaginatorTestRunner extends RobolectricTestRunner {
    public PaginatorTestRunner(final Class<?> testClass) throws InitializationError {
        super(testClass, new File(PaginatorTestRunner.class.getResource("/").getPath()));
    }
}
