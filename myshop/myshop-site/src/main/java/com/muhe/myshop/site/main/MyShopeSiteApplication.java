package com.muhe.myshop.site.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="{com.muhe.myshop.*}")
	

public class MyShopeSiteApplication {

	public static void main(String args[]) {
		SpringApplication.run(MyShopeSiteApplication.class, args);
		
	}
}
