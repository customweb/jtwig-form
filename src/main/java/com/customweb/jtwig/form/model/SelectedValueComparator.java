package com.customweb.jtwig.form.model;

import java.util.Arrays;
import java.util.Collection;

import com.customweb.jtwig.form.Utils;

abstract public class SelectedValueComparator {

	public static boolean isSelected(Object actualValue, String candidateValue) {
		if (actualValue == null) {
			return (candidateValue == null);
		}
		if (Utils.nullSafeEquals(actualValue, candidateValue)) {
			return true;
		}
		if (actualValue.getClass().isArray()) {
			return Arrays.asList(actualValue).contains(candidateValue);
		}
		else if (actualValue instanceof Collection) {
			return ((Collection<?>) actualValue).contains(candidateValue);
		}
		return false;
	}
	
}
