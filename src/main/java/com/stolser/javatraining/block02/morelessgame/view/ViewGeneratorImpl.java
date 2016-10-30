package com.stolser.javatraining.block02.morelessgame.view;

import com.stolser.javatraining.block02.morelessgame.model.UserAttempt;
import com.stolser.javatraining.block02.morelessgame.model.menu.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class ViewGeneratorImpl implements ViewGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewGeneratorImpl.class);
    private ViewPrinter output;

    ViewGeneratorImpl(ViewPrinter output) {
        this.output = output;
    }

    @Override
    public String getMenuView(MenuItem menu) {
        StringBuilder builder = new StringBuilder(String.format("============ %s ============\n",
                output.getMessageWithKey("generalMessages", "menu." + menu.getSystemName())));

        appendAllMenuItems(menu.getItems(), 0, builder);
        appendMenuFooter(builder);

        return builder.toString();
    }

    private void appendAllMenuItems(List<MenuItem> menu, int level, StringBuilder builder) {
        for(MenuItem item: menu) {
            appendTabsBeforeMenuItem(level, builder);
            if(item.isSubMenu()) {
                builder.append(String.format("- %s\n",
                        output.getMessageWithKey("generalMessages", "menu." + item.getSystemName())));
                appendAllMenuItems(item.getItems(), level + 1, builder);

            } else {
                builder.append(String.format("- %s: %s\n",
                        output.getMessageWithKey("generalMessages", "menu." + item.getSystemName()),
                        item.getOptionId()));
            }
        }
    }

    private void appendMenuFooter(StringBuilder builder) {
        builder.append("===================================\n");
    }

    private void appendTabsBeforeMenuItem(int level, StringBuilder builder) {
        for(int i = 0; i < level; i++) {
            builder.append("\t");
        }
    }

    @Override
    public String getGameStatisticsView(List<UserAttempt> userAttempts) {
        StringBuilder builder = new StringBuilder(String.format("==================================== " +
                "%s ===================================\n",
                output.getMessageWithKey("generalMessages", "game.statisticsHeader")));

        builder.append(String.format("|\t%-5s|\t%-15s|\t%-15s|\t%-15s|\t%-15s|\n",
                output.getMessageWithKey("generalMessages", "game.statisticsHeader.attemptNo"),
                output.getMessageWithKey("generalMessages", "game.statisticsHeader.currentRange"),
                output.getMessageWithKey("generalMessages", "game.statisticsHeader.number"),
                output.getMessageWithKey("generalMessages", "game.statisticsHeader.result"),
                output.getMessageWithKey("generalMessages", "game.statisticsHeader.newRange")));

        for(UserAttempt attempt: userAttempts) {
            builder.append(String.format("|\t%-5s|\t%-15s|\t%-15s|\t%-15s|\t%-15s|\n",
                    attempt.getSerialNo(),
                    attempt.getCurrentRange(),
                    attempt.getNumber(),
                    attempt.getResult(),
                    attempt.getNewRange()));
        }

        builder.append("===================================================" +
                "=====================================");

        return builder.toString();
    }
}
