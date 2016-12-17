package com.stolser.javatraining.webproject.controller.request_processor.admin;

import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.model.entity.statistics.FinancialStatistics;
import com.stolser.javatraining.webproject.model.entity.statistics.PeriodicalNumberByCategory;
import com.stolser.javatraining.webproject.model.service.invoice.InvoiceService;
import com.stolser.javatraining.webproject.model.service.invoice.InvoiceServiceImpl;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalService;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.ADMIN_PANEL_VIEW_NAME;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.FINANCIAL_STATISTICS_ATTR_NAME;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.PERIODICAL_STATISTICS_ATTR_NAME;

/**
 * Processes a request to the Admin Panel page.
 */
public class DisplayAdminPanel implements RequestProcessor {
    private PeriodicalService periodicalService = PeriodicalServiceImpl.getInstance();
    private InvoiceService invoiceService = InvoiceServiceImpl.getInstance();

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        List<PeriodicalNumberByCategory> periodicalStatistics = periodicalService.getQuantitativeStatistics();

        Instant until = Instant.now();
        Instant since = until.minus(30, ChronoUnit.DAYS);
        FinancialStatistics finStatistics = invoiceService.getFinStatistics(since, until);

        request.setAttribute(PERIODICAL_STATISTICS_ATTR_NAME, periodicalStatistics);
        request.setAttribute(FINANCIAL_STATISTICS_ATTR_NAME, finStatistics);

        return ADMIN_PANEL_VIEW_NAME;
    }
}
