package com.customweb.jtwig.form;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.customweb.jtwig.lib.model.Attribute;
import com.lyncode.jtwig.render.RenderContext;
import com.lyncode.jtwig.types.Undefined;

abstract public class Utils {
	
	@SuppressWarnings("unchecked")
	public static String nextId(String name, RenderContext context) {
		Map<String, Integer> idCountCache;
		if (context.map("idCountCache") == null || context.map("idCountCache") == Undefined.UNDEFINED) {
			idCountCache = new HashMap<String, Integer>();
			context.with("idCountCache", idCountCache);
		} else {
			idCountCache = (Map<String, Integer>) context.map("idCountCache");
		}
		int count = 1;
		if (idCountCache.containsKey(name)) {
			count = idCountCache.get(name) + 1;
		}
		idCountCache.put(name, count);
		return name + count;
	}

	public static String concatAttributes(Collection<? extends Attribute> attributes) {
		StringBuilder builder = new StringBuilder();
		for (Attribute attribute : attributes) {
			builder.append(" ").append(attribute.toString());
		}
		return builder.toString();
	}
	
	public static String nullSafeToString(Object o) {
		if (o == null) {
			return "";
		} else {
			return o.toString();
		}
	}

	public static boolean nullSafeEquals(Object o1, Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		if (o1.equals(o2)) {
			return true;
		}
		if (o1.getClass().isArray() && o2.getClass().isArray()) {
			if (o1 instanceof Object[] && o2 instanceof Object[]) {
				return Arrays.equals((Object[]) o1, (Object[]) o2);
			}
			if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
				return Arrays.equals((boolean[]) o1, (boolean[]) o2);
			}
			if (o1 instanceof byte[] && o2 instanceof byte[]) {
				return Arrays.equals((byte[]) o1, (byte[]) o2);
			}
			if (o1 instanceof char[] && o2 instanceof char[]) {
				return Arrays.equals((char[]) o1, (char[]) o2);
			}
			if (o1 instanceof double[] && o2 instanceof double[]) {
				return Arrays.equals((double[]) o1, (double[]) o2);
			}
			if (o1 instanceof float[] && o2 instanceof float[]) {
				return Arrays.equals((float[]) o1, (float[]) o2);
			}
			if (o1 instanceof int[] && o2 instanceof int[]) {
				return Arrays.equals((int[]) o1, (int[]) o2);
			}
			if (o1 instanceof long[] && o2 instanceof long[]) {
				return Arrays.equals((long[]) o1, (long[]) o2);
			}
			if (o1 instanceof short[] && o2 instanceof short[]) {
				return Arrays.equals((short[]) o1, (short[]) o2);
			}
		}
		return false;
	}

}
