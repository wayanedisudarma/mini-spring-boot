package com.wyn.context;

import com.wyn.scanner.ControllerScanner;

import java.util.Set;

public class RestApplicationContext {
    private final Class<?> primarySource;

    private Set<Class<?>> controllerClasses;

    public RestApplicationContext(Class<?> primarySource) {
        this.primarySource = primarySource;
    }

    public void refresh() {

        String basePackage =
                primarySource.getPackageName();

        ControllerScanner scanner =
                new ControllerScanner();

        controllerClasses = scanner.scan(basePackage);
    }

    public Set<Class<?>> getControllerClasses() {
        return controllerClasses;
    }
}
