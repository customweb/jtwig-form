package com.customweb.jtwig.form.addon.element;

import org.jtwig.Environment;
import org.jtwig.loader.Loader;

import com.customweb.jtwig.form.tag.element.FormInputTag;

public class FormInputAddon extends FormElementAddon<FormInputTag> {

	public FormInputAddon(Loader.Resource resource, Environment environment) {
		super(resource, environment);
	}

	@Override
	protected String keyword() {
		return "form:input";
	}

	@Override
	public FormInputTag instance() {
		return new FormInputTag();
	}

}
