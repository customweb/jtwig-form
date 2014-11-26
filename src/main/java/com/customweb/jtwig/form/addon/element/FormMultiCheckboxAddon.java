package com.customweb.jtwig.form.addon.element;

import com.customweb.jtwig.form.tag.element.FormMultiCheckboxTag;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormMultiCheckboxAddon extends FormElementAddon<FormMultiCheckboxTag> {

	public FormMultiCheckboxAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:multicheckbox";
	}

	@Override
	public FormMultiCheckboxTag instance() {
		return new FormMultiCheckboxTag();
	}

}
