package com.customweb.jtwig.form.addon.element;

import org.jtwig.Environment;
import org.jtwig.loader.Loader;

import com.customweb.jtwig.form.tag.AbstractFormElementTag;
import com.customweb.jtwig.lib.attribute.AttributeAddon;

public abstract class FormElementAddon<T extends AbstractFormElementTag<T>> extends AttributeAddon<T> {

	public FormElementAddon(Loader.Resource resource, Environment environment) {
		super(resource, environment);
	}

}