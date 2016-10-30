package com.stolser.javatraining.block02.morelessgame.view;

import com.stolser.javatraining.block02.morelessgame.model.Game;
import com.stolser.javatraining.block02.morelessgame.model.UserAttempt;
import com.stolser.javatraining.block02.morelessgame.model.menu.MenuItem;

import java.util.List;

public interface ViewGenerator {
    String getMenuView(MenuItem menu);
    String getGameStatisticsView(List<UserAttempt> userAttempts);
}
