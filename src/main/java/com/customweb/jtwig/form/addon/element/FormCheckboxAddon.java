package com.customweb.jtwig.form.addon.element;

import org.jtwig.Environment;
import org.jtwig.loader.Loader;

import com.customweb.jtwig.form.tag.element.FormCheckboxTag;

public class FormCheckboxAddon extends FormElementAddon<FormCheckboxTag> {

	public FormCheckboxAddon(Loader.Resource resource, Environment environment) {
		super(resource, environment);
	}

	@Override
	protected String keyword() {
		return "form:checkbox";
	}

	@Override
	public FormCheckboxTag instance() {
		return new FormCheckboxTag();
	}

}
