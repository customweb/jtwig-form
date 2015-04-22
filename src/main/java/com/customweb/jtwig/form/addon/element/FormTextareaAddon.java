package com.customweb.jtwig.form.addon.element;

import org.jtwig.Environment;
import org.jtwig.loader.Loader;

import com.customweb.jtwig.form.tag.element.FormTextareaTag;

public class FormTextareaAddon extends FormElementAddon<FormTextareaTag> {

	public FormTextareaAddon(Loader.Resource resource, Environment environment) {
		super(resource, environment);
	}

	@Override
	protected String keyword() {
		return "form:textarea";
	}

	@Override
	public FormTextareaTag instance() {
		return new FormTextareaTag();
	}

}
