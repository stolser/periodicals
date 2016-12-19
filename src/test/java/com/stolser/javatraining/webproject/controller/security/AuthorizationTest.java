package com.stolser.javatraining.webproject.controller.security;

import com.stolser.javatraining.webproject.model.entity.user.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthorizationTest {
    private static final String NOT_ADMIN_ROLE = "notAdmin";
    private HttpSession session = mock(HttpSession.class);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private User admin;
    private User notAdmin;

    @Before
    public void setUp() throws Exception {
        Set<String> adminRole = new HashSet<>();
        adminRole.add(ADMIN_ROLE_NAME);

        admin = new User();
        admin.setRoles(adminRole);

        Set<String> notAdminRole = new HashSet<>();
        adminRole.add(NOT_ADMIN_ROLE);

        notAdmin = new User();
        notAdmin.setRoles(notAdminRole);

        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void checkPermissions_AccessToPersistPeriodical_ShouldReturn_TrueForAdmin() throws Exception {
        when(request.getMethod()).thenReturn("post");
        when(request.getRequestURI()).thenReturn(PERIODICAL_LIST_URI);
        when(session.getAttribute(CURRENT_USER_ATTR_NAME)).thenReturn(admin);

        Assert.assertTrue(Authorization.getInstance().checkPermissions(request));

    }

    @Test
    public void checkPermissions_AccessToPersistPeriodical_ShouldReturn_FalseForNotAdmin() throws Exception {
        when(request.getMethod()).thenReturn("post");
        when(request.getRequestURI()).thenReturn(PERIODICAL_LIST_URI);
        when(session.getAttribute(CURRENT_USER_ATTR_NAME)).thenReturn(notAdmin);

        Assert.assertFalse(Authorization.getInstance().checkPermissions(request));

    }

    @Test
    public void checkPermissions_AccessToDisplayPeriodicals_ShouldReturn_TrueForAdmin() throws Exception {
        when(request.getMethod()).thenReturn("get");
        when(request.getRequestURI()).thenReturn(PERIODICAL_LIST_URI);
        when(session.getAttribute(CURRENT_USER_ATTR_NAME)).thenReturn(admin);

        Assert.assertTrue(Authorization.getInstance().checkPermissions(request));

    }

    @Test
    public void checkPermissions_AccessToDisplayPeriodicals_ShouldReturn_TrueForNotAdmin() throws Exception {
        when(request.getMethod()).thenReturn("get");
        when(request.getRequestURI()).thenReturn(PERIODICAL_LIST_URI);
        when(session.getAttribute(CURRENT_USER_ATTR_NAME)).thenReturn(notAdmin);

        Assert.assertTrue(Authorization.getInstance().checkPermissions(request));

    }

    @Test
    public void checkPermissions_AccessToDisplayUsers_ShouldReturn_FalseForNotAdmin() throws Exception {
        when(request.getMethod()).thenReturn("get");
        when(request.getRequestURI()).thenReturn("/backend/users");
        when(session.getAttribute(CURRENT_USER_ATTR_NAME)).thenReturn(notAdmin);

        Assert.assertFalse(Authorization.getInstance().checkPermissions(request));

    }

    @Test
    public void checkPermissions_AccessToDisplayUsers_ShouldReturn_TrueForAdmin() throws Exception {
        when(request.getMethod()).thenReturn("get");
        when(request.getRequestURI()).thenReturn("/backend/users");
        when(session.getAttribute(CURRENT_USER_ATTR_NAME)).thenReturn(admin);

        Assert.assertTrue(Authorization.getInstance().checkPermissions(request));

    }

    @Test
    public void checkPermissions_AccessToAdminPanel_ShouldReturn_FalseForNotAdmin() throws Exception {
        when(request.getMethod()).thenReturn("get");
        when(request.getRequestURI()).thenReturn(ADMIN_PANEL_URI);
        when(session.getAttribute(CURRENT_USER_ATTR_NAME)).thenReturn(notAdmin);

        Assert.assertFalse(Authorization.getInstance().checkPermissions(request));

    }

    @Test
    public void checkPermissions_AccessToAdminPanel_ShouldReturn_TrueForAdmin() throws Exception {
        when(request.getMethod()).thenReturn("get");
        when(request.getRequestURI()).thenReturn(ADMIN_PANEL_URI);
        when(session.getAttribute(CURRENT_USER_ATTR_NAME)).thenReturn(admin);

        Assert.assertTrue(Authorization.getInstance().checkPermissions(request));

    }

    @Test
    public void checkPermissions_AccessToUpdatePeriodical_ShouldReturn_TrueForAdmin() throws Exception {
        when(request.getMethod()).thenReturn("get");
        when(request.getRequestURI()).thenReturn("/backend/periodicals/10/update");
        when(session.getAttribute(CURRENT_USER_ATTR_NAME)).thenReturn(admin);

        Assert.assertTrue(Authorization.getInstance().checkPermissions(request));

    }

    @Test
    public void checkPermissions_AccessToUpdatePeriodical_ShouldReturn_FalseForNotAdmin() throws Exception {
        when(request.getMethod()).thenReturn("get");
        when(request.getRequestURI()).thenReturn("/backend/periodicals/10/update");
        when(session.getAttribute(CURRENT_USER_ATTR_NAME)).thenReturn(notAdmin);

        Assert.assertFalse(Authorization.getInstance().checkPermissions(request));

    }
}