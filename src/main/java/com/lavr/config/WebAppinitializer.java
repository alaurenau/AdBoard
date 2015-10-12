package com.lavr.config;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.lavr.web.WebConfig;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class WebAppinitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.addServlet("captcha", KaptchaServlet.class).addMapping("/kaptcha.jpg");
        super.onStartup(servletContext);
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class };
    }

    //filter for char encoding
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encFilter = new CharacterEncodingFilter();

        encFilter.setEncoding("UTF-8");
        encFilter.setForceEncoding(true);
        return new Filter[] {encFilter};
    }
}



