package com.coderdi.java.practice;

import java.lang.IllegalAccessException;
import java.lang.NoSuchMethodException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

class MethodHandleTest {
    private int methodCount;
    public static void main(String[] args) {
        MethodHandleTest methodHandleTest = new MethodHandleTest();
        try { 
            MethodHandle countAndRenderResult =
                    methodHandleTest.getMethod("countAndRenderResult", String.class);
            String firstOutput = (String) countAndRenderResult.invokeExact(methodHandleTest);
            System.out.println(firstOutput);
        } catch (NoSuchMethodException | IllegalAccessException | Throwable e) {
        }
    }

    public MethodHandleTest() {
        methodCount = 0;
    }

    private String countAndRenderResult() {
        return String.format("Current count is %d", ++methodCount);
    }

    private MethodHandle getMethod(
        String methodName, 
        Class<?> returnClass, 
        Class<?>... inputClasses) throws NoSuchMethodException, IllegalAccessException{
        MethodType methodType =
            inputClasses == null || inputClasses.length == 0 ? 
                MethodType.methodType(returnClass) :
                MethodType.methodType(returnClass, inputClasses);
        return MethodHandles.lookup().findVirtual(MethodHandleTest.class, methodName, methodType);
    }
}