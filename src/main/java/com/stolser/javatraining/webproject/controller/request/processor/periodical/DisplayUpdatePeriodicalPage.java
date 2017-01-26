package com.stolser.javatraining.webproject.controller.request.processor.periodical;

import com.stolser.javatraining.webproject.controller.request.processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.periodical.PeriodicalCategory;
import com.stolser.javatraining.webproject.service.PeriodicalService;
import com.stolser.javatraining.webproject.service.impl.PeriodicalServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;
import static java.util.Objects.isNull;

/**
 * Processes a GET request to a page where admin can update information of one periodical.
 */
public class DisplayUpdatePeriodicalPage implements RequestProcessor {
    private static final String NO_PERIODICAL_WITH_ID_IN_DB = "There is no periodical with id %d in the db.";
    private PeriodicalService periodicalService = PeriodicalServiceImpl.getInstance();

    private DisplayUpdatePeriodicalPage() {}

    private static class InstanceHolder {
        private static final DisplayUpdatePeriodicalPage INSTANCE = new DisplayUpdatePeriodicalPage();
    }

    public static DisplayUpdatePeriodicalPage getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public Optional<String> process(HttpServletRequest request, HttpServletResponse response) {
        long periodicalId = HttpUtils.getFirstIdFromUri(request.getRequestURI());
        Periodical periodical = periodicalService.findOneById(periodicalId);

        if (isNull(periodical)) {
            throw new NoSuchElementException(String.format(NO_PERIODICAL_WITH_ID_IN_DB, periodicalId));
        }

        setRequestAttributes(request, periodical);

        return Optional.of(CREATE_EDIT_PERIODICAL_VIEW_NAME);
    }

    private void setRequestAttributes(HttpServletRequest request, Periodical periodical) {
        request.setAttribute(PERIODICAL_ATTR_NAME, periodical);
        request.setAttribute(PERIODICAL_OPERATION_TYPE_PARAM_ATTR_NAME,
                Periodical.OperationType.UPDATE.name().toLowerCase());
        request.setAttribute(PERIODICAL_STATUSES_ATTR_NAME, Periodical.Status.values());
        request.setAttribute(PERIODICAL_CATEGORIES_ATTR_NAME, PeriodicalCategory.values());
    }
}
