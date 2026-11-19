package com.wyn.web.tomcat;

import com.wyn.web.RestApplicationInitializer;
import com.wyn.web.RestWebServer;
import com.wyn.web.RestWebServerFactory;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.nio.file.Files;
import java.nio.file.Path;

public class TomcatRestWebServerFactory
        implements RestWebServerFactory {

    private int port = 8080;

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public RestWebServer getWebServer(
            RestApplicationInitializer initializer
    ) {

        try {
            Tomcat tomcat = new Tomcat();

            Path baseDir = Files.createTempDirectory(
                    "mini-spring-boot"
            );

            tomcat.setBaseDir(baseDir.toString());

            tomcat.setPort(port);
            tomcat.getConnector();

            Context context = tomcat.addContext(
                    "",
                    baseDir.toString()
            );

            context.setParentClassLoader(
                    Thread.currentThread().getContextClassLoader()
            );

            return new TomcatRestWebServer(
                    tomcat,
                    context,
                    initializer
            );

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Cannot create embedded Tomcat", e
            );
        }
    }
}
