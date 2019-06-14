package com.haulmont.testtask.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class WebAppPropertiesListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String rootPath = sce.getServletContext().getRealPath("/");
        System.setProperty("webroot", rootPath.substring(0 , rootPath.indexOf("target")));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}