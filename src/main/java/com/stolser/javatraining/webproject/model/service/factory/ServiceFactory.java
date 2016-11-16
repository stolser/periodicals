package com.stolser.javatraining.webproject.model.service.factory;

import com.stolser.javatraining.webproject.model.service.dbsetup.DbSetupService;
import com.stolser.javatraining.webproject.model.service.login.LoginService;

public interface ServiceFactory {
    DbSetupService getDbSetupService();
    LoginService getLoginService();

}
