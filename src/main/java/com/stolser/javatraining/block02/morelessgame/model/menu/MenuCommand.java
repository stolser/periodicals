package com.stolser.javatraining.block02.morelessgame.model.menu;

/**
 * An interface that along with all classes implementing it is part of
 * the Command Design Pattern for the main menu. Each implementation represents
 * an actions that will be executed when a corresponding menu item is chosen by a user.
 */
public interface MenuCommand {
    void execute();
}
