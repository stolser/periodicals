package com.stolser.javatraining.block05.reflection.controller.proxy.mock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.util.HashMap;
import java.util.Map;

import static com.stolser.javatraining.block05.reflection.controller.proxy.mock.FileVisitorMockingProxy.FileVisitorMethod.*;
import static com.stolser.javatraining.controller.utils.ReflectionUtils.getShortNameAsString;

/**
 * Mocks {@link FileVisitor} and allows to check how many times
 * each method of that interface has been called.
 */
public class FileVisitorMockingProxy implements InvocationHandler {
    private Map<FileVisitorMethod, Integer> counter = getInitialMap();

    enum FileVisitorMethod {
        VISIT_FILE,
        PRE_VISIT_DIRECTORY,
        POST_VISIT_DIRECTORY,
        VISIT_FILE_FAILED;

        static FileVisitorMethod getByName(String name) {
            FileVisitorMethod result;

            switch (name) {
                case "visitFile":
                    result = VISIT_FILE;
                    break;
                case "preVisitDirectory":
                    result = PRE_VISIT_DIRECTORY;
                    break;
                case "postVisitDirectory":
                    result = POST_VISIT_DIRECTORY;
                    break;
                case "visitFileFailed":
                    result = VISIT_FILE_FAILED;
                    break;
                default:
                    throw new AssertionError("Should not get here");
            }

            return result;
        }
    }

    /**
     * Asserts that a specified method of the {@code FileVisitor} interface
     * has been called some number of times. If the actual number differs from the number passed
     * as a parameter then a {@link ComparisonFailure} is thrown.
     * @param method represents a method of the {@code FileVisitor} interface
     * @param number the expected number of time this method has been called
     */
    public void assertNumberOfCalls(FileVisitorMethod method, int number) {
        int expectedValue = number;
        int actualValue = counter.get(method);

        if (expectedValue != actualValue) {
            throw new ComparisonFailure(expectedValue, actualValue);
        }
    }

    /**
     * @return an instance of a proxy class that implements the {@code FileVisitor} interface
     */
    public FileVisitor mock() {
        return (FileVisitor) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[]{FileVisitor.class},
                this);
    }

    /**
     * Using the Reflection API counts how many times each method of the {@code FileVisitor} interface
     * has been called on the object returned by {@link FileVisitorMockingProxy#mock()}.
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = getShortNameAsString(method.getName());
        FileVisitorMethod fileVisitorMethod = FileVisitorMethod.getByName(methodName);
        int oldCounter = counter.get(fileVisitorMethod);
        counter.put(fileVisitorMethod, ++oldCounter);

        return FileVisitResult.CONTINUE;
    }

    private HashMap<FileVisitorMethod, Integer> getInitialMap() {
        return new HashMap<FileVisitorMethod, Integer>() {
            {
                put(VISIT_FILE, 0);
                put(PRE_VISIT_DIRECTORY, 0);
                put(POST_VISIT_DIRECTORY, 0);
                put(VISIT_FILE_FAILED, 0);
            }
        };
    }
}
