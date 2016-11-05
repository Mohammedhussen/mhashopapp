package com.muhe.myshop.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@ComponentScan
//@EnableAutoConfiguration
@SpringBootApplication (scanBasePackages={"com.muhe.myshop.*"})
public class MyShopCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyShopCoreApplication.class,args);
		

	}

}
