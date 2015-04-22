package com.customweb.jtwig.form.model;

import org.jtwig.render.RenderContext;
import org.jtwig.types.Undefined;

abstract public class IdGenerator {
	
	private static final String CONTEXT_ATTRIBUTE_PREFIX = IdGenerator.class.getName() + ".";

	public static String nextId(String name, RenderContext context) {
		RenderContext topContext = (RenderContext) context.map("topContext");
		String attributeName = CONTEXT_ATTRIBUTE_PREFIX + name;
		Object currentCount = topContext.map(attributeName);
		currentCount = (currentCount != null && !currentCount.equals(Undefined.UNDEFINED) ? ((Integer) currentCount) + 1 : 1);
		topContext.with(attributeName, currentCount);
		return (name + currentCount);
	}
	
}