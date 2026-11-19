package com.wyn.scanner;

import com.wyn.annotation.RestController;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.stream.Collectors;

public class ControllerScanner {
    public Set<Class<?>> scan(String basePackage) {

        try (ScanResult result = new ClassGraph()
                .enableClassInfo()
                .enableAnnotationInfo()
                .acceptPackages(basePackage)
                .scan()) {

            return result
                    .getClassesWithAnnotation(
                            RestController.class.getName()
                    )
                    .loadClasses()
                    .stream()
                    .filter(type -> !type.isInterface())
                    .filter(type -> !Modifier.isAbstract(
                            type.getModifiers()
                    ))
                    .collect(Collectors.toSet());
        }
    }
}
