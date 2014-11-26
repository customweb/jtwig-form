package com.customweb.jtwig.form.addon.element;

import com.customweb.jtwig.form.tag.element.FormOptionTag;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormOptionAddon extends FormElementAddon<FormOptionTag> {

	public FormOptionAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:option";
	}

	@Override
	public FormOptionTag instance() {
		return new FormOptionTag();
	}

}
