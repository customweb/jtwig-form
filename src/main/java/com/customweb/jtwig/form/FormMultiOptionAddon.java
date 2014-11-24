package com.customweb.jtwig.form;

import com.customweb.jtwig.form.model.FormMultiOption;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormMultiOptionAddon extends FormElementAddon<FormMultiOption> {

	public FormMultiOptionAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:multioption";
	}

	@Override
	public FormMultiOption instance() {
		return new FormMultiOption();
	}

}
