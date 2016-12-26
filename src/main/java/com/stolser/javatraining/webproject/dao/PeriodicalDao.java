package com.stolser.javatraining.webproject.dao;

import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.periodical.PeriodicalCategory;

import java.util.List;

public interface PeriodicalDao extends GenericDao<Periodical> {
    Periodical findOneByName(String name);

    List<Periodical> findAllByStatus(Periodical.Status status);

    int findNumberOfPeriodicalsWithCategoryAndStatus(PeriodicalCategory category, Periodical.Status status);

    /**
     * Updates a periodical and sets a new status 'discarded' only if there is no active subscriptions
     * of this periodical.
     *
     * @return the number of affected rows: 0 - if the condition was not satisfied and updated
     * has not happened; 1 - if the status of this periodical has been changed to 'discarded'
     */
    int updateAndSetDiscarded(Periodical periodical);

    /**
     * Deletes from the db all periodicals with status = 'discarded'.
     */
    void deleteAllDiscarded();
}
