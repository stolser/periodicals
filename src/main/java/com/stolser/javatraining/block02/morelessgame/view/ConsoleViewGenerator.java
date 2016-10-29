package com.stolser.javatraining.block02.morelessgame.view;

import com.stolser.javatraining.block02.morelessgame.model.menu.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ResourceBundle;

class ConsoleViewGenerator implements ViewGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleViewGenerator.class);

    private StringBuilder builder;
    private ViewPrinter output;
    private ResourceBundle generalMessages;

    public ConsoleViewGenerator(ViewPrinter output) {
        this.output = output;
    }

    @Override
    public String getMenuView(Menu menu) {
        generalMessages = ResourceBundle.getBundle("generalMessages", output.getLocale());
        builder = new StringBuilder(menu.getSystemName() + ":\n");

        appendItems(menu.getItems(), 0);
        builder.append(generalMessages.getString("menu.makeachoice"));

//        LOGGER.debug("getMenuView: " + builder.toString());

        return builder.toString();
    }

    private void appendItems(List<Menu> menu, int level) {
        for(Menu item: menu) {
            appendTabs(level);
            if(item.isSubMenu()) {
                builder.append("- " + generalMessages.getString("menu." + item.getSystemName()) + "\n");
                appendItems(item.getItems(), level + 1);
            } else {
                builder.append("- " + generalMessages.getString("menu." + item.getSystemName())
                        + ": " + item.getOptionId() + "\n");
            }
        }
    }

    private void appendTabs(int level) {
        for(int i = 0; i < level; i++) {
            builder.append("\t");
        }
    }

//    @Override
//    public String getGameStatisticsView(Game game) {
//        generalMessages = ResourceBundle.getBundle("generalMessages", output.getLocale());
//        String result = "";
//
//        return result;
//    }
}
