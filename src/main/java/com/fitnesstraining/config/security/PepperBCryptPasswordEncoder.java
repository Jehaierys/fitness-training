package com.fitnesstraining.config.security;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Primary
@Component
public class PepperBCryptPasswordEncoder implements PasswordEncoder {

    private final String pepper;
    private final BCryptPasswordEncoder delegate;

    public PepperBCryptPasswordEncoder(
            @Autowired Dotenv dotenv,
            @Autowired BCryptPasswordEncoder passwordEncoder
    ) {
        this.pepper = dotenv.get("PASSWORD_PEPPER");
        this.delegate = passwordEncoder;
    }


    @Override
    public String encode(CharSequence rawPassword) {
        return delegate.encode(rawPassword + pepper);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return delegate.matches(rawPassword + pepper, encodedPassword);
    }
}
