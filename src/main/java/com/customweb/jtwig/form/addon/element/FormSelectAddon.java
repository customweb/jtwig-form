package com.customweb.jtwig.form.addon.element;

import com.customweb.jtwig.form.tag.element.FormSelectTag;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormSelectAddon extends FormElementAddon<FormSelectTag> {

	public FormSelectAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:select";
	}

	@Override
	public FormSelectTag instance() {
		return new FormSelectTag();
	}

}
