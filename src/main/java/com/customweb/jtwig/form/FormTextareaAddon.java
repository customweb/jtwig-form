package com.customweb.jtwig.form;

import com.customweb.jtwig.form.model.FormTextarea;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormTextareaAddon extends FormElementAddon<FormTextarea> {

	public FormTextareaAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:textarea";
	}

	@Override
	public FormTextarea instance() {
		return new FormTextarea();
	}

}
