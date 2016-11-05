package com.muhe.myshop.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;




//@Configuration
//@ComponentScan
//@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages={"com.muhe.myshop.*"})
@EntityScan("com.muhe.myshop.*")  
public class MyShopAdminApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(MyShopAdminApplication.class,args);
	}

}
