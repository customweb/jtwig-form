package com.customweb.jtwig.form;

import com.customweb.jtwig.form.model.FormToken;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormTokenAddon extends FormElementAddon<FormToken> {

	public FormTokenAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:token";
	}

	@Override
	public FormToken instance() {
		return new FormToken();
	}

}
