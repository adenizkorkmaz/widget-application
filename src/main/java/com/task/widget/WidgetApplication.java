package com.task.widget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration;

@SpringBootApplication(exclude = HypermediaAutoConfiguration.class)
public class WidgetApplication {
    public static void main(String[] args) {
        SpringApplication.run(WidgetApplication.class, args);
    }

}
