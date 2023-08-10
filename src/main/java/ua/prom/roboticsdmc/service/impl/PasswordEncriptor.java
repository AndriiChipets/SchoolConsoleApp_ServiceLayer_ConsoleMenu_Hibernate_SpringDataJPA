package ua.prom.roboticsdmc.service.impl;

import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class PasswordEncriptor {

    public String encript(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }
}
