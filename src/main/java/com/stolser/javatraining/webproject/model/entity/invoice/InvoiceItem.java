package com.stolser.javatraining.webproject.model.entity.invoice;

import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;

import java.util.Date;

public class InvoiceItem {
    private Periodical periodical;
    private Date endDate;

    public Periodical getPeriodical() {
        return periodical;
    }

    public void setPeriodical(Periodical periodical) {
        this.periodical = periodical;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
