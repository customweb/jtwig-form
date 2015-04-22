package com.customweb.jtwig.form.addon.element;

import org.jtwig.Environment;
import org.jtwig.loader.Loader;

import com.customweb.jtwig.form.tag.element.FormRadioTag;

public class FormRadioAddon extends FormElementAddon<FormRadioTag> {

	public FormRadioAddon(Loader.Resource resource, Environment environment) {
		super(resource, environment);
	}

	@Override
	protected String keyword() {
		return "form:radio";
	}

	@Override
	public FormRadioTag instance() {
		return new FormRadioTag();
	}

}
