package com.customweb.jtwig.form.addon.element;

import com.customweb.jtwig.form.tag.element.FormCheckboxTag;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormCheckboxAddon extends FormElementAddon<FormCheckboxTag> {

	public FormCheckboxAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:checkbox";
	}

	@Override
	public FormCheckboxTag instance() {
		return new FormCheckboxTag();
	}

}
