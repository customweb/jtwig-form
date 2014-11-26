package com.customweb.jtwig.form.addon.element;

import com.customweb.jtwig.form.tag.element.FormInputTag;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormInputAddon extends FormElementAddon<FormInputTag> {

	public FormInputAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:input";
	}

	@Override
	public FormInputTag instance() {
		return new FormInputTag();
	}

}
