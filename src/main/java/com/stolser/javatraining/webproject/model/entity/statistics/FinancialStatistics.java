package com.stolser.javatraining.webproject.model.entity.statistics;

public class FinancialStatistics {
    /**
     * The sum of all invoices that were created regardless whether they have been paid or not.
     */
    private long totalInvoiceSum;
    /**
     * The sum of all invoices that have been paid.
     */
    private long paidInvoiceSum;

    public FinancialStatistics(long totalInvoiceSum, long paidInvoiceSum) {
        this.totalInvoiceSum = totalInvoiceSum;
        this.paidInvoiceSum = paidInvoiceSum;
    }

    public long getTotalInvoiceSum() {
        return totalInvoiceSum;
    }

    public long getPaidInvoiceSum() {
        return paidInvoiceSum;
    }
}
