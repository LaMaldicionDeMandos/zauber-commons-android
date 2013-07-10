package com.zauberlabs.android.image_paginator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.google.common.io.Closeables;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by marcelo on 7/10/13.
 */
public class ImageRequestProvider implements BitmapRequestProvider {
    volatile private boolean cancel;


    private void success(final Bitmap bm, final BitmapRequestListener listener) {
        if (!cancel) {
            listener.onBitmap(bm);
        }
    }

    @Override
    public void provide(final String url, final BitmapRequestListener listener) {
        final PhotoLoaderAsyncTask task = new PhotoLoaderAsyncTask();
        task.execute(url, listener);
    }

    class PhotoLoaderAsyncTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected Void doInBackground(final Object... params) {
            final String url = (String)params[0];
            final BitmapRequestListener listener = (BitmapRequestListener)params[1];
            BufferedInputStream bis = null;
            try {
                Bitmap bm;
                final URL aURL = new URL(url);
                final InputStream is = (InputStream) aURL.getContent();
                bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                success(bm, listener);
            } catch (final IOException e) {
                Log.w(ImageRequestProvider.class.getSimpleName(), "Error loading url: " + url);
            } finally {
                Closeables.closeQuietly(bis);
            }
            return null;
        }

    }
    @Override
    public void cancel() {
        cancel = true;
    }
}
