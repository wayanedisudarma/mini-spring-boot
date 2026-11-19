package com.wyn.web.tomcat;

import com.wyn.web.RestApplicationInitializer;
import com.wyn.web.RestWebServer;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

public class TomcatRestWebServer implements RestWebServer {

    private final Tomcat tomcat;

    public TomcatRestWebServer(
            Tomcat tomcat,
            Context context,
            RestApplicationInitializer initializer
    ) {
        this.tomcat = tomcat;

        context.addServletContainerInitializer(
                (classes, servletContext) ->
                        initializer.onStartup(servletContext),
                null
        );
    }

    @Override
    public void start() {

        try {
            tomcat.start();
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Cannot start embedded Tomcat", e
            );
        }
    }

    @Override
    public void await() {
        tomcat.getServer().await();
    }

    @Override
    public void stop() {

        try {
            tomcat.stop();
            tomcat.destroy();
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Cannot stop embedded Tomcat", e
            );
        }
    }
}
