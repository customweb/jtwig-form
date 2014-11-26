package com.customweb.jtwig.form.addon.element;

import com.customweb.jtwig.form.tag.element.FormTokenTag;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormTokenAddon extends FormElementAddon<FormTokenTag> {

	public FormTokenAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:token";
	}

	@Override
	public FormTokenTag instance() {
		return new FormTokenTag();
	}

}
