package ru.molefed;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Value("${mvc.cors:true}")
    private boolean corsEnabled;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (!corsEnabled) {
            registry.addMapping("/**").allowedMethods("*");
        }
    }

}
