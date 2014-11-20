package com.customweb.jtwig.form;

import java.util.Collection;

import com.customweb.jtwig.lib.model.Attribute;

public class Util {
	
	private Util() {}
	
	public static String concatAttributes(Collection<? extends Attribute> attributes) {
		StringBuilder builder = new StringBuilder();
		for (Attribute attribute : attributes) {
			builder.append(" ").append(attribute.toString());
		}
		return builder.toString();
	}
	
}
