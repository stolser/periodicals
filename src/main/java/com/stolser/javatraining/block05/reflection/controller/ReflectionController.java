package com.stolser.javatraining.block05.reflection.controller;

import com.stolser.javatraining.block05.reflection.controller.proxy.ProxyFactory;
import com.stolser.javatraining.block05.reflection.model.vehicle.Car;
import com.stolser.javatraining.block05.reflection.model.vehicle.Motorizeable;
import com.stolser.javatraining.block05.reflection.model.vehicle.Truck;
import com.stolser.javatraining.block05.reflection.model.vehicle.Vehicle;
import com.stolser.javatraining.view.ViewPrinter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.stolser.javatraining.block05.reflection.model.vehicle.Car.TransmissionType.AUTOMATIC;
import static com.stolser.javatraining.controller.utils.ReflectionUtils.*;

/**
 * The main controller for displaying info about objects using the Reflection API.
 */
public class ReflectionController {
    private static final String CLASS_FULL_NAME_TEXT = "Class's full name: %s";
    private static final String CLASS_PACKAGE_NAME_TEXT = "Class's package name: %s";
    private static final String CLASS_SIMPLE_NAME_TEXT = "Class's simple name: %s";
    private static final String CLASS_MODIFIERS_TEXT = "Class's modifiers: %s";
    private static final String CLASS_ANNOTATIONS_TEXT = "Class's annotations: %s";
    private static final String SUPERCLASS_FULL_NAME_TEXT = "Superclass's full name: %s";
    private static final String SUPERCLASS_PACKAGE_NAME_TEXT = "Superclass's package name: %s";
    private static final String SUPERCLASS_SIMPLE_NAME_TEXT = "Superclass's simple name: %s";
    private static final String CONSTRUCTORS_TITLE = "------------ Constructors ------------";
    private static final String METHODS_TITLE = "------------ Methods ------------";
    private static final String FIELDS_TITLE = "------------ Fields ------------";
    private static final String INTERFACES_TITLE = "------------ Interfaces ------------";
    private static final String METHOD_SEPARATOR_STRING = "==================================================";

    private ViewPrinter output;
    private Class clazz;

    public ReflectionController(ViewPrinter output) {
        this.output = output;
    }

    /**
     * Creates some variables and display info their types and members.
     */
    public void start() {
        /*Replace '5' with '-5' to check the NotNegative annotation in action.
        Checking this argument in the constructor is deliberately commented.*/
        Vehicle vehicle1 = new Car("BMW", 5, 420, AUTOMATIC);
        Vehicle vehicle2 = new Truck("MAN", 5000);

        getAndPrintInfoAbout(vehicle1);
        getAndPrintInfoAbout(vehicle2);

        Vehicle invokable1 = (Vehicle) ProxyFactory.newInvokableProxyFor(vehicle1);
        runVehicle(invokable1);

        Vehicle notNegative1 = (Vehicle) ProxyFactory.newNotNegativeProxyFor(vehicle1);
        runVehicle(notNegative1);

        Motorizeable immutable = (Motorizeable) ProxyFactory.newImmutableProxyFor(vehicle1);
        System.out.printf("The number of cylinders: %d\n", immutable.getCylinderNumber());
        System.out.printf("The power in hps: %d\n", immutable.getPower());
        System.out.printf("The transmission type: %s\n", immutable.getTransType());
        immutable.setPower(500);    // throws an exception!
    }

    private void runVehicle(Vehicle vehicle) {
        System.out.printf("Running vehicle: %s\n", vehicle);
        vehicle.getMaxSpeed();
        vehicle.accelerate(1000);
        vehicle.brake(500);
        vehicle.getCurrentSpeed();
        vehicle.accelerate(500);
        vehicle.brake(200);
        vehicle.getCurrentSpeed();
        vehicle.moveLeft(10);
        vehicle.moveRight(30);
        vehicle.getCurrentSpeed();

    }

    private void getAndPrintInfoAbout(Object vehicle1) {
        clazz = vehicle1.getClass();

        printMethodInfoSeparator();

        printNameAndSuperclass();
        printConstructors();
        printMethods();
        printFields();
        printInterfaces();

        printMethodInfoSeparator();
    }

    private void printNameAndSuperclass() {
        output.printlnString(String.format(CLASS_FULL_NAME_TEXT, clazz.getName()));
        output.printlnString(String.format(CLASS_PACKAGE_NAME_TEXT, clazz.getPackage()));
        output.printlnString(String.format(CLASS_SIMPLE_NAME_TEXT, getShortNameAsString(clazz.getName())));
        output.printlnString(String.format(CLASS_MODIFIERS_TEXT, getModifiesAsString(clazz.getModifiers())));
        output.printlnString(String.format(CLASS_ANNOTATIONS_TEXT, Arrays.toString(clazz.getAnnotations())));
        output.printlnString(String.format(SUPERCLASS_FULL_NAME_TEXT, clazz.getSuperclass().getName()));
        output.printlnString(String.format(SUPERCLASS_PACKAGE_NAME_TEXT, clazz.getSuperclass().getPackage()));
        output.printlnString(String.format(SUPERCLASS_SIMPLE_NAME_TEXT,
                getShortNameAsString(clazz.getSuperclass().getName())));
    }

    private void printConstructors() {
        Constructor[] constructors = clazz.getConstructors();

        output.printlnString(CONSTRUCTORS_TITLE);
        Arrays.stream(constructors)
                .forEach(constructor -> {
                    String name = getShortNameAsString(constructor.getName());
                    Class[] params = constructor.getParameterTypes();
                    String paramsString = String.join(PARAMS_DELIMITER, Arrays.stream(params)
                            .map(param -> getShortNameAsString(param.getName()))
                            .collect(Collectors.toList()));

                    output.printlnString(String.format("%s(%s); number of params = %d",
                            name, paramsString, params.length));
                });
    }

    private void printMethods() {
        Method[] allPublicMethods = clazz.getMethods();
        Method[] allMethods = clazz.getDeclaredMethods();

        output.printlnString(METHODS_TITLE);
        output.printlnString(String.format("The number of public methods: %d", allPublicMethods.length));
        output.printlnString(String.format("The number of methods declared in this type: %d", allMethods.length));

        for (Method method : allMethods) {
            String annotations = getAnnotationsAsMultiLineString(method.getDeclaredAnnotations());
            String modifiers = getModifiesAsString(method.getModifiers());
            String returnTypeName = getShortNameAsString(method.getReturnType().getName());
            String methodName = getShortNameAsString(method.getName());
            String params = getParamsAsString(method.getParameterTypes());

            output.printlnString(String.format("%s%s %s %s(%s);", annotations, modifiers,
                    returnTypeName, methodName, params));
        }
    }

    private void printFields() {
        Field[] fields = clazz.getDeclaredFields();

        output.printlnString(FIELDS_TITLE);
        Arrays.stream(fields).forEach(field -> {
            String annotations = getAnnotationsAsMultiLineString(field.getDeclaredAnnotations());
            String modifiers = getModifiesAsString(field.getModifiers());
            String type = getShortNameAsString(field.getType().getName());
            String name = getShortNameAsString(field.getName());

            output.printlnString(String.format("%s%s %s %s", annotations, modifiers, type, name));
        });

    }

    private void printInterfaces() {
        output.printlnString(INTERFACES_TITLE);

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
                output.printlnString(thisInterface.getName());
            });
        }
    }

    private void printMethodInfoSeparator() {
        output.printlnString(METHOD_SEPARATOR_STRING);
    }
}
