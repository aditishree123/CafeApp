package com.dbms.cafe.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.Properties;

@Configuration
@EnableWebSecurity
public class Config {
    @Bean(name="mailSender")
    public MailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setProtocol("smtp");
        javaMailSender.setUsername("aditishree105");
        javaMailSender.setPassword("rupam@123");
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth","true");
        mailProperties.put("mail.smtp.starttls.enable","true");
        mailProperties.put("mail.smtp.debug","true");
        javaMailSender.setJavaMailProperties(mailProperties);
        return javaMailSender;
    }

    @Bean(name = "mailMessageSource")
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource =  new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(0);
        return messageSource;
    }
}
