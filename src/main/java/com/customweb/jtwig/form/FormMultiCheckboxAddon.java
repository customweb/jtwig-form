package com.customweb.jtwig.form;

import com.customweb.jtwig.form.model.FormMultiCheckbox;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormMultiCheckboxAddon extends FormElementAddon<FormMultiCheckbox> {

	public FormMultiCheckboxAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:multicheckbox";
	}

	@Override
	public FormMultiCheckbox instance() {
		return new FormMultiCheckbox();
	}

}
