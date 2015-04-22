package com.customweb.jtwig.form.addon.element;

import org.jtwig.Environment;
import org.jtwig.loader.Loader;

import com.customweb.jtwig.form.tag.element.FormButtonTag;

public class FormButtonAddon extends FormElementAddon<FormButtonTag> {

	public FormButtonAddon(Loader.Resource resource, Environment environment) {
		super(resource, environment);
	}

	@Override
	protected String keyword() {
		return "form:button";
	}

	@Override
	public FormButtonTag instance() {
		return new FormButtonTag();
	}

}
