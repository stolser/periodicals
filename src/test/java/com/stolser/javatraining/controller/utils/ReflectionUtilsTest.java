package com.stolser.javatraining.controller.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static com.stolser.javatraining.controller.utils.ReflectionUtils.*;
import static com.stolser.javatraining.controller.utils.ReflectionUtils.getShortNameAsString;
import static org.junit.Assert.assertEquals;

public class ReflectionUtilsTest {
    private Class<?>[] methodAParamTypes;

    @Before
    public void setUp() {
        methodAParamTypes = new Class<?>[]{int.class, double.class, Boolean.class, ReflectionUtilsTest.class};
    }

    @Test
    public void getShortNameAsStringShouldReturnTypeNameWithoutPackageName() throws Exception {
        assertEquals("ClassName", getShortNameAsString("com.s_toler@urk.net.Package.ClassName"));
    }

    @Test
    public void getModifiesAsStringShouldReturnCorrectString() throws Exception {
        int modifiers = ReflectionUtilsTest.class.getMethod("methodA", methodAParamTypes)
                .getModifiers();

        assertEquals("public final synchronized", getModifiesAsString(modifiers));
    }

    @Test
    public void getParamsAsStringShouldReturnCorrectString() throws Exception {
        Class<?>[] parameterTypes = ReflectionUtilsTest.class.getMethod("methodA", methodAParamTypes)
                .getParameterTypes();

        String expected =String.join(PARAMS_DELIMITER, Arrays.asList(new String[]
                {"int", "double", "Boolean", "ReflectionUtilsTest"}));
        String actual = getParamsAsString(parameterTypes);

        assertEquals(expected, actual);
    }

    public final synchronized void methodA(int param1, double param2,
                                           Boolean param3, ReflectionUtilsTest param4) {}
}