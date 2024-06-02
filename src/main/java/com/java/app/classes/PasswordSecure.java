package com.java.app.classes;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.Getter;
import lombok.Setter;

public class PasswordSecure {
    @Getter
    @Setter
    private static int cost = 12;

    public static String hashPassword(String password) {
        BCrypt.Hasher hasher = BCrypt.withDefaults();
        return hasher.hashToString(cost, password.toCharArray());
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        return (BCrypt.verifyer().verify(password.toCharArray(), hashedPassword)).verified;
    }
}
