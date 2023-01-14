package com.example.accountbook.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        MustacheViewResolver mustacheViewResolver = new MustacheViewResolver();
//        mustacheViewResolver.setCharset("UTF-8");
//        mustacheViewResolver.setContentType("text/html;charset=UTF-8");
//        mustacheViewResolver.setPrefix("classpath:/templates/");
//        mustacheViewResolver.setSuffix(".html");
//
//        registry.viewResolver(mustacheViewResolver);
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                .exposedHeaders("X-Total-Count");
    }


}
