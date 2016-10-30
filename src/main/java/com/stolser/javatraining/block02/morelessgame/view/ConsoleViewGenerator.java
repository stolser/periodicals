package com.stolser.javatraining.block02.morelessgame.view;

import com.stolser.javatraining.block02.morelessgame.model.menu.MenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class ConsoleViewGenerator implements ViewGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleViewGenerator.class);

    private StringBuilder builder;
    private ViewPrinter output;

    public ConsoleViewGenerator(ViewPrinter output) {
        this.output = output;
    }

    @Override
    public String getMenuView(MenuItem menu) {
        builder = new StringBuilder(String.format("------------ %s ------------\n",
                output.getMessageWithKey("generalMessages", "menu." + menu.getSystemName())));

        appendAllMenuItems(menu.getItems(), 0);
        appendMenuFooter();

        return builder.toString();
    }

    private void appendAllMenuItems(List<MenuItem> menu, int level) {
        for(MenuItem item: menu) {
            appendTabsBeforeMenuItem(level);
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

    private void appendMenuFooter() {
        builder.append("==============================\n");
    }

    private void appendTabsBeforeMenuItem(int level) {
        for(int i = 0; i < level; i++) {
            builder.append("\t");
        }
    }

//    @Override
//    public String getGameStatisticsView(Game game) {
//        String result = "";
//
//        return result;
//    }
}
