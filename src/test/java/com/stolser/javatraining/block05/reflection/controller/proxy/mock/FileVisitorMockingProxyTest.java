package com.stolser.javatraining.block05.reflection.controller.proxy.mock;

import org.junit.Test;

import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.stolser.javatraining.block05.reflection.controller.proxy.mock.FileVisitorMockingProxy.FileVisitorMethod.*;

public class FileVisitorMockingProxyTest {

    @Test
    public void assertNumberOfCallsShouldWorkCorrectly() throws Exception {
        FileVisitorMockingProxy proxy = new FileVisitorMockingProxy();
        FileVisitor mock = proxy.mock();
        Path start = Paths.get("src\\main\\java\\com\\stolser" +
                "\\javatraining\\block05\\reflection\\controller");

        Files.walkFileTree(start, mock);

        proxy.assertNumberOfCalls(VISIT_FILE, 10);
        proxy.assertNumberOfCalls(PRE_VISIT_DIRECTORY, 3);
        proxy.assertNumberOfCalls(POST_VISIT_DIRECTORY, 3);
    }
}