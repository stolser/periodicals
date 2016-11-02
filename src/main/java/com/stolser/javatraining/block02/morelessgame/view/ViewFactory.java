package com.stolser.javatraining.block02.morelessgame.view;

/**
 * An abstraction of a factory that allow client to get concrete implementations for generating and
 * displaying localized information.
 */
public interface ViewFactory {
    ViewGenerator getViewGenerator();
    ViewPrinter getViewPrinter();
}
