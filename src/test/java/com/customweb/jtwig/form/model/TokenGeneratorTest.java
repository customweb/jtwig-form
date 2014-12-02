package com.customweb.jtwig.form.model;

import java.text.ParseException;

import org.junit.Test;

public class TokenGeneratorTest {

	@Test
	public void testToken() throws SecurityException, ParseException {
		String token = new TokenGenerator().generate("formId");
		new TokenGenerator().validate(token);
	}

	public static class TokenGenerator extends AbstractTokenGenerator {

		@Override
		protected String getSecretKey() {
			return "secretKey";
		}

		@Override
		protected String getUserId() {
			return "userId";
		}

	}

}