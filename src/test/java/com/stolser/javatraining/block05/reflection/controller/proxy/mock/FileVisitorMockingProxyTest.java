package com.stolser.javatraining.block05.reflection.controller.proxy.mock;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.stolser.javatraining.block05.reflection.controller.proxy.mock.FileVisitorMockingProxy.FileVisitorMethod.*;

public class FileVisitorMockingProxyTest {
    private static final String START_PATH = "src\\main\\java\\com\\stolser" +
            "\\javatraining\\block05\\reflection\\controller";
    private FileVisitorMockingProxy proxy;

    @Before
    public void setUp() {
        proxy = new FileVisitorMockingProxy();
    }

    @Test
    public void walkFileTree_Should_TraverseWholeFileTree() throws Exception {
        createMockAndWalkFileTree();

        proxy.assertNumberOfCalls(VISIT_FILE, 10);
        proxy.assertNumberOfCalls(PRE_VISIT_DIRECTORY, 3);
        proxy.assertNumberOfCalls(POST_VISIT_DIRECTORY, 3);
    }

    @Test
    public void walkFileTree_Should_SkipDirIf_PreVisitDirectory_Returns_SkipSubtree()
            throws Exception {
        proxy.skipDirectory("mock");
        createMockAndWalkFileTree();

        proxy.assertNumberOfCalls(VISIT_FILE, 8);
        proxy.assertNumberOfCalls(PRE_VISIT_DIRECTORY, 3);
        proxy.assertNumberOfCalls(POST_VISIT_DIRECTORY, 2);
    }

    @Test
    public void walkFileTree_Should_TerminateImmediatelyIf_VisitFile_Returns_Terminate()
            throws Exception {
        proxy.setTerminateAfterFirstFile(true);
        createMockAndWalkFileTree();

        proxy.assertNumberOfCalls(VISIT_FILE, 1);
        proxy.assertNumberOfCalls(PRE_VISIT_DIRECTORY, 1);
        proxy.assertNumberOfCalls(POST_VISIT_DIRECTORY, 0);
    }

    private void createMockAndWalkFileTree() throws IOException {
        FileVisitor mock = proxy.mock();
        Path start = Paths.get(START_PATH);

        Files.walkFileTree(start, mock);
    }
}