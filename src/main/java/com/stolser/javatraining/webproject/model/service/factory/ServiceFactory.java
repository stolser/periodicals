package com.stolser.javatraining.webproject.model.service.factory;

import com.stolser.javatraining.webproject.model.service.dbsetup.DbSetupService;

public interface ServiceFactory {
    DbSetupService getDbSetupService();

}
