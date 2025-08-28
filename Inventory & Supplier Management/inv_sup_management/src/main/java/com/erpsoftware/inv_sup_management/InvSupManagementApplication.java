package com.erpsoftware.inv_sup_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
@SpringBootApplication
@RestController
public class InvSupManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvSupManagementApplication.class, args);
	}
	@GetMapping("/")
	public String index(){
		return "Hello world";
	}
}
