package com.customweb.jtwig.form.model;

import java.util.List;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.util.HtmlUtils;

import com.customweb.jtwig.form.tag.FormTag;
import com.lyncode.jtwig.render.RenderContext;
import com.lyncode.jtwig.types.Undefined;

public class BindStatus {

	private final String path;

	private final boolean htmlEscape;

	private final String expression;

	private final Errors errors;

	private Object value;

	private Class<?> valueType;

	private Object actualValue;

	private List<? extends ObjectError> objectErrors;

	private String[] errorCodes;

	private String[] errorMessages;

	@SuppressWarnings("unchecked")
	public BindStatus(RenderContext context, String path, boolean htmlEscape) {
		this.path = path;
		this.htmlEscape = htmlEscape;

		String beanName;
		int dotPos = path.indexOf('.');
		if (dotPos == -1) {
			beanName = path;
			this.expression = null;
		} else {
			beanName = path.substring(0, dotPos);
			this.expression = path.substring(dotPos + 1);
		}

		Object target = context.map(beanName);
		if (target == null || target.equals(Undefined.UNDEFINED)) {
			throw new IllegalStateException("No target object for bean name '" + beanName
					+ "' available as model map attribute.");
		}
		if (this.expression != null) {
			BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(target);
			this.value = beanWrapper.getPropertyValue(this.expression);
			this.valueType = beanWrapper.getPropertyType(this.expression);
			this.actualValue = this.value;
		}

		Object errorTargetName = context.map(FormTag.ERRORS_ATTRIBUTE_VARIABLE_NAME);
		if (errorTargetName != null && errorTargetName instanceof String) {
			
		}
		
		Object errorTarget = this.getErrorTarget(context);
		if (errorTarget != null && errorTarget instanceof Errors) {
			this.errors = (Errors) errorTarget;
			if (this.expression != null) {
				this.objectErrors = this.errors.getFieldErrors(this.expression);
			} else {
				this.objectErrors = this.errors.getGlobalErrors();
			}
			initErrorCodes();
			initErrorMessages();
		} else {
			this.errors = null;
			this.errorCodes = new String[0];
			this.errorMessages = new String[0];
		}
	}
	
	private Object getErrorTarget(RenderContext context) {
		Object errorTargetName = context.map(FormTag.ERRORS_ATTRIBUTE_VARIABLE_NAME);
		if (errorTargetName == null || !(errorTargetName instanceof String)) {
			return null;
		}
		return context.map((String) errorTargetName);
	}

	private void initErrorCodes() {
		this.errorCodes = new String[this.objectErrors.size()];
		for (int i = 0; i < this.objectErrors.size(); i++) {
			ObjectError error = this.objectErrors.get(i);
			this.errorCodes[i] = error.getCode();
		}
	}

	private void initErrorMessages() throws NoSuchMessageException {
		this.errorMessages = new String[this.objectErrors.size()];
		for (int i = 0; i < this.objectErrors.size(); i++) {
			ObjectError error = this.objectErrors.get(i);
			this.errorMessages[i] = (this.htmlEscape ? HtmlUtils.htmlEscape(error.getDefaultMessage()) : error
					.getDefaultMessage());
		}
	}

	public String getPath() {
		return this.path;
	}

	public String getExpression() {
		return this.expression;
	}

	public Object getValue() {
		return this.value;
	}

	public Class<?> getValueType() {
		return this.valueType;
	}

	public Object getActualValue() {
		return this.actualValue;
	}

	public String getDisplayValue() {
		String displayValue = ObjectUtils.getDisplayString(this.value);
		return (this.htmlEscape ? HtmlUtils.htmlEscape(displayValue) : displayValue);
	}

	public boolean isError() {
		return (this.errorCodes != null && this.errorCodes.length > 0);
	}

	public String[] getErrorCodes() {
		return this.errorCodes;
	}

	public String getErrorCode() {
		return (this.errorCodes.length > 0 ? this.errorCodes[0] : "");
	}

	public String[] getErrorMessages() {
		return this.errorMessages;
	}

	public String getErrorMessage() {
		return (this.errorMessages.length > 0 ? this.errorMessages[0] : "");
	}

	public String getErrorMessagesAsString(String delimiter) {
		return StringUtils.arrayToDelimitedString(this.errorMessages, delimiter);
	}

	public Errors getErrors() {
		return this.errors;
	}

}
