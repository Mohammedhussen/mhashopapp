package com.muhe.myshop.admin.config;

import javax.servlet.Filter;// for filter registration

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.muhe.myshop.admin.security.PostAuthorizationFilter;



@Configuration

public class WebConfig  extends WebMvcConfigurerAdapter{
	
	 @Value("${server.port:9443}") private int serverPort;	
	
	
	 @Bean
	    public EmbeddedServletContainerFactory servletContainer() {
	        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
	            @Override
	            protected void postProcessContext(Context context) {
	                SecurityConstraint securityConstraint = new SecurityConstraint();
	                securityConstraint.setUserConstraint("CONFIDENTIAL");
	                SecurityCollection collection = new SecurityCollection();
	                collection.addPattern("/*");
	                securityConstraint.addCollection(collection);
	                context.addConstraint(securityConstraint);
	            }
	        };
	 
	        tomcat.addAdditionalTomcatConnectors(initiateHttpConnector());
	        return tomcat;
	        
	        
	    }
	
	
	 private Connector initiateHttpConnector() {
	        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
	        connector.setScheme("http");
	        connector.setPort(9090);
	        connector.setSecure(false);
	        connector.setRedirectPort(serverPort);
	 
	        return connector;
	    }
	
	
 @Override
   public void addViewControllers(ViewControllerRegistry registry)
    {
    super.addViewControllers(registry);
        registry.addViewController("/login").setViewName("public/login");
   registry.addRedirectViewController("/", "/home");
     
    }
	 
	 
	 @Autowired
    private MessageSource messageSource;
     @Override
   public Validator getValidator() {
       LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
       factory.setValidationMessageSource(messageSource);
       return factory;
   }	    

	
	@Autowired
    private PostAuthorizationFilter postAuthorizationFilter;
             
    @Bean
    public FilterRegistrationBean SecurityFilterChain(@Qualifier(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME) Filter securityFilter) {
      
		FilterRegistrationBean registration = new FilterRegistrationBean(securityFilter);
        registration.setOrder(Integer.MAX_VALUE - 1);
        registration.setName(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
        return registration;
    }
 
    @Bean
    public FilterRegistrationBean PostAuthorizationFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(postAuthorizationFilter);
        registrationBean.setOrder(Integer.MAX_VALUE);
        return registrationBean;
    }

	
	@Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }
	
	
	@Bean
	public ClassLoaderTemplateResolver emailTemplateResolver(){ 
	    ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver(); 
	    emailTemplateResolver.setPrefix("email-templates/"); 
	    emailTemplateResolver.setSuffix(".html"); 
	    emailTemplateResolver.setTemplateMode("HTML5"); 
	    emailTemplateResolver.setCharacterEncoding("UTF-8"); 
	    emailTemplateResolver.setOrder(2);
	     
	    return emailTemplateResolver; 
	}
	
}
