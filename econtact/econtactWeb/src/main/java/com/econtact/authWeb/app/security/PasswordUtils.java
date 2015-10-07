package com.econtact.authWeb.app.security;

import java.util.UUID;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public final class PasswordUtils {

	private static ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);

	public static String convertPassword(String password, String salt) {
		return passwordEncoder.encodePassword(password, salt);
	}

	public static String getRandomSalt() {
		return UUID.randomUUID().toString();
	}

	public static boolean machPassword(String hashPassword, String currenPassword, String salt) {
		return passwordEncoder.isPasswordValid(hashPassword, currenPassword, salt);
	}

	private PasswordUtils() {
	}
}
