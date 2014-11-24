package com.customweb.jtwig.form;

import com.customweb.jtwig.form.model.FormPassword;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormPasswordAddon extends FormElementAddon<FormPassword> {

	public FormPasswordAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:password";
	}

	@Override
	public FormPassword instance() {
		return new FormPassword();
	}

}
