package com.muhe.myshop;


import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.muhe.myshop.common.service.EmailService;
import com.muhe.myshop.main.MyShopCoreApplication;





@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes= MyShopCoreApplication.class)
//@ComponentScan("com.muhe.myshop.service")
public class MyShopApplicationTest {
	
	@Autowired
	DataSource datasource;
	
	@Autowired
	EmailService emailService;
	

	
	
	@Test
	public void testDummy( ) throws SQLException
	{
		String schema= datasource.getConnection().getCatalog();
		assertEquals("myshop",schema);
	}
	
	@Test
	@Ignore
	public void  testSendEmail(){
		
		emailService.sendEmail("yabsiyo@yahoo.com", "Myshop -test email", "This is test email from myshop hi muhe how are you???");
			
		
	}
	
}
