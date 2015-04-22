package com.customweb.jtwig.form.addon.element;

import org.jtwig.Environment;
import org.jtwig.loader.Loader;

import com.customweb.jtwig.form.tag.element.FormHiddenTag;

public class FormHiddenAddon extends FormElementAddon<FormHiddenTag> {

	public FormHiddenAddon(Loader.Resource resource, Environment environment) {
		super(resource, environment);
	}

	@Override
	protected String keyword() {
		return "form:hidden";
	}

	@Override
	public FormHiddenTag instance() {
		return new FormHiddenTag();
	}

}
