package com.customweb.jtwig.form;

import com.customweb.jtwig.form.model.FormButton;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormButtonAddon extends FormElementAddon<FormButton> {

	public FormButtonAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:button";
	}

	@Override
	public FormButton instance() {
		return new FormButton();
	}

}
