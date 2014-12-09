package com.customweb.jtwig.form.model;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.util.ObjectUtils;

import com.customweb.jtwig.form.tag.AbstractFormMultiElementTag.MapItem;

abstract public class SelectedValueComparator {

	public static boolean isSelected(BindStatus bindStatus, Object candidateValue) {
		if (candidateValue instanceof MapItem) {
			candidateValue = ((MapItem) candidateValue).getValue();
		}
		
		if (bindStatus == null) {
			return (candidateValue == null);
		}
		
		Object boundValue = bindStatus.getValue();
		if (ObjectUtils.nullSafeEquals(boundValue, candidateValue)) {
			return true;
		}

		Object actualValue = bindStatus.getActualValue();
		if (actualValue != null && actualValue != boundValue && ObjectUtils.nullSafeEquals(actualValue, candidateValue)) {
			return true;
		}

		if (actualValue != null) {
			boundValue = actualValue;
		} else if (boundValue == null) {
			return false;
		}

		String candidateDisplayString = ObjectUtils.getDisplayString(candidateValue);
		if (actualValue.getClass().isArray()) {
			return Arrays.asList(actualValue).contains(candidateValue);
		} else if (actualValue instanceof Collection) {
			return ((Collection<?>) actualValue).contains(candidateValue);
		} else if (boundValue.getClass().isEnum()) {
			Enum<?> boundEnum = (Enum<?>) boundValue;
			String enumCodeAsString = ObjectUtils.getDisplayString(boundEnum.name());
			if (enumCodeAsString.equals(candidateDisplayString)) {
				return true;
			}
			String enumLabelAsString = ObjectUtils.getDisplayString(boundEnum.toString());
			if (enumLabelAsString.equals(candidateDisplayString)) {
				return true;
			}
		} else if (ObjectUtils.getDisplayString(boundValue).equals(candidateDisplayString)) {
			return true;
		}
		return false;
	}

}
