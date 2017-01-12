package com.stolser.javatraining.webproject.service;

import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.statistics.PeriodicalNumberByCategory;

import java.util.List;

public interface PeriodicalService {

    Periodical findOneById(long id);

    Periodical findOneByName(String name);

    List<Periodical> findAll();

    List<Periodical> findAllByStatus(Periodical.Status status);

    /**
     * If the id of this periodical is 0, creates a new one. Otherwise tries to update
     * an existing periodical in the db with this id.
     * Use the returned instance for further operations as the save operation
     * might have changed the entity instance completely.
     *
     * @param periodical the persisted periodical
     * @return a periodical from the db
     */
    Periodical save(Periodical periodical);

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
     * @return the number of deleted periodicals
     */
    int deleteAllDiscarded();

    /**
     * @return true if there are subscriptions with status = 'active' on the specified periodical
     */
    boolean hasActiveSubscriptions(long periodicalId);

    List<PeriodicalNumberByCategory> getQuantitativeStatistics();

}
