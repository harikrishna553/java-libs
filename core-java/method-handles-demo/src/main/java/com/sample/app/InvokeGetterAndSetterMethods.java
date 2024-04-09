package com.sample.app;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import com.sample.app.model.Employee;

public class InvokeGetterAndSetterMethods {
    public static void main(String[] args) throws Throwable {
        Employee emp = new Employee(1, "Krishna Gurram");
        System.out.println("emp : " + emp);

        // MethodHandles lookup
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        // Lookup the getter method
        MethodType getterMethodType = MethodType.methodType(String.class);
        String getterMethodName = "getName";
        MethodHandle getterMethodHandle = lookup.findVirtual(Employee.class, getterMethodName, getterMethodType);
        String value = (String) getterMethodHandle.invoke(emp);
        
        System.out.println("Value obtained through getter: " + value);

        // Lookup the setter method
        MethodType setterMethodType = MethodType.methodType(void.class, String.class);
        String setterMethodName = "setName";
        MethodHandle setterMethodHandle = lookup.findVirtual(Employee.class, setterMethodName, setterMethodType);

        // Invoke the setter method
        setterMethodHandle.invoke(emp, "Rama Krishna");
        System.out.println("Set the employee name to 'Rama Krishna'");
        
        System.out.println("emp : " + emp);
    }
}
