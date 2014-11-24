package com.customweb.jtwig.form;

import com.customweb.jtwig.form.model.FormInput;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormInputAddon extends FormElementAddon<FormInput> {

	public FormInputAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:input";
	}

	@Override
	public FormInput instance() {
		return new FormInput();
	}

}
