package com.overji.ebookbackend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EBookBackendApplication {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EBookBackendApplication.class);
    public EBookBackendApplication() {
        logger.info("EBookBackendApplication started");
    }

    public static void main(String[] args) {
        SpringApplication.run(EBookBackendApplication.class, args);
    }
}