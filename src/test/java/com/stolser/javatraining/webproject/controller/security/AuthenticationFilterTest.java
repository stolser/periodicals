package com.stolser.javatraining.webproject.controller.security;

import com.stolser.javatraining.webproject.controller.TestResources;
import com.stolser.javatraining.webproject.model.entity.user.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.CURRENT_USER_ATTR_NAME;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.ORIGINAL_URI_ATTR_NAME;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.SIGN_IN_URI;
import static org.mockito.Mockito.*;

public class AuthenticationFilterTest {
    private HttpSession session = mock(HttpSession.class);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    @Mock
    private FilterChain chain;
    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setId(77);

        when(session.getAttribute(CURRENT_USER_ATTR_NAME)).thenReturn(user);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void doFilter_IfUserInSessionIsNull() throws Exception {
        String requestURI = TestResources.USER_2_INVOICE_10_PAYMENT;
        when(request.getRequestURI()).thenReturn(requestURI);

        new AuthenticationFilter().doFilter(request, response, chain);

        verify(request, times(1)).getRequestURI();
        verify(session, times(1)).setAttribute(ORIGINAL_URI_ATTR_NAME, requestURI);
        verify(response, times(1)).sendRedirect(SIGN_IN_URI);

    }
}