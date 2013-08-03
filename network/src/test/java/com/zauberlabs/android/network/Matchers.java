package com.zauberlabs.android.network;

import org.mockito.Mockito;

/**
 * Created by hernan on 7/11/13.
 */
public class Matchers {

    private Matchers() {}

    public static <T> T isNullOf(Class<T> clazz) {
        return (T) Mockito.isNull();
    }
}
