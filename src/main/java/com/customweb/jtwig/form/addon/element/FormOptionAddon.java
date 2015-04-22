package com.customweb.jtwig.form.addon.element;

import org.jtwig.Environment;
import org.jtwig.loader.Loader;

import com.customweb.jtwig.form.tag.element.FormOptionTag;

public class FormOptionAddon extends FormElementAddon<FormOptionTag> {

	public FormOptionAddon(Loader.Resource resource, Environment environment) {
		super(resource, environment);
	}

	@Override
	protected String keyword() {
		return "form:option";
	}

	@Override
	public FormOptionTag instance() {
		return new FormOptionTag();
	}

}
