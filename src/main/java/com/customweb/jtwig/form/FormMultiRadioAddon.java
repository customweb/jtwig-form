package com.customweb.jtwig.form;

import com.customweb.jtwig.form.model.FormMultiRadio;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormMultiRadioAddon extends FormElementAddon<FormMultiRadio> {

	public FormMultiRadioAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:multiradio";
	}

	@Override
	public FormMultiRadio instance() {
		return new FormMultiRadio();
	}

}
