package com.overji.ebookbackend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EBookBackendApplication {
    public EBookBackendApplication() {
        System.out.println("Start!");
    }

    public static void main(String[] args) {
        SpringApplication.run(EBookBackendApplication.class, args);
    }
}