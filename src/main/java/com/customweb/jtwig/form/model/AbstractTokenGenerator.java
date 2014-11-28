package com.customweb.jtwig.form.model;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.ObjectUtils;

import com.customweb.jtwig.form.addon.element.FormTokenAddon;

abstract public class AbstractTokenGenerator {
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyyMMddHHmmss");

	private static final String HMAC_ALGORITHM = "HmacSHA256";
	
	public final void register() throws InstantiationException, IllegalAccessException {
		FormTokenAddon.setTokenGeneratorClass(this.getClass());
	}
	
	/**
	 * Return the number of seconds, after which the token is considered invalid (timeout).
	 * 
	 * @return The number of seconds the token will be valid for.
	 */
	protected Integer getTimeout() {
		return 600;
	}

	/**
	 * Return a secret key to encrypt the message.
	 * 
	 * @return
	 */
	abstract protected String getSecretKey();
	
	/**
	 * Return an identifier for the currently active user.
	 * 
	 * @return
	 */
	abstract protected String getUserId();
	
	public final void validate(String token) throws ParseException, SecurityException {
		if (token == null || token.isEmpty()) {
			throw new SecurityException("The form token must not be empty.");
		}
		
		String[] splitToken = token.split(":");
		if (splitToken.length != 3) {
			throw new SecurityException("The form token's format is invalid.");
		}
		String actualToken = splitToken[0];
		String formId = splitToken[1];
		DateTime date = DateTime.parse(splitToken[2], DATE_FORMATTER);
		
		if (Seconds.secondsBetween(date, new DateTime())
                .isGreaterThan(Seconds.seconds(this.getTimeout()))) {
			throw new SecurityException("The form token is expired.");
		}
		String compareToken = this.generateHmac(this.buildString(date, formId));
		if (!ObjectUtils.nullSafeEquals(compareToken, actualToken)) {
			throw new SecurityException("The form token is not valid.");
		}
	}
	
	public final String generate(String formId) {
		DateTime date = new DateTime();
		return this.generateHmac(this.buildString(date, formId)) + ":" + formId + ":" + DATE_FORMATTER.print(date);
	}
	
	private final String buildString(DateTime date, String formId) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.getUserId());
		stringBuilder.append(DATE_FORMATTER.print(date));
		stringBuilder.append(formId);
		return stringBuilder.toString();
	}

	private final String generateHmac(String input) {
		String digest = null;
		try {
			SecretKeySpec key = new SecretKeySpec((this.getSecretKey()).getBytes("UTF-8"), HMAC_ALGORITHM);
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

}
