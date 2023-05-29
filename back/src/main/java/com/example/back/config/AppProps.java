package com.example.back.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@ConfigurationProperties(prefix = "application")
@Component
public class AppProps implements SecurityProps {

    private String signUpURLS;
    private String v3URLS;
    private String swaggerURLS;
    private String secret;
    private String tokenPrefix;
    private String headerString;
    private String contentType;
    private long expirationTime;

    @Override
    public String getSignUpURLS() {
        return signUpURLS;
    }

    @Override
    public String getSecret() {
        return secret;
    }

    @Override
    public String getTokenPrefix() {
        return tokenPrefix;
    }

    @Override
    public String getHeaderString() {
        return headerString;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public long getExpirationTime() {
        return expirationTime;
    }

    @Override
    public void setSignUpURLS(String signUpURLS) {
        this.signUpURLS = signUpURLS;
    }

    @Override
    public String getV3URLS() {
        return v3URLS;
    }

    @Override
    public void setV3URLS(String v3URLS) {
        this.v3URLS = v3URLS;
    }

    @Override
    public String getSwaggerURLS() {
        return swaggerURLS;
    }

    @Override
    public void setSwaggerURLS(String swaggerURLS) {
        this.swaggerURLS = swaggerURLS;
    }

    @Override
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    @Override
    public void setHeaderString(String headerString) {
        this.headerString = headerString;
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
