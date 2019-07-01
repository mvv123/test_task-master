package com.haulmont.testtask.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class WebAppPropertiesListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String rootPath = getClass().getClassLoader().getResource("").getPath();
        System.setProperty("webroot" , rootPath.substring(1 , rootPath.length()));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}