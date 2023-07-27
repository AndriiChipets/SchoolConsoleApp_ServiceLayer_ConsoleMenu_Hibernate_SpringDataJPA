package ua.prom.roboticsdmc.service;

import java.util.Base64;

public class PasswordEncriptor {

    public String encript(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public String dencript(String password) {
        return new String(Base64.getDecoder().decode(password));
    }
}
