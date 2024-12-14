package org.baibei.keyboardtrackertgbot.pojo.passwordencoder;

public class PasswordEncoder {

    public String encode(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public boolean verify(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            throw new IllegalArgumentException("Passwords cannot be null");
        }
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
