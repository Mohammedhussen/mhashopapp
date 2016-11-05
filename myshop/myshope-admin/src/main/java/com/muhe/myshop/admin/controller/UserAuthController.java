package com.muhe.myshop.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.muhe.myshop.admin.service.SecurityService;
import static com.muhe.myshop.admin.util.MessageCodes.*;

import com.muhe.myshop.admin.util.MessageCodes;
import com.muhe.myshop.admin.util.WebUtility;
import com.muhe.myshop.common.service.EmailService;
import com.muhe.myshop.common.service.MyshopException;


@Controller
@ComponentScan
public class UserAuthController extends MyshopAdminBaseController {
	
	private static final String viewPrefix = "public/";
	private static Logger logger = Logger.getLogger(UserAuthController.class);
	
	
	@Autowired protected SecurityService securityService;
	@Autowired protected EmailService emailService;
	@Autowired protected PasswordEncoder passwordEncoder;
	@Autowired protected TemplateEngine templateEngine;
	
	@Autowired protected WebUtility webUtils;
	
	//@Autowired protected MessageCodes messageCodes;
	
	
	@Override
	protected String getHeaderTitle() {
		return "User";
	}
	
	
	@RequestMapping(value="/forgotPwd", method=RequestMethod.GET)
	public String forgotPwd()
	{
		return viewPrefix+"forgotPwd";
	}

	
	@RequestMapping (value="/forgotPwd" , method=RequestMethod.POST)
	public String handleForgotPwd(HttpServletRequest request, RedirectAttributes redirectAttributes){
		
		
		String email= request.getParameter("email");
		
		try{
			 //generates a token (UUID.randomUUID().toString()) and store
			
			String token =securityService.resetPassword(email);
			String resetPwdURL= webUtils.getURLWithContextPath(request)+"/resetPwd?email="+email+"&token="+token;
			logger.debug(resetPwdURL);
			this.sendForgotPasswordEmail(email,resetPwdURL);
			redirectAttributes.addFlashAttribute("msg", getMessage(MessageCodes.LABEL_PASSWORD_RESET_EMAIL_SUBJECT));
			
		}catch (MyshopException e) {
			logger.error(e);
			
			
		}
		
		return "redirect:/forgotPwd";
	}
	
	
	@RequestMapping(value="/resetPwd", method=RequestMethod.GET)
	public String resetPwd(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) throws MyshopException
	{
		String email = request.getParameter("email");
		String token = request.getParameter("token");
		
		boolean valid = securityService.verifyPasswordResetToken(email, token);
		if(valid){
			model.addAttribute("email", email);
			model.addAttribute("token", token);			
			return viewPrefix+"resetPwd";	
		} else {
			redirectAttributes.addFlashAttribute("msg", getMessage(MessageCodes.ERROR_INVALID_PASSWORD_RESET_REQUEST));
			return "redirect:/login";
		}
		
	}
	
	@RequestMapping(value="/resetPwd", method=RequestMethod.POST)
	public String handleResetPwd(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes)
	{
		try
		{
			String email = request.getParameter("email");
			String token = request.getParameter("token");
			String password = request.getParameter("password");
			String confPassword = request.getParameter("confPassword");
			if(!password.equals(confPassword))
			{
				model.addAttribute("email", email);
				model.addAttribute("token", token);	
				model.addAttribute("msg", getMessage(MessageCodes.ERROR_PASSWORD_CONF_PASSWORD_MISMATCH));
				return viewPrefix+"resetPwd";
			}
			String encodedPwd = passwordEncoder.encode(password);
			securityService.updatePassword(email, token, encodedPwd);
			
			redirectAttributes.addFlashAttribute("msg", getMessage(MessageCodes.INFO_PASSWORD_UPDATED_SUCCESS));
		} catch (MyshopException e)
		{
			logger.error(e);
			redirectAttributes.addFlashAttribute("msg", getMessage(MessageCodes.ERROR_INVALID_PASSWORD_RESET_REQUEST));
		}
		return "redirect:/login";
	}
	
	
	
	
	
	
	
	protected void sendForgotPasswordEmail(String email, String resetPwdURL) throws MyshopException
	{
		
	// Prepare the evaluation context
	
	final Context ctx= new Context();

	ctx.setVariable("resetPwdURL",resetPwdURL);
	
	 // Create the HTML body using Thymeleaf
	final String htmlContent= this.templateEngine.process("forgot-password-email", ctx);
	emailService.sendEmail(email, getMessage(MessageCodes.LABEL_PASSWORD_RESET_EMAIL_SUBJECT), htmlContent);
	
	

}
	
	
	
}