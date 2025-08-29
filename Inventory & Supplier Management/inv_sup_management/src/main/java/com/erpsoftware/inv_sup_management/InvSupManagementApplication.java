package com.erpsoftware.inv_sup_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
@SpringBootApplication
@RestController
public class InvSupManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(InvSupManagementApplication.class, args);
	}
}
