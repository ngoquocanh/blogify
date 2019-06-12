package com.quopri.blogify.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@PropertySource("file:/home/albert/anhqngo/config-files/blogify/settings.properties")
@ConfigurationProperties(prefix = "app")
@Data
public class ApplicationSettings implements Serializable {

    private String baseUrl;
    private String title;
    private Boolean mailSignUp;
    private String sendEmailContactUsTo;
    private String webMasterEmail;
    private String secretKeyPassword;
    private String secretKeySalt;
}
