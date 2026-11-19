package com.wyn.web;

public interface RestWebServerFactory {
    RestWebServer getWebServer(
            RestApplicationInitializer initializer
    );
}
