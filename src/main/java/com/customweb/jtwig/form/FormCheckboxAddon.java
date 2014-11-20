package com.customweb.jtwig.form;

import com.customweb.jtwig.form.model.FormCheckbox;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormCheckboxAddon extends FormElementAddon<FormCheckbox> {

	public FormCheckboxAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "checkbox";
	}

	@Override
	public FormCheckbox instance() {
		return new FormCheckbox();
	}

}
