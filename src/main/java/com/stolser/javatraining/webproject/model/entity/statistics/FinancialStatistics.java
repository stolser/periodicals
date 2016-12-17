package com.stolser.javatraining.webproject.model.entity.statistics;

public class FinancialStatistics {
    private long totalInvoiceSum;
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
