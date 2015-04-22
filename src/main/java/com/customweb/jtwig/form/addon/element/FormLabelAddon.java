package com.customweb.jtwig.form.addon.element;

import org.jtwig.Environment;
import org.jtwig.loader.Loader;

import com.customweb.jtwig.form.tag.element.FormLabelTag;
import com.customweb.jtwig.lib.attribute.AttributeAddon;

public class FormLabelAddon extends AttributeAddon<FormLabelTag> {

	public FormLabelAddon(Loader.Resource resource, Environment environment) {
		super(resource, environment);
	}

	@Override
	protected String keyword() {
		return "form:label";
	}

	@Override
	public FormLabelTag instance() {
		return new FormLabelTag();
	}

}
