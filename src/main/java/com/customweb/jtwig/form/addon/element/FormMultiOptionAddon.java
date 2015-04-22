package com.customweb.jtwig.form.addon.element;

import org.jtwig.Environment;
import org.jtwig.loader.Loader;

import com.customweb.jtwig.form.tag.element.FormMultiOptionTag;

public class FormMultiOptionAddon extends FormElementAddon<FormMultiOptionTag> {

	public FormMultiOptionAddon(Loader.Resource resource, Environment environment) {
		super(resource, environment);
	}

	@Override
	protected String keyword() {
		return "form:multioption";
	}

	@Override
	public FormMultiOptionTag instance() {
		return new FormMultiOptionTag();
	}

}
