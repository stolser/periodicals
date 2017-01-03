package com.stolser.javatraining.webproject.controller.request_processor.periodical;

import com.stolser.javatraining.webproject.controller.TestResources;
import com.stolser.javatraining.webproject.service.PeriodicalService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DisplayOnePeriodicalTest {
    private HttpSession session = mock(HttpSession.class);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private PeriodicalService periodicalService = mock(PeriodicalService.class);

    @InjectMocks
    private DisplayOnePeriodical displayOnePeriodical;

    @Before
    public void setUp() throws Exception {
        when(request.getSession()).thenReturn(session);

        when(request.getRequestURI()).thenReturn(TestResources.USER_2_INVOICE_10_PAYMENT);
        when(periodicalService.findOneById(2)).thenReturn(null);

        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = NoSuchElementException.class)
    public void getViewName_Should_ThrowException() throws Exception {
        displayOnePeriodical.process(request, response);
    }
}