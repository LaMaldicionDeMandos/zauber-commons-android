package com.zauberlabs.android.network.operation;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.ObjectParser;
import com.octo.android.robospice.persistence.googlehttpclient.json.GsonObjectPersisterFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.net.MalformedURLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by hernan on 7/11/13.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpRequest.class, HttpResponse.class, HttpRequestFactory.class})
public class HttpGetOperationTest {

    private static final String BASE_URL = "http://www.somewhere.far.beyond";
    private static final String PATH = "resource";
    private static final String FULL_URL = "http://www.somewhere.far.beyond/resource";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private HttpRequest request;
    private HttpResponse response;
    private HttpRequestFactory factory;

    private HttpOperation operation;

    @Before
    public void setUp() throws Exception {
        request = mock(HttpRequest.class);
        response = mock(HttpResponse.class);
        factory = mock(HttpRequestFactory.class);
        operation = new HttpGetOperation(BASE_URL);

        when(factory.buildGetRequest(any(GenericUrl.class))).thenReturn(request);
    }

    @Test
    public void shouldCreateOperationWithBaseURL() throws MalformedURLException {
        assertNotNull(new HttpGetOperation(BASE_URL));
    }

    @Test
    public void shouldFailToCreateWhenURLIsInvalid() throws Exception {
        expectedException.expect(MalformedURLException.class);
        assertNull(new HttpGetOperation("INVALID"));
    }

    @Test
    public void shouldFailToCreateWhenURLIsNull() throws Exception {
        expectedException.expect(MalformedURLException.class);
        assertNull(new HttpGetOperation(null));
    }

    @Test
    public void shouldCreateRequest() throws IOException {
        final HttpRequest request = operation.buildRequest(PATH, factory);
        assertNotNull(request);
        verify(factory).buildGetRequest(any(GenericUrl.class));
    }

    @Test
    public void shouldCreateRequestWithResourcePath() throws Exception {
        operation.buildRequest(PATH, factory);
        ArgumentCaptor<GenericUrl> captor = ArgumentCaptor.forClass(GenericUrl.class);
        verify(factory).buildGetRequest(captor.capture());
        GenericUrl url = captor.getValue();
        assertEquals(FULL_URL, url.build());
    }

    @Test
    public void shouldSetParserOnRequest() throws Exception {
        operation.buildRequest(PATH, factory);
        verify(request).setParser(any(ObjectParser.class));
    }
}
