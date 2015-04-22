package com.customweb.jtwig.form.addon.element;

import org.jtwig.Environment;
import org.jtwig.loader.Loader;

import com.customweb.jtwig.form.tag.element.FormErrorsTag;

public class FormErrorsAddon extends FormElementAddon<FormErrorsTag> {

	public FormErrorsAddon(Loader.Resource resource, Environment environment) {
		super(resource, environment);
	}

	@Override
	protected String keyword() {
		return "form:errors";
	}

	@Override
	public FormErrorsTag instance() {
		return new FormErrorsTag();
	}

}
