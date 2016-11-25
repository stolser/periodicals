package com.stolser.javatraining.webproject.controller.command.periodical;

import com.stolser.javatraining.webproject.controller.command.RequestCommand;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

import static java.lang.String.*;

public class DisplayOnePeriodical implements RequestCommand {
    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {
        String idString = request.getRequestURI().replace("/admin/periodicals/", "");
        long periodicalId = Integer.valueOf(idString);

        Periodical periodical = PeriodicalService.getInstance().findOne(periodicalId);
        System.out.println("found periodical: " + periodical);

        if (periodical == null) {
            throw new NoSuchElementException(format("There is no periodical with id = %d",
                    periodicalId));
        }

        request.setAttribute("periodical", periodical);

        return "/WEB-INF/admin/periodical/periodical.jsp";
    }
}
