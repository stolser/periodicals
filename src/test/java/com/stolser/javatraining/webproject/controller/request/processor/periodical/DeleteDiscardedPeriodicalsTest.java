package com.stolser.javatraining.webproject.controller.request.processor.periodical;

import com.stolser.javatraining.webproject.controller.form.validator.front.message.FrontMessageFactory;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.service.PeriodicalService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.Collections;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.MSG_NO_PERIODICALS_TO_DELETE;
import static org.mockito.Mockito.*;

public class DeleteDiscardedPeriodicalsTest {
    private HttpSession session = mock(HttpSession.class);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private PeriodicalService periodicalService = mock(PeriodicalService.class);

    @Mock
    private FrontMessageFactory messageFactory;

    @InjectMocks
    private DeleteDiscardedPeriodicals discardedPeriodicals;

    @Before
    public void setUp() throws Exception {
        when(request.getSession()).thenReturn(session);
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void getViewName_Should_CallFindAllByStatusOnPeriodicalService() throws Exception {
        discardedPeriodicals.process(request, response);

        verify(periodicalService, times(1)).findAllByStatus(Periodical.Status.DISCARDED);
    }

    @Test
    public void getViewName_Should_CallDeleteAllDiscardedOnPeriodicalService() {
        when(periodicalService.findAllByStatus(Periodical.Status.DISCARDED))
                .thenReturn(Arrays.asList(new Periodical()));

        discardedPeriodicals.process(request, response);

        verify(periodicalService, times(1)).deleteAllDiscarded();
        verify(messageFactory, times(1)).getSuccess(anyString());
    }

    @Test
    public void getViewName_Should_Not_CallDeleteAllDiscardedOnPeriodicalService() {
        when(periodicalService.findAllByStatus(Periodical.Status.DISCARDED))
                .thenReturn(Collections.emptyList());

        discardedPeriodicals.process(request, response);

        verify(periodicalService, times(0)).deleteAllDiscarded();
        verify(messageFactory, times(1)).getWarning(MSG_NO_PERIODICALS_TO_DELETE);
    }
}