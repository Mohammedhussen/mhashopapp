package com.muhe.myshop.common.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component

public class EmailService  {
	
	@Autowired
	JavaMailSender javamailsender;
	
	@Value("${support.email}")
	String supportemail;
	 private final Logger logger = LoggerFactory.getLogger(EmailService.class.getName());
	
	public void sendEmail(String to, String subject,String content )  {
		// prepare message using spring .....
		final MimeMessage mimemessage= this.javamailsender.createMimeMessage();
		final MimeMessageHelper message= new MimeMessageHelper(mimemessage,"UTF-8");
		try {
			message.setSubject(subject);
			message.setFrom(supportemail);
			message.setTo(to);
			message.setText(content,true /*isHTml*/);
			javamailsender.send(message.getMimeMessage());
			logger.info("Email was sent successfully  to ----"+subject);
		} catch (MailException|MessagingException e) {
			logger.info("Errorsending =======>"+e.getMessage());;
			//throw new MyshopException("Unable to send email");
			e.printStackTrace();
		}
		
	}

}
