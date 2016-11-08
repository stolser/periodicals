package com.stolser.javatraining.block04.recordbook.view;

import com.stolser.javatraining.block04.recordbook.model.RecordBook;

/**
 * Implementations of this interface generate specific view presentations<br />
 * of record books.
 */
public interface ViewGenerator {
    /**
     * @param recordBook contains records which info will be displayed
     * @return a formatted string representation all records in this RecordBook
     */
    String getRecordBookView(RecordBook recordBook);
}
