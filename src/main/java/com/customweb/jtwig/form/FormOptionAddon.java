package com.customweb.jtwig.form;

import com.customweb.jtwig.form.model.FormOption;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormOptionAddon extends FormElementAddon<FormOption> {

	public FormOptionAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:option";
	}

	@Override
	public FormOption instance() {
		return new FormOption();
	}

}
