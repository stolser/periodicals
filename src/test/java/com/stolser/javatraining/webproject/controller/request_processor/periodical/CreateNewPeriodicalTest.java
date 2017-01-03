package com.stolser.javatraining.webproject.controller.request_processor.periodical;

import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.PERIODICAL_ATTR_NAME;
import static org.mockito.Mockito.*;

public class CreateNewPeriodicalTest {
    private HttpSession session = mock(HttpSession.class);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);

    @Before
    public void setUp() throws Exception {
        when(session.getAttribute(PERIODICAL_ATTR_NAME)).thenReturn(new Periodical());
        when(request.getSession()).thenReturn(session);

    }

    @Test
    public void getViewName_Should_RemovePeriodicalFromSession() throws Exception {
        new CreateNewPeriodical().process(request, response);

        verify(session, times(1)).removeAttribute(PERIODICAL_ATTR_NAME);
    }

    @Test
    public void getViewName_Should_CallSetAttributeOnRequestFiveTimes() throws Exception {
        new CreateNewPeriodical().process(request, response);

        verify(request, times(5)).setAttribute(anyString(), any());
    }
}