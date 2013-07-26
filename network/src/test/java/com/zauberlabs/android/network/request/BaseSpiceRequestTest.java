package com.zauberlabs.android.network.request;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import com.zauberlabs.android.network.operation.HttpOperation;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import edu.emory.mathcs.backport.java.util.Arrays;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by hernan on 8/7/13.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpRequest.class, HttpResponse.class, HttpRequestFactory.class})
public class BaseSpiceRequestTest {

    private static final Class<String> RESULT_TYPE = String.class;
    private static final TypeToken<List<String>> RESULT_GENERIC_TYPE = new TypeToken<List<String>>(){};
    private static final String URL = "URL";
    private static final String STRING_VALUE = "VALUE";
    private static final List<String> LIST_VALUE = Lists.newArrayList("VALUE1", "VALUE2");

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private HttpRequest request;
    private HttpResponse response;
    private HttpOperation requestBuilder;
    private HttpHeaders headers;
    private HttpRequestFactory factory;
    private InputStream contentStream;

    private BaseSpiceRequest<String> spiceRequest;

    @Before
    public void setUp() throws Exception {
        request = mock(HttpRequest.class);
        response = mock(HttpResponse.class);
        headers = mock(HttpHeaders.class);
        requestBuilder = mock(HttpOperation.class);
        contentStream = mock(InputStream.class);
        factory = mock(HttpRequestFactory.class);
        spiceRequest = newSpiceRequestFor(RESULT_TYPE);

        when(requestBuilder.buildRequest(anyString(), eq(factory))).thenReturn(request);
        when(request.execute()).thenReturn(response);
        when(response.parseAs(RESULT_TYPE)).thenReturn(STRING_VALUE);
        when(response.parseAs(RESULT_GENERIC_TYPE.getType())).thenReturn(LIST_VALUE);
        when(requestBuilder.isSuccessful(eq(response))).thenReturn(TRUE);
        when(response.getHeaders()).thenReturn(headers);
        when(response.getContent()).thenReturn(contentStream);
    }

    @Test
    public void shouldBuildAndExecuteHttpRequest() throws Exception {
        spiceRequest.loadDataFromNetwork();
        verify(requestBuilder).buildRequest(eq(URL), eq(factory));
        verify(request).execute();
    }

    @Test
    public void shouldBuildRequestWithURL() throws Exception {
        spiceRequest.loadDataFromNetwork();
        verify(requestBuilder).buildRequest(eq(URL), eq(factory));
    }

    @Test
    public void shouldParseResponseAndReturnValue() throws Exception {
        String value = spiceRequest.loadDataFromNetwork();
        assertEquals(STRING_VALUE, value);
        verify(response).parseAs(RESULT_TYPE);
    }

    @Test
    public void shouldReturnValueOnSuccess() throws Exception {
        String value = spiceRequest.loadDataFromNetwork();
        assertEquals(STRING_VALUE, value);
        verify(requestBuilder).isSuccessful(eq(response));
    }

    @Test
    public void shouldReturnListOnSuccess() throws Exception {
        BaseSpiceRequest<List<String>> spiceRequest = newSpiceRequestFor(RESULT_GENERIC_TYPE);
        List<String> value = spiceRequest.loadDataFromNetwork();
        assertEquals(LIST_VALUE, value);
        verify(requestBuilder).isSuccessful(eq(response));
    }

    @Test
    public void shouldReturnNullOnSuccessWhenResultTypeIsVoid() throws Exception {
        final Class clazz = Void.class;
        spiceRequest = newSpiceRequestFor(clazz);
        String value = spiceRequest.loadDataFromNetwork();
        assertNull(value);
        verifyZeroInteractions(response);
    }

    @Test
    public void shouldRaiseExceptionWhenResponseIsNotSuccessful() throws Exception {
        expectedException.expect(HttpResponseException.class);
        when(response.parseAsString()).thenReturn("");
        when(requestBuilder.isSuccessful(any(HttpResponse.class))).thenReturn(FALSE);
        spiceRequest.loadDataFromNetwork();
    }


    private BaseSpiceRequest newSpiceRequestFor(final Class clazz) {
        return new BaseSpiceRequest(clazz, requestBuilder) {
            @Override
            public String getResourcePath() {
                return URL;
            }

            @Override
            public HttpRequestFactory getHttpRequestFactory() {
                return factory;
            }
        };
    }

    private BaseSpiceRequest newSpiceRequestFor(final TypeToken typeToken) {
        return new BaseSpiceRequest(typeToken, requestBuilder) {
            @Override
            public String getResourcePath() {
                return URL;
            }

            @Override
            public HttpRequestFactory getHttpRequestFactory() {
                return factory;
            }
        };
    }
}
