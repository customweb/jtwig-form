package com.customweb.jtwig.form.addon.element;

import com.customweb.jtwig.form.tag.element.FormHiddenTag;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormHiddenAddon extends FormElementAddon<FormHiddenTag> {

	public FormHiddenAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:hidden";
	}

	@Override
	public FormHiddenTag instance() {
		return new FormHiddenTag();
	}

}
