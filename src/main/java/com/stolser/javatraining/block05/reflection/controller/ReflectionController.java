package com.stolser.javatraining.block05.reflection.controller;

import com.stolser.javatraining.block05.reflection.model.Car;
import com.stolser.javatraining.block05.reflection.model.Truck;
import com.stolser.javatraining.block05.reflection.model.Vehicle;
import com.stolser.javatraining.view.ViewPrinter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ReflectionController {
    private ViewPrinter output;

    public ReflectionController(ViewPrinter output) {
        this.output = output;
    }

    public void start() {
        Vehicle vehicle1 = new Car("BMW", 5, 420, Car.TransmissionType.AUTOMATIC);
        Vehicle vehicle2 = new Truck("MAN", 5000);

        getAndPrintInfoAbout(vehicle1);
        getAndPrintInfoAbout(vehicle2);
//        getAndPrintInfoAbout("Hello World");
    }

    private void getAndPrintInfoAbout(Object vehicle1) {
        Class clazz = vehicle1.getClass();

        output.printlnString("==================================================");
        printNameAndSuperclass(clazz);
        printConstructors(clazz);
        printMethods(clazz);
        printFields(clazz);
        printInterfaces(clazz);

        output.printlnString("==================================================");
    }

    private void printNameAndSuperclass(Class clazz) {
        output.printlnString(String.format("Class full name: %s", clazz.getName()));
        output.printlnString(String.format("Class simple name: %s", getShortName(clazz.getName())));
        output.printlnString(String.format("Superclass full name: %s", clazz.getSuperclass().getName()));
        output.printlnString(String.format("Superclass simple name: %s",
                getShortName(clazz.getSuperclass().getName())));
    }

    private void printConstructors(Class clazz) {
        Constructor[] constructors = clazz.getConstructors();
        output.printlnString("------------ Constructors ------------");
        Arrays.stream(constructors)
                .forEach(constructor -> {
                    String name = getShortName(constructor.getName());
                    Class[] params = constructor.getParameterTypes();
                    String paramsString = String.join(", ", Arrays.stream(params)
                            .map(param -> getShortName(param.getName())).collect(Collectors.toList()));

                    output.printlnString(String.format("%s(%s); number of params = %d",
                            name, paramsString, params.length));
                });
    }

    private void printMethods(Class clazz) {

        Method[] allPublicMethods = clazz.getMethods();
        Method[] allMethods = clazz.getDeclaredMethods();

        output.printlnString("------------ Methods ------------");
        output.printlnString(String.format("The number of public methods: %d", allPublicMethods.length));
        output.printlnString(String.format("The number of methods declared in this type: %d", allMethods.length));
        Arrays.stream(allMethods).forEach(method -> {
            Annotation[] annotations = method.getAnnotations();
            if (annotations.length > 0) {
                Arrays.stream(annotations)
                        .forEach(annotation -> {
                            // todo: annotations are printed incorrectly;
                            output.printlnString(String.format("\t@%s", annotation.getClass().getName()));
                        });
            }
            output.printlnString(String.format("%s", method));
        });
    }

    private String getShortName(String fullName) {
        String[] nameParts = fullName.split("\\.");

        return nameParts[nameParts.length - 1];
    }

    private void printFields(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();

        output.printlnString("------------ Fields ------------");
        Arrays.stream(fields).forEach(field -> {
            output.printlnString(field.toString());
        });

    }

    private void printInterfaces(Class clazz) {
        output.printlnString("------------ Interfaces ------------");

        printInterfacesOfThisType(clazz);
    }

    private void printInterfacesOfThisType(Class clazz) {
        if (clazz.getSuperclass() != null) {
            printInterfacesOfThisType(clazz.getSuperclass());

        }

        Class[] interfaces = clazz.getInterfaces();

        if (interfaces.length > 0) {
            Arrays.stream(interfaces).forEach(thisInterface -> {
                printInterfacesOfThisType(thisInterface);
                output.printlnString(thisInterface.toString());
            });
        }
    }
}
