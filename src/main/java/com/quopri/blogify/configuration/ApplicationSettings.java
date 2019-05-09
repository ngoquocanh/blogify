package com.quopri.blogify.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@PropertySource("file:/home/dev/config/settings.properties")
@ConfigurationProperties(prefix = "app")
@Data
public class ApplicationSettings implements Serializable {

    private String baseUrl;
}
