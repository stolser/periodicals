package com.stolser.javatraining.webproject.model.dao.periodical;

import com.stolser.javatraining.webproject.model.dao.GenericDao;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;

public interface PeriodicalDao extends GenericDao<Periodical> {
    Periodical findOneByName(String name);
    void deleteAllDiscarded();
}
