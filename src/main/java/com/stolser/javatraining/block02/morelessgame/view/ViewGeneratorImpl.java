package com.stolser.javatraining.block02.morelessgame.view;

import com.stolser.javatraining.block02.morelessgame.model.game.UserAttempt;
import com.stolser.javatraining.block02.morelessgame.model.menu.MenuItem;
import com.stolser.javatraining.view.ViewPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class ViewGeneratorImpl implements ViewGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewGeneratorImpl.class);
    private static final String STATISTICS_DATA_LAYOUT = "|\t%-5s|\t%-15s|\t%-15s|\t%-15s|\t%-15s|\n";

    /**
     * The name of a bundle for general messages.
     */
    private static final String GENERAL_MESSAGE_BUNDLE = "generalMessages";

    /**
     * The first part of the compound key name of the elements related to the menu.
     */
    private static final String MENU_KEY_PART = "menu.";
    private static final String GAME_STATISTICS_HEADER = "game.statisticsHeader";
    private static final String GAME_STATISTICS_HEADER_ATTEMPT_NO = "game.statisticsHeader.attemptNo";
    private static final String GAME_STATISTICS_HEADER_CURRENT_RANGE = "game.statisticsHeader.currentRange";
    private static final String GAME_STATISTICS_HEADER_NUMBER = "game.statisticsHeader.number";
    private static final String GAME_STATISTICS_HEADER_RESULT = "game.statisticsHeader.result";
    private static final String GAME_STATISTICS_HEADER_NEW_RANGE = "game.statisticsHeader.newRange";

    private ViewPrinter output;
    private StringBuilder builder;

    ViewGeneratorImpl(ViewPrinter output) {
        this.output = output;
    }

    @Override
    public String getMainMenuView(MenuItem mainMenu) {
        builder = new StringBuilder();

        appendMainMenuHeader(mainMenu);
        appendAllMenuItems(mainMenu.getItems(), 0);
        appendMenuFooter();

        return builder.toString();
    }

    private void appendMainMenuHeader(MenuItem menu) {
        builder.append(String.format("============ %s ============\n",
                output.getMessageWithKey(GENERAL_MESSAGE_BUNDLE, MENU_KEY_PART + menu.getSystemName())));
    }

    private void appendAllMenuItems(List<MenuItem> menu, int level) {
        LOGGER.debug("There are {} menu-items at the {} level.", menu.size(), level);

        for(MenuItem item: menu) {
            appendTabsBeforeThisMenuItem(level);
            if(item.isSubMenu()) {
                builder.append(String.format("- %s\n",
                        output.getMessageWithKey(GENERAL_MESSAGE_BUNDLE, MENU_KEY_PART + item.getSystemName())));
                appendAllMenuItems(item.getItems(), level + 1);

            } else {
                builder.append(String.format("- %s: %s\n",
                        output.getMessageWithKey(GENERAL_MESSAGE_BUNDLE, MENU_KEY_PART + item.getSystemName()),
                        item.getOptionId()));
            }
        }
    }

    private void appendTabsBeforeThisMenuItem(int level) {
        for(int i = 0; i < level; i++) {
            builder.append("\t");
        }
    }

    private void appendMenuFooter() {
        builder.append("===================================\n");
    }

    @Override
    public String getGameStatisticsView(List<UserAttempt> userAttempts) {
        builder = new StringBuilder();

        appendStatisticsHeader();
        appendStatisticsData(userAttempts);
        appendStatisticsFooter();

        return builder.toString();
    }

    private void appendStatisticsHeader() {
        builder.append(String.format("==================================== " +
                "%s ===================================\n",
                output.getMessageWithKey(GENERAL_MESSAGE_BUNDLE, GAME_STATISTICS_HEADER)));

        builder.append(String.format(STATISTICS_DATA_LAYOUT,
                output.getMessageWithKey(GENERAL_MESSAGE_BUNDLE, GAME_STATISTICS_HEADER_ATTEMPT_NO),
                output.getMessageWithKey(GENERAL_MESSAGE_BUNDLE, GAME_STATISTICS_HEADER_CURRENT_RANGE),
                output.getMessageWithKey(GENERAL_MESSAGE_BUNDLE, GAME_STATISTICS_HEADER_NUMBER),
                output.getMessageWithKey(GENERAL_MESSAGE_BUNDLE, GAME_STATISTICS_HEADER_RESULT),
                output.getMessageWithKey(GENERAL_MESSAGE_BUNDLE, GAME_STATISTICS_HEADER_NEW_RANGE)));
    }

    private void appendStatisticsData(List<UserAttempt> userAttempts) {
        for(UserAttempt attempt: userAttempts) {
            builder.append(String.format(STATISTICS_DATA_LAYOUT,
                    attempt.getSerialNo(),
                    attempt.getCurrentRange(),
                    attempt.getNumber(),
                    output.getMessageWithKey(GENERAL_MESSAGE_BUNDLE, attempt.getResult().toString()),
                    attempt.getNewRange()));
        }
    }

    private void appendStatisticsFooter() {
        builder.append("===================================================" +
                "=====================================");
    }
}
