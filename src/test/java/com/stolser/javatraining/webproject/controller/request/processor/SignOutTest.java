package com.stolser.javatraining.webproject.controller.request.processor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.CURRENT_USER_ATTR_NAME;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.SIGN_IN_URI;
import static org.mockito.Mockito.*;

public class SignOutTest {
    private HttpSession session = mock(HttpSession.class);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);

    @InjectMocks
    private SignOut signOut;

    @Before
    public void setUp() throws Exception {
        when(request.getSession()).thenReturn(session);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getViewName_Should_InvalidateCurrentSession() throws Exception {
        signOut.process(request, response);

        verify(session, times(1)).removeAttribute(CURRENT_USER_ATTR_NAME);
        verify(session, times(1)).invalidate();
        verify(response, times(1)).sendRedirect(SIGN_IN_URI);
    }
}