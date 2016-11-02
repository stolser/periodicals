package com.stolser.javatraining.block02.morelessgame.view;

import com.stolser.javatraining.block02.morelessgame.model.UserAttempt;
import com.stolser.javatraining.block02.morelessgame.model.menu.MenuItem;

import java.util.List;

/**
 * Implementations of this interface generate specific view presentations of different information blocks.
 */
public interface ViewGenerator {
    String getMainMenuView(MenuItem menu);
    String getGameStatisticsView(List<UserAttempt> userAttempts);
}
