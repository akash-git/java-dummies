package org;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@Profile(value = {"development", "production"})
public class SprintMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(SprintMainApplication.class, args);
    }

}

