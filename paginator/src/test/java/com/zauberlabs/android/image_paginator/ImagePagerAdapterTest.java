package com.zauberlabs.android.image_paginator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by marcelo on 7/18/13.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ImagePagerAdapterTest {
    private ImagePagerAdapter<String> adapter;

    @Before
    public void before() {
        adapter = new TestPagerAdapter(Arrays.asList("bla"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkList() {
        new TestPagerAdapter(null);
    }

    @Test(expected = MalformedURLException.class)
    public void nullUrl() throws MalformedURLException{
        adapter.getUrl(null);
    }

    @Test(expected = MalformedURLException.class)
    public void illegalUrl() throws MalformedURLException{
        adapter.getUrl("bla");
    }

    @Test
    public void legalUrl() {
        try {
            URL url = adapter.getUrl("http://www.example.com/");
            assertNotNull(url);
        } catch (MalformedURLException e) {}
    }

}
class TestPagerAdapter extends ImagePagerAdapter<String> {
    public TestPagerAdapter(List<String> list) {
        super(null, list);
    }
    @Override
    protected URL getUrl(String item) throws MalformedURLException{
        return new URL(item);
    }
}
