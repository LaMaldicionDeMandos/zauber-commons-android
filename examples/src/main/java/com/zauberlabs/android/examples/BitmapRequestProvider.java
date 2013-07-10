package com.zauberlabs.android.examples;

/**
 * Created by marcelo on 7/10/13.
 */
public interface BitmapRequestProvider {
    void provide(String url, BitmapRequestListener listener);
    void cancel();
}
