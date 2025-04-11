package com.demo.incidentmanagement.util;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class for managing password related operations
 */
public final class PasswordUtility {
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}";
    private static final SecureRandom random = new SecureRandom();

    /**
     * Creates temporary password
     * @return generated password
     */
    public static String generateTempPassword() {
        List<Character> passwordChars = new ArrayList<>();

        // Ensure at least one character from each required set
        passwordChars.add(LOWER.charAt(random.nextInt(LOWER.length())));
        passwordChars.add(UPPER.charAt(random.nextInt(UPPER.length())));
        passwordChars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        passwordChars.add(SYMBOLS.charAt(random.nextInt(SYMBOLS.length())));

        // Fill the rest of the password (8 - 4 = 4 more characters)
        String allChars = LOWER + UPPER + DIGITS + SYMBOLS;
        for (int i = 0; i < 4; i++) {
            passwordChars.add(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Shuffle to avoid predictable order
        Collections.shuffle(passwordChars, random);

        // Convert to string
        StringBuilder password = new StringBuilder();
        for (char ch : passwordChars) {
            password.append(ch);
        }

        return password.toString();
    }

    /**
     * Validated password
     * @param password User's password
     * @return Whether password is valid or not
     */
    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }

        if (password.length() < 8 || password.length() > 20) {
            return false;
        }

        //Regex for password validation
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&^#()_+=\\-{}\\[\\]|:;\"'<>,./~`]).{8,20}$";

        return password.matches(pattern);
    }
}
