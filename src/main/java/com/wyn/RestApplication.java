package com.wyn;

import com.wyn.context.RestApplicationContext;
import com.wyn.web.RestApplicationInitializer;
import com.wyn.web.RestWebServer;
import com.wyn.web.RestWebServerFactory;
import com.wyn.web.tomcat.TomcatRestWebServerFactory;

public class RestApplication {
    public static void run(
            Class<?> primarySource
    ) {

        // 1. Create application context
        RestApplicationContext applicationContext =
                new RestApplicationContext(primarySource);

        // 2. Scan application components
        applicationContext.refresh();

        // 3. Create servlet initializer
        RestApplicationInitializer initializer =
                new RestApplicationInitializer(applicationContext);

        // 4. Create web server factory
        RestWebServerFactory factory =
                new TomcatRestWebServerFactory();

        // 5. Create web server
        RestWebServer webServer =
                factory.getWebServer(initializer);

        // 6. Start web server
        webServer.start();

        System.out.println(
                "Application started on http://localhost:8080"
        );

        // 7. Keep application running
        webServer.await();
    }
}
