package com.stolser.javatraining.block02.morelessgame.view;

import com.stolser.javatraining.block02.morelessgame.model.UserAttempt;
import com.stolser.javatraining.block02.morelessgame.model.menu.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class ViewGeneratorImpl implements ViewGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewGeneratorImpl.class);
    private ViewPrinter output;
    private StringBuilder builder;
    private static final String STATISTICS_DATA_LAYOUT = "|\t%-5s|\t%-15s|\t%-15s|\t%-15s|\t%-15s|\n";

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
                output.getMessageWithKey("generalMessages", "menu." + menu.getSystemName())));
    }

    private void appendAllMenuItems(List<MenuItem> menu, int level) {
        LOGGER.debug("There are {} menu-items at the {} level.", menu.size(), level);

        for(MenuItem item: menu) {
            appendTabsBeforeThisMenuItem(level);
            if(item.isSubMenu()) {
                builder.append(String.format("- %s\n",
                        output.getMessageWithKey("generalMessages", "menu." + item.getSystemName())));
                appendAllMenuItems(item.getItems(), level + 1);

            } else {
                builder.append(String.format("- %s: %s\n",
                        output.getMessageWithKey("generalMessages", "menu." + item.getSystemName()),
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
                output.getMessageWithKey("generalMessages", "game.statisticsHeader")));

        builder.append(String.format(STATISTICS_DATA_LAYOUT,
                output.getMessageWithKey("generalMessages", "game.statisticsHeader.attemptNo"),
                output.getMessageWithKey("generalMessages", "game.statisticsHeader.currentRange"),
                output.getMessageWithKey("generalMessages", "game.statisticsHeader.number"),
                output.getMessageWithKey("generalMessages", "game.statisticsHeader.result"),
                output.getMessageWithKey("generalMessages", "game.statisticsHeader.newRange")));
    }

    private void appendStatisticsData(List<UserAttempt> userAttempts) {
        for(UserAttempt attempt: userAttempts) {
            builder.append(String.format(STATISTICS_DATA_LAYOUT,
                    attempt.getSerialNo(),
                    attempt.getCurrentRange(),
                    attempt.getNumber(),
                    output.getMessageWithKey("generalMessages", attempt.getResult().toString()),
                    attempt.getNewRange()));
        }
    }

    private void appendStatisticsFooter() {
        builder.append("===================================================" +
                "=====================================");
    }
}
