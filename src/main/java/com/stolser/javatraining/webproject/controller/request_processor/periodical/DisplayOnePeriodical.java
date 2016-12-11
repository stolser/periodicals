package com.stolser.javatraining.webproject.controller.request_processor.periodical;

import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.user.User;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public class DisplayOnePeriodical implements RequestProcessor {

    private static final String NO_PERIODICAL_WITH_ID_IN_DB = "There is no periodical with id %d in the db.";

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        User thisUser = HttpUtils.getCurrentUserFromFromDb(request);
        long periodicalId = HttpUtils.getFirstIdFromUri(request.getRequestURI());
        Periodical periodical = PeriodicalService.getInstance().findOneById(periodicalId);

        if (periodical == null) {
            throw new NoSuchElementException(String.format(NO_PERIODICAL_WITH_ID_IN_DB, periodicalId));
        } else if(!Periodical.Status.VISIBLE.equals(periodical.getStatus())
                && !thisUser.hasRole(ADMIN_ROLE_NAME)) {
            HttpUtils.sendRedirect(request, response, ACCESS_DENIED_HRF);
            return null;
        }

        System.out.println("found periodical: " + periodical);

        request.setAttribute(PERIODICAL_ATTR_NAME, periodical);

        return ONE_PERIODICAL_VIEW_NAME;
    }
}
