package com.customweb.jtwig.form.addon.element;

import org.jtwig.Environment;
import org.jtwig.loader.Loader;

import com.customweb.jtwig.form.tag.element.FormSelectTag;

public class FormSelectAddon extends FormElementAddon<FormSelectTag> {

	public FormSelectAddon(Loader.Resource resource, Environment environment) {
		super(resource, environment);
	}

	@Override
	protected String keyword() {
		return "form:select";
	}

	@Override
	public FormSelectTag instance() {
		return new FormSelectTag();
	}

}
