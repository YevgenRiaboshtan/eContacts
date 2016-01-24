package com.econtact.authWeb.app.security;

import java.util.UUID;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public final class PasswordUtils {

	private static ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);

	/**
	 * Method return hashPassword;
	 * @param password
	 * @param salt
	 * @return
	 */
	public static String convertPassword(String password, String salt) {
		return passwordEncoder.encodePassword(password, salt);
	}

	/**
	 * Метод для генерации новой соли для пользователя.
	 * @return генерированая соль.
	 */
	public static String getRandomSalt() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * Сравнение паролей
	 * @param hashPassword - пароль в зашифрованном виде.
	 * @param currentPassword - введённый пароль.
	 * @param salt - соль.
	 * @return true - если пароли равны, иначе false.
	 */
	public static boolean machPassword(String hashPassword, String currentPassword, String salt) {
		return passwordEncoder.isPasswordValid(hashPassword, currentPassword, salt);
	}

	private PasswordUtils() {
	}
}
