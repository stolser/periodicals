package com.stolser.javatraining.webproject.controller.request.processor.periodical;

import com.stolser.javatraining.webproject.controller.form.validator.front.message.FrontMessageFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;
import static org.mockito.Mockito.*;

public class PersistOnePeriodicalTest {
    private HttpSession session = mock(HttpSession.class);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    @Mock
    private FrontMessageFactory messageFactory;
    @InjectMocks
    private PersistOnePeriodical persistOnePeriodical;


    @Before
    public void setUp() throws Exception {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(ENTITY_ID_PARAM_NAME)).thenReturn("10");
        when(request.getParameter(PERIODICAL_NAME_PARAM_NAME)).thenReturn("Test Name");
        when(request.getParameter(PERIODICAL_CATEGORY_PARAM_NAME)).thenReturn("news");
        when(request.getParameter(PERIODICAL_PUBLISHER_PARAM_NAME)).thenReturn("Test Publisher");
        when(request.getParameter(PERIODICAL_DESCRIPTION_PARAM_NAME)).thenReturn("Test description");
        when(request.getParameter(PERIODICAL_COST_PARAM_NAME)).thenReturn("99");
        when(request.getParameter(PERIODICAL_STATUS_PARAM_NAME)).thenReturn("nonExistingStatus");

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void getViewName_Should_ReturnNullIfRequestHasIncorrectData() throws Exception {
        Assert.assertNull(persistOnePeriodical.process(request, response));

        verify(messageFactory, times(1)).getError(MSG_PERIODICAL_PERSISTING_ERROR);
    }
}