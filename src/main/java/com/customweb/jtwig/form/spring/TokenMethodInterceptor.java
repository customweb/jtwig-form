package com.customweb.jtwig.form.spring;

import java.text.ParseException;

import javax.servlet.ServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;

import com.customweb.jtwig.form.addon.element.FormTokenAddon;

@Aspect
public class TokenMethodInterceptor {

	@Before("@annotation(com.customweb.jtwig.form.spring.TokenProtected)")
	public void validateToken(JoinPoint joinPoint) {
		String token = "";
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			if (arg instanceof ServletRequest) {
				token = ((ServletRequest) arg).getParameter("_token");
			}
		}
		
		try {
			FormTokenAddon.getTokenGenerator().validate(token);
		} catch (SecurityException | ParseException e) {
			throw new AccessDeniedException(e.getMessage());
		}
	}

}
