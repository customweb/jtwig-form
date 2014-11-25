package com.customweb.jtwig.form.model;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.ObjectUtils;

public final class TokenGenerator {
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyyMMddhhmmss");

	private static final String HMAC_ALGORITHM = "HmacSHA256";

	private final String secretKey;

	private final String perUserValue;
	
	public TokenGenerator(String secretKey, String perUserValue) {
		this.secretKey = secretKey;
		this.perUserValue = perUserValue;
	}
	
	public void validate(String formId, String inputToken, String inputDateString) throws ParseException, SecurityException {
		if (inputToken == null || inputToken.isEmpty()) {
			throw new SecurityException("The form token must not be empty.");
		}
		DateTime inputDate = DateTime.parse(inputDateString, DATE_FORMATTER);
		if (Minutes.minutesBetween(inputDate, new DateTime())
                .isGreaterThan(Minutes.minutes(20))) {
			throw new SecurityException("The form token is expired.");
		}
		String tokenString = this.generateHmac(this.buildString(inputDate, formId));
		if (!ObjectUtils.nullSafeEquals(tokenString, inputToken)) {
			throw new SecurityException("The form token is not valid.");
		}
	}
	
	public Token generate(String formId) {
		DateTime date = new DateTime();
		String tokenString = this.generateHmac(this.buildString(date, formId));
		return new Token(date, tokenString);
	}
	
	private String buildString(DateTime date, String formId) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.perUserValue);
		stringBuilder.append(DATE_FORMATTER.print(date));
		stringBuilder.append(formId);
		return stringBuilder.toString();
	}

	private String generateHmac(String input) {
		String digest = null;
		try {
			SecretKeySpec key = new SecretKeySpec((this.secretKey).getBytes("UTF-8"), HMAC_ALGORITHM);
			Mac mac = Mac.getInstance(HMAC_ALGORITHM);
			mac.init(key);

			byte[] bytes = mac.doFinal(input.getBytes("ASCII"));

			StringBuffer hash = new StringBuffer();
			for (int i = 0; i < bytes.length; i++) {
				String hex = Integer.toHexString(0xFF & bytes[i]);
				if (hex.length() == 1) {
					hash.append('0');
				}
				hash.append(hex);
			}
			digest = hash.toString();
		} catch (UnsupportedEncodingException e) {
		} catch (InvalidKeyException e) {
		} catch (NoSuchAlgorithmException e) {
		}
		return digest;
	}
	
	public final class Token {
		private final String tokenString;
		
		private final DateTime date;

		public Token(DateTime date, String tokenString) {
			this.tokenString = tokenString;
			this.date = date;
		}

		public String getTokenString() {
			return tokenString;
		}

		public DateTime getDate() {
			return date;
		}
	}

}
