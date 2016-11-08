package com.stolser.javatraining.block02.morelessgame.view;

import com.stolser.javatraining.block02.morelessgame.model.UserAttempt;
import com.stolser.javatraining.block02.morelessgame.model.menu.MenuItem;
import com.stolser.javatraining.block04.recordbook.model.RecordBook;

import java.util.List;

/**
 * Implementations of this interface generate specific view presentations of different information blocks.
 */
public interface ViewGenerator {
    /**
     * @param menu an instance of MenuItem for which string representation will be generated.
     * @return a representation of this menu to display.
     */
    String getMainMenuView(MenuItem menu);

    /**
     * @param userAttempts a list of all UserAttempts of the last game.
     * @return a formatted string representation of a game statistics.
     */
    String getGameStatisticsView(List<UserAttempt> userAttempts);
}
