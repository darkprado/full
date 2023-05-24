package com.example.back.config;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
public interface SecurityProps {

    String getSignUpURLS();

    void setSignUpURLS(String signUpURLS);

    String getSecret();

    void setSecret(String secret);

    String getTokenPrefix();

    void setTokenPrefix(String tokenPrefix);

    String getHeaderString();

    void setHeaderString(String headerString);

    String getContentType();

    void setContentType(String contentType);

    long getExpirationTime();

    void setExpirationTime(long expirationTime);

}
