package com.customweb.jtwig.form.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.NoSuchMessageException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.customweb.jtwig.form.tag.FormTag;
import com.lyncode.jtwig.render.RenderContext;
import com.lyncode.jtwig.types.Undefined;
import com.lyncode.jtwig.util.ObjectExtractor;
import com.lyncode.jtwig.util.ObjectExtractor.ExtractException;

public class BindStatus {

	private final String path;

	private final String expression;

	private final Errors errors;

	private Object value;

	private Class<?> valueType;

	private Object actualValue;

	private List<? extends ObjectError> objectErrors;

	private List<String> errorCodes = new ArrayList<String>();

	private List<String> errorMessages = new ArrayList<String>();

	public BindStatus(RenderContext context, String path) throws ExtractException {
		this.path = path;

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
			ObjectExtractor extractor = new ObjectExtractor(target);
			this.value = extractor.extract(this.expression);
			this.valueType = this.value.getClass();
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
		for (int i = 0; i < this.objectErrors.size(); i++) {
			ObjectError error = this.objectErrors.get(i);
			this.errorCodes.add(error.getCode());
		}
	}

	private void initErrorMessages() throws NoSuchMessageException {
		for (int i = 0; i < this.objectErrors.size(); i++) {
			ObjectError error = this.objectErrors.get(i);
			this.errorMessages.add(error.getDefaultMessage());
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
		return ObjectUtils.getDisplayString(this.value);
	}

	public boolean isError() {
		return (this.errorCodes != null && this.errorCodes.size() > 0);
	}

	public List<String> getErrorCodes() {
		return Collections.unmodifiableList(this.errorCodes);
	}

	public String getErrorCode() {
		return (this.errorCodes.size() > 0 ? this.errorCodes.get(0) : "");
	}

	public List<String> getErrorMessages() {
		return Collections.unmodifiableList(this.errorMessages);
	}

	public String getErrorMessage() {
		return (this.errorMessages.size() > 0 ? this.errorMessages.get(0) : "");
	}

	public List<? extends ObjectError> getErrors() {
		return Collections.unmodifiableList(this.objectErrors);
	}

}
