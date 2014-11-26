package com.customweb.jtwig.form.addon.element;

import com.customweb.jtwig.form.tag.element.FormPasswordTag;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormPasswordAddon extends FormElementAddon<FormPasswordTag> {

	public FormPasswordAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:password";
	}

	@Override
	public FormPasswordTag instance() {
		return new FormPasswordTag();
	}

}
