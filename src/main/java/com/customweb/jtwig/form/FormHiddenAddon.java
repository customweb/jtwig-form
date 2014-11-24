package com.customweb.jtwig.form;

import com.customweb.jtwig.form.model.FormHidden;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormHiddenAddon extends FormElementAddon<FormHidden> {

	public FormHiddenAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:hidden";
	}

	@Override
	public FormHidden instance() {
		return new FormHidden();
	}

}
