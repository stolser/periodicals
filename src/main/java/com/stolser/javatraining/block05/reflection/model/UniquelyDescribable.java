package com.stolser.javatraining.block05.reflection.model;

/**
 * Represents any kind of entity that has a unique identifier and can be described.
 */
public interface UniquelyDescribable {
    /**
     * @return a unique identifier of this car.
     */
    int getUid();
    String getBrand();
    String getDescription();
    void setDescription(String description);
}
