package com.customweb.jtwig.form;

import com.customweb.jtwig.form.model.FormSelect;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormSelectAddon extends FormElementAddon<FormSelect> {

	public FormSelectAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:select";
	}

	@Override
	public FormSelect instance() {
		return new FormSelect();
	}

}
