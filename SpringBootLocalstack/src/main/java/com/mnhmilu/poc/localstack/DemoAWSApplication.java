package com.mnhmilu.poc.localstack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DemoAWSApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        try {
            SpringApplication.run(DemoAWSApplication.class, args);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
