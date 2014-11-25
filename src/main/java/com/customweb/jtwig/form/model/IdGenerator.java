package com.customweb.jtwig.form.model;

import com.lyncode.jtwig.render.RenderContext;
import com.lyncode.jtwig.types.Undefined;

abstract public class IdGenerator {
	
	private static final String CONTEXT_ATTRIBUTE_PREFIX = IdGenerator.class.getName() + ".";

	public static String nextId(String name, RenderContext context) {
		String attributeName = CONTEXT_ATTRIBUTE_PREFIX + name;
		Object currentCount = context.map(attributeName);
		currentCount = (currentCount != null && !currentCount.equals(Undefined.UNDEFINED) ? ((Integer) currentCount) + 1 : 1);
		context.with(attributeName, currentCount);
		return (name + currentCount);
	}
	
}