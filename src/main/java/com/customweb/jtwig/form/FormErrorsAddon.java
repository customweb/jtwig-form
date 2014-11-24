package com.customweb.jtwig.form;

import com.customweb.jtwig.form.model.FormErrors;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormErrorsAddon extends FormElementAddon<FormErrors> {

	public FormErrorsAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:errors";
	}

	@Override
	public FormErrors instance() {
		return new FormErrors();
	}

}
