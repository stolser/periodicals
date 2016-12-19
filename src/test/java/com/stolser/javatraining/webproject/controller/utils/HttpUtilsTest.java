package com.stolser.javatraining.webproject.controller.utils;

import com.stolser.javatraining.webproject.controller.TestResources;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.periodical.PeriodicalCategory;
import com.stolser.javatraining.webproject.model.entity.user.User;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpUtilsTest {

    private static final int USER_ID = 2;
    private HttpSession session;
    private HttpServletRequest request;

    @Before
    public void setup() {
        User user = new User();
        user.setId(USER_ID);

        session = mock(HttpSession.class);
        when(session.getAttribute(CURRENT_USER_ATTR_NAME)).thenReturn(user);

        request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void getFirstIdFromUri_Should_ReturnCorrectId() throws Exception {
        String uri = TestResources.USER_2_INVOICE_10_PAYMENT;
        int expected = 2;
        int actual = HttpUtils.getFirstIdFromUri(uri);

        assertEquals(expected, actual);
    }

    @Test
    public void getUserIdFromSession_Should_ReturnCorrectId() throws Exception {
        long expected = 2;
        long actual = HttpUtils.getUserIdFromSession(request);

        assertEquals(expected, actual);
    }

    @Test
    public void getPeriodicalFromRequest_Should_ReturnPeriodical() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(ENTITY_ID_PARAM_NAME)).thenReturn("10");
        when(request.getParameter(PERIODICAL_NAME_PARAM_NAME)).thenReturn("Test Name");
        when(request.getParameter(PERIODICAL_CATEGORY_PARAM_NAME)).thenReturn("news");
        when(request.getParameter(PERIODICAL_PUBLISHER_PARAM_NAME)).thenReturn("Test Publisher");
        when(request.getParameter(PERIODICAL_DESCRIPTION_PARAM_NAME)).thenReturn("Test description");
        when(request.getParameter(PERIODICAL_COST_PARAM_NAME)).thenReturn("99");
        when(request.getParameter(PERIODICAL_STATUS_PARAM_NAME)).thenReturn("active");

        Periodical periodical = HttpUtils.getPeriodicalFromRequest(request);

        assertEquals(10, periodical.getId());
        assertEquals("Test Name", periodical.getName());
        assertEquals(PeriodicalCategory.NEWS, periodical.getCategory());
        assertEquals("Test Publisher", periodical.getPublisher());
        assertEquals("Test description", periodical.getDescription());
        assertEquals(99, periodical.getOneMonthCost());
        assertEquals(Periodical.Status.ACTIVE, periodical.getStatus());
    }
}