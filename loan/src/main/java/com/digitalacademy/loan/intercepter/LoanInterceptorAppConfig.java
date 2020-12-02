package com.digitalacademy.loan.intercepter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class LoanInterceptorAppConfig extends WebMvcConfigurerAdapter {
    @Autowired
    LoanInterceptor loanInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loanInterceptor);
    }
}
