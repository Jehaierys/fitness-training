package com.fitnesstraining;

import com.fitnesstraining.config.DataConfig;
import com.fitnesstraining.config.RootConfig;
import com.fitnesstraining.config.web.WebMvcConfig;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;


public class Application {

    public static void main(String[] args) throws Exception {

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        StandardContext context = (StandardContext) tomcat.addContext("", new File(".").getAbsolutePath());

        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();

        applicationContext.register(
                RootConfig.class,
                WebMvcConfig.class,
                DataConfig.class
        );

        DispatcherServlet dispatcherServlet =
                new DispatcherServlet(applicationContext);

        Tomcat.addServlet(context, "dispatcher", dispatcherServlet);

        context.addServletMappingDecoded("/", "dispatcher");

        tomcat.start();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Shalom");
        System.out.println();

        tomcat.getServer().await();
    }
}