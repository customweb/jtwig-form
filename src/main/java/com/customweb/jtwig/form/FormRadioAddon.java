package com.customweb.jtwig.form;

import com.customweb.jtwig.form.model.FormRadio;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormRadioAddon extends FormElementAddon<FormRadio> {

	public FormRadioAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:radio";
	}

	@Override
	public FormRadio instance() {
		return new FormRadio();
	}

}
