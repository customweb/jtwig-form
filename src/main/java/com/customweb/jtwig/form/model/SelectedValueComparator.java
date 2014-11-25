package com.customweb.jtwig.form.model;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.util.ObjectUtils;

abstract public class SelectedValueComparator {

	public static boolean isSelected(BindStatus bindStatus, Object candidateValue) {
		if (bindStatus == null) {
			return (candidateValue == null);
		}

		Object boundValue = bindStatus.getValue();
		if (ObjectUtils.nullSafeEquals(boundValue, candidateValue)) {
			return true;
		}
		
		Object actualValue = bindStatus.getActualValue();
		if (actualValue != null && actualValue != boundValue &&
				ObjectUtils.nullSafeEquals(actualValue, candidateValue)) {
			return true;
		}
		
		if (actualValue != null) {
			boundValue = actualValue;
		} else if (boundValue == null) {
			return false;
		}
		
		if (actualValue.getClass().isArray()) {
			return Arrays.asList(actualValue).contains(candidateValue);
		} else if (actualValue instanceof Collection) {
			return ((Collection<?>) actualValue).contains(candidateValue);
		}
		return false;
	}

}
