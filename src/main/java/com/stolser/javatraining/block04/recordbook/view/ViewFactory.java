package com.stolser.javatraining.block04.recordbook.view;

import com.stolser.javatraining.view.ViewPrinter;

/**
 * An abstraction of a factory that allow client to get concrete implementations for generating and
 * displaying localized information.
 */
public interface ViewFactory {
    ViewGenerator getViewGenerator();
    ViewPrinter getViewPrinter();
}
