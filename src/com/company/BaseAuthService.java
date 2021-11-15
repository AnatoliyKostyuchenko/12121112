package com.company;

import javax.print.DocFlavor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BaseAuthService implements AuthService{
    private List<Entry> entries;
    private Object InputStream;

    public BaseAuthService() {
        entries = new ArrayList<>();
        entries.add(new Entry("login1","pass1","nickname1"));
        entries.add(new Entry("login2","pass2","nickname2"));
        entries.add(new Entry("login3","pass3","nickname3"));
    }
    @Override
    public void start(){
        System.out.println("Auth service is running");
    }
    @Override
    public void stop(){
        System.out.println("Auth service is shutting down");
    }
    @Override
    public String authorization(String login, String pass) throws FileNotFoundException {
        for (Entry entry : entries) {
            Scanner scanner = new Scanner((File) InputStream);
            while (scanner.hasNext()) {
                if (entry.login.equals(login) && entry.pass.equals(pass)) {
                    return entry.nickname;
                } else {
                    System.out.println("Логин или пароль неверен");
                }
            }
        }
        return null;
    }
    private class Entry{
        private String login;
        private String pass;
        public String nickname;

        public Entry(String login, String pass, String nickname) {
            this.login = login;
            this.pass = pass;
            this.nickname = nickname;
        }
    }
}
