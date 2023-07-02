package com.example.wagster.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordPolicy {

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,16}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(String password){
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
