package com.company;

import java.io.FileNotFoundException;

public interface AuthService {
    void start();
    void stop();
    String authorization(String login, String pass) throws FileNotFoundException;
}
