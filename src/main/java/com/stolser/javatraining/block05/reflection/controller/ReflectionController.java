package com.stolser.javatraining.block05.reflection.controller;

import com.stolser.javatraining.block05.reflection.model.vehicle.Car;
import com.stolser.javatraining.block05.reflection.model.vehicle.Truck;
import com.stolser.javatraining.block05.reflection.model.vehicle.Vehicle;
import com.stolser.javatraining.view.ViewPrinter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.stolser.javatraining.controller.utils.ReflectionUtils.*;

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
        new InvokeController(output).invokeMethodsViaReflection(vehicle1);
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
        output.printlnString(String.format("Class's full name: %s", clazz.getName()));
        output.printlnString(String.format("Class's package name: %s", clazz.getPackage()));
        output.printlnString(String.format("Class's simple name: %s", getShortNameAsString(clazz.getName())));
        output.printlnString(String.format("Class's modifiers: %s", getModifiesAsString(clazz.getModifiers())));
        output.printlnString(String.format("Class's annotations: %s", Arrays.toString(clazz.getAnnotations())));
        output.printlnString(String.format("Superclass's full name: %s", clazz.getSuperclass().getName()));
        output.printlnString(String.format("Superclass's package name: %s", clazz.getSuperclass().getPackage()));
        output.printlnString(String.format("Superclass's simple name: %s",
                getShortNameAsString(clazz.getSuperclass().getName())));
    }

    private void printConstructors(Class clazz) {
        Constructor[] constructors = clazz.getConstructors();
        output.printlnString("------------ Constructors ------------");
        Arrays.stream(constructors)
                .forEach(constructor -> {
                    String name = getShortNameAsString(constructor.getName());
                    Class[] params = constructor.getParameterTypes();
                    String paramsString = String.join(", ", Arrays.stream(params)
                            .map(param -> getShortNameAsString(param.getName())).collect(Collectors.toList()));

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
            String annotations = getAnnotationsAsMultiLineString(method.getDeclaredAnnotations());
            String modifiers = getModifiesAsString(method.getModifiers());
            String returnTypeName = getShortNameAsString(method.getReturnType().getName());
            String methodName = getShortNameAsString(method.getName());
            String params = getParamsAsString(method.getParameterTypes());

            output.printlnString(String.format("%s%s %s %s(%s);", annotations, modifiers,
                    returnTypeName, methodName, params));
        });
    }

    private void printFields(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();

        output.printlnString("------------ Fields ------------");
        Arrays.stream(fields).forEach(field -> {
            String annotations = getAnnotationsAsMultiLineString(field.getDeclaredAnnotations());
            String modifiers = getModifiesAsString(field.getModifiers());
            String type = getShortNameAsString(field.getType().getName());
            String name = getShortNameAsString(field.getName());

            output.printlnString(String.format("%s%s %s %s", annotations, modifiers, type, name));
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
                output.printlnString(thisInterface.getName());
            });
        }
    }

}
