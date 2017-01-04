package com.stolser.javatraining.webproject.controller.request.processor.admin.panel;

import com.stolser.javatraining.webproject.controller.request.processor.RequestProcessor;
import com.stolser.javatraining.webproject.model.entity.statistics.FinancialStatistics;
import com.stolser.javatraining.webproject.model.entity.statistics.PeriodicalNumberByCategory;
import com.stolser.javatraining.webproject.service.InvoiceService;
import com.stolser.javatraining.webproject.service.impl.InvoiceServiceImpl;
import com.stolser.javatraining.webproject.service.PeriodicalService;
import com.stolser.javatraining.webproject.service.impl.PeriodicalServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.ADMIN_PANEL_VIEW_NAME;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.FINANCIAL_STATISTICS_ATTR_NAME;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.PERIODICAL_STATISTICS_ATTR_NAME;

/**
 * Processes a GET request to the Admin Panel page.
 */
public class DisplayAdminPanel implements RequestProcessor {
    private PeriodicalService periodicalService = PeriodicalServiceImpl.getInstance();
    private InvoiceService invoiceService = InvoiceServiceImpl.getInstance();

    @Override
    public String process(HttpServletRequest request, HttpServletResponse response) {
        List<PeriodicalNumberByCategory> periodicalStatistics = periodicalService.getQuantitativeStatistics();

        Instant until = Instant.now();
        Instant since = until.minus(30, ChronoUnit.DAYS);
        FinancialStatistics finStatistics = invoiceService.getFinStatistics(since, until);

        request.setAttribute(PERIODICAL_STATISTICS_ATTR_NAME, periodicalStatistics);
        request.setAttribute(FINANCIAL_STATISTICS_ATTR_NAME, finStatistics);

        return ADMIN_PANEL_VIEW_NAME;
    }
}
