package com.customweb.jtwig.form.addon.element;

import com.customweb.jtwig.form.tag.element.FormErrorsTag;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormErrorsAddon extends FormElementAddon<FormErrorsTag> {

	public FormErrorsAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:errors";
	}

	@Override
	public FormErrorsTag instance() {
		return new FormErrorsTag();
	}

}
