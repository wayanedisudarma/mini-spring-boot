package com.wyn.web;

import com.wyn.context.RestApplicationContext;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

public class RestApplicationInitializer {

    private final RestApplicationContext applicationContext;

    public RestApplicationInitializer(
            RestApplicationContext applicationContext
    ) {
        this.applicationContext = applicationContext;
    }

    public void onStartup(ServletContext servletContext)
            throws ServletException {

        DispatcherServlet dispatcherServlet =
                new DispatcherServlet(
                        applicationContext.getControllerClasses()
                );

        var registration = servletContext.addServlet(
                "dispatcherServlet",
                dispatcherServlet
        );

        registration.addMapping("/");
        registration.setLoadOnStartup(1);
    }
}
