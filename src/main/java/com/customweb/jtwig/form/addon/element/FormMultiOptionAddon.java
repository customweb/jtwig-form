package com.customweb.jtwig.form.addon.element;

import com.customweb.jtwig.form.tag.element.FormMultiOptionTag;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormMultiOptionAddon extends FormElementAddon<FormMultiOptionTag> {

	public FormMultiOptionAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:multioption";
	}

	@Override
	public FormMultiOptionTag instance() {
		return new FormMultiOptionTag();
	}

}
