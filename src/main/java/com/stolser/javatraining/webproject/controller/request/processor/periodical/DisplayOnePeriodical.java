package com.stolser.javatraining.webproject.controller.request.processor.periodical;

import com.stolser.javatraining.webproject.controller.request.processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.service.PeriodicalService;
import com.stolser.javatraining.webproject.service.impl.PeriodicalServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

/**
 * Processes a GET request to page with the information of the selected individual periodical.
 */
public final class DisplayOnePeriodical implements RequestProcessor {
    private static final String NO_PERIODICAL_WITH_ID_IN_DB = "There is no periodical with id %d in the db.";
    private PeriodicalService periodicalService = PeriodicalServiceImpl.getInstance();

    private DisplayOnePeriodical() {}

    private static class InstanceHolder {
        private static final DisplayOnePeriodical INSTANCE = new DisplayOnePeriodical();
    }

    public static DisplayOnePeriodical getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {
        User currentUser = HttpUtils.getCurrentUserFromFromDb(request);
        long periodicalId = HttpUtils.getFirstIdFromUri(request.getRequestURI());
        Periodical periodicalInDb = periodicalService.findOneById(periodicalId);

        checkPeriodicalExists(periodicalId, periodicalInDb);

        if (hasUserNotEnoughPermissions(currentUser, periodicalInDb)) {
            HttpUtils.sendRedirect(request, response, ACCESS_DENIED_URI);
            return null;
        }

        request.setAttribute(PERIODICAL_ATTR_NAME, periodicalInDb);

        return ONE_PERIODICAL_VIEW_NAME;
    }

    private void checkPeriodicalExists(long periodicalId, Periodical periodicalInDb) {
        if (periodicalInDb == null) {
            throw new NoSuchElementException(String.format(NO_PERIODICAL_WITH_ID_IN_DB, periodicalId));
        }
    }

    private boolean hasUserNotEnoughPermissions(User currentUser, Periodical periodicalInDb) {
        return !Periodical.Status.ACTIVE.equals(periodicalInDb.getStatus())
                && !currentUser.hasRole(ADMIN_ROLE_NAME);
    }
}
