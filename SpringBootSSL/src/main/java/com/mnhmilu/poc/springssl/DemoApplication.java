package com.mnhmilu.poc.springssl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        try {
            SpringApplication.run(DemoApplication.class, args);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
