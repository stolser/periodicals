package com.stolser.javatraining.webproject.model.service.periodical;

import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.entity.periodical.statistics.NumberByCategory;

import java.util.List;

public interface PeriodicalService {

    Periodical findOneById(long id);

    Periodical findOneByName(String name);

    List<Periodical> findAll();

    List<Periodical> findAllByStatus(Periodical.Status status);

    Periodical save(Periodical periodical);

    int updateAndSetDiscarded(Periodical periodical);

    void deleteAllDiscarded();

    boolean hasActiveSubscriptions(long periodicalId);

    List<NumberByCategory> getQuantitativeStatistics();

}
