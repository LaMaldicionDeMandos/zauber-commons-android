package com.zauberlabs.android.network.operation;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.util.ObjectParser;

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
import java.util.HashMap;
import java.util.Map;

import static com.zauberlabs.android.network.Matchers.isNullOf;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by hernan on 7/11/13.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpRequest.class, HttpResponse.class, HttpRequestFactory.class})
public class HttpBaseOperationTest {

    private static final String BASE_URL = "http://www.somewhere.far.beyond";
    private static final String PATH = "resource";
    private static final String FULL_URL = "http://www.somewhere.far.beyond/resource";
    private static final String FULL_URL_WITH_PARAMS = "http://www.somewhere.far.beyond/resource?key=value";
    private static final String HEADER_KEY = "user-password";
    private static final String HEADER_VALUE = "bla:ble";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private HttpRequest request;
    private HttpResponse response;
    private HttpRequestFactory factory;
    private Map<String,String> parameters;
    private Object object;

    private HttpOperation operation;

    @Before
    public void setUp() throws Exception {
        request = mock(HttpRequest.class);
        response = mock(HttpResponse.class);
        factory = mock(HttpRequestFactory.class);
        operation = new HttpBaseOperation(BASE_URL, HttpMethods.PUT);
        parameters = new HashMap<String, String>();
        parameters.put("key", "value");
        object = mock(Object.class);

        when(request.getHeaders()).thenCallRealMethod();
        when(request.setHeaders(any(HttpHeaders.class))).thenCallRealMethod();
        when(factory.buildRequest(anyString(), any(GenericUrl.class), any(HttpContent.class))).thenReturn(request);

    }

    @Test
    public void shouldCreateOperationWithBaseURL() throws MalformedURLException {
        assertNotNull(new HttpBaseOperation(BASE_URL, HttpMethods.GET));
    }

    @Test
    public void shouldFailToCreateWhenURLIsInvalid() throws Exception {
        expectedException.expect(MalformedURLException.class);
        assertNull(new HttpBaseOperation("INVALID", HttpMethods.GET));
    }

    @Test
    public void shouldFailToCreateWhenHttpMethodIsNull() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        assertNull(new HttpBaseOperation(BASE_URL, null));
    }

    @Test
    public void shouldFailToCreateWhenHttpMethodIsEmpty() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        assertNull(new HttpBaseOperation(BASE_URL, ""));
    }

    @Test
    public void shouldCreateOperationWithHttpMethod() throws MalformedURLException {
        assertNotNull(new HttpBaseOperation(BASE_URL, HttpMethods.PATCH));
    }

    @Test
    public void shouldFailToCreateWhenURLIsNull() throws Exception {
        expectedException.expect(MalformedURLException.class);
        assertNull(new HttpBaseOperation(null, HttpMethods.GET));
    }

    @Test
    public void shouldCreateRequest() throws IOException {
        final HttpRequest request = operation.buildRequest(PATH, factory);
        assertNotNull(request);
        verify(factory).buildRequest(eq(HttpMethods.PUT), any(GenericUrl.class), isNullOf(HttpContent.class));
    }

    @Test
    public void shouldCreateRequestWithResourcePath() throws Exception {
        operation.buildRequest(PATH, factory);
        ArgumentCaptor<GenericUrl> captor = ArgumentCaptor.forClass(GenericUrl.class);
        verify(factory).buildRequest(eq(HttpMethods.PUT), captor.capture(), isNullOf(HttpContent.class));
        GenericUrl url = captor.getValue();
        assertEquals(FULL_URL, url.build());
    }

    @Test
    public void shouldSetParserOnRequest() throws Exception {
        operation.buildRequest(PATH, factory);
        verify(request).setParser(any(ObjectParser.class));
    }

    @Test
    public void shouldReturnSuccessForHttpSuccess() {
        when(response.getStatusCode()).thenReturn(HttpStatusCodes.STATUS_CODE_OK);
        assertTrue(operation.isSuccessful(response));
    }

    @Test
    public void shouldReturnSuccessForServerError() {
        when(response.getStatusCode()).thenReturn(HttpStatusCodes.STATUS_CODE_SERVER_ERROR);
        assertFalse(operation.isSuccessful(response));
    }

    @Test
    public void shouldReturnSuccessForInvalidRequest() {
        when(response.getStatusCode()).thenReturn(400);
        assertFalse(operation.isSuccessful(response));
    }

    @Test
    public void shouldCreateOperationWithParams() throws MalformedURLException {
        assertNotNull(new HttpBaseOperation(BASE_URL, HttpMethods.GET, new HashMap<String, Object>()));
    }

    @Test
    public void shouldCreateRequestWithResourcePathAndParameters() throws Exception {
        operation = new HttpBaseOperation(BASE_URL, HttpMethods.PUT, parameters);
        operation.buildRequest(PATH, factory);
        ArgumentCaptor<GenericUrl> captor = ArgumentCaptor.forClass(GenericUrl.class);
        verify(factory).buildRequest(eq(HttpMethods.PUT), captor.capture(), isNullOf(HttpContent.class));
        GenericUrl url = captor.getValue();
        assertEquals(FULL_URL_WITH_PARAMS, url.build());
    }

    @Test
    public void shouldCreateOperationWithBodyContent() throws MalformedURLException {
        assertNotNull(new HttpBaseOperation(BASE_URL, HttpMethods.POST, object));
    }

    @Test
    public void shouldCreateRequestWithBody() throws Exception {
        operation = new HttpBaseOperation(BASE_URL, HttpMethods.POST, object);
        HttpRequest request = operation.buildRequest(PATH, factory);
        assertNotNull(request);
        ArgumentCaptor<HttpContent> captor = ArgumentCaptor.forClass(HttpContent.class);
        verify(factory).buildRequest(eq(HttpMethods.POST), any(GenericUrl.class), captor.capture());
        HttpContent content = captor.getValue();
        assertNotNull(content);
        assertThat(content.getType(), containsString("application/json"));
    }

    @Test
    public void souldCreateRequestWithHeader() throws Exception {
        Map<String, Object> headerValues = new HashMap<String, Object>();
        headerValues.put(HEADER_KEY, HEADER_VALUE);
        operation = new HttpOperationFactory(BASE_URL, headerValues).buildGet();
        HttpRequest request = operation.buildRequest(PATH, factory);
        Object value = request.getHeaders().get(HEADER_KEY);
        assertNotNull(value);
        assertEquals(HEADER_VALUE, value);
    }
}
