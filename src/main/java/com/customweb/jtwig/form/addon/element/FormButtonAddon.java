package com.customweb.jtwig.form.addon.element;

import com.customweb.jtwig.form.tag.element.FormButtonTag;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormButtonAddon extends FormElementAddon<FormButtonTag> {

	public FormButtonAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:button";
	}

	@Override
	public FormButtonTag instance() {
		return new FormButtonTag();
	}

}
