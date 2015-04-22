package com.customweb.jtwig.form.addon.element;

import org.jtwig.Environment;
import org.jtwig.loader.Loader;

import com.customweb.jtwig.form.tag.element.FormMultiCheckboxTag;

public class FormMultiCheckboxAddon extends FormElementAddon<FormMultiCheckboxTag> {

	public FormMultiCheckboxAddon(Loader.Resource resource, Environment environment) {
		super(resource, environment);
	}

	@Override
	protected String keyword() {
		return "form:multicheckbox";
	}

	@Override
	public FormMultiCheckboxTag instance() {
		return new FormMultiCheckboxTag();
	}

}
