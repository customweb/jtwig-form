package com.customweb.jtwig.form.addon.element;

import org.jtwig.Environment;
import org.jtwig.loader.Loader;

import com.customweb.jtwig.form.tag.element.FormMultiRadioTag;

public class FormMultiRadioAddon extends FormElementAddon<FormMultiRadioTag> {

	public FormMultiRadioAddon(Loader.Resource resource, Environment environment) {
		super(resource, environment);
	}

	@Override
	protected String keyword() {
		return "form:multiradio";
	}

	@Override
	public FormMultiRadioTag instance() {
		return new FormMultiRadioTag();
	}

}
