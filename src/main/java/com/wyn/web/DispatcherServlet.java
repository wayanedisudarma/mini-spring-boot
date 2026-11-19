package com.wyn.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyn.annotation.GetMapping;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DispatcherServlet extends HttpServlet {

    private final Map<String, HandlerMethod> handlers =
            new HashMap<>();

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    public DispatcherServlet(Set<Class<?>> controllerClasses) {
        registerHandlers(controllerClasses);
    }

    private void registerHandlers(
            Set<Class<?>> controllerClasses
    ) {

        for (Class<?> controllerClass : controllerClasses) {

            try {
                Object controller =
                        controllerClass.getDeclaredConstructor()
                                .newInstance();

                for (Method method :
                        controllerClass.getDeclaredMethods()) {

                    GetMapping mapping =
                            method.getAnnotation(GetMapping.class);

                    if (mapping == null) {
                        continue;
                    }

                    String path = mapping.value();

                    handlers.put(
                            "GET:" + path,
                            new HandlerMethod(controller, method)
                    );
                }

            } catch (Exception e) {
                throw new IllegalStateException(
                        "Cannot register controller: "
                                + controllerClass.getName(),
                        e
                );
            }
        }
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        String path = request.getRequestURI()
                .substring(request.getContextPath().length());
        System.out.println(path);

        HandlerMethod handler =
                handlers.get("GET:" + path);

        if (handler == null) {
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND
            );
            return;
        }

        try {
            Object result = handler.method().invoke(
                    handler.controller()
            );

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            objectMapper.writeValue(
                    response.getOutputStream(),
                    result
            );

        } catch (Exception e) {
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );
        }
    }

    private record HandlerMethod(
            Object controller,
            Method method
    ) {}
}
