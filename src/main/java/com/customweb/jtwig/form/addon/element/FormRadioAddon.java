package com.customweb.jtwig.form.addon.element;

import com.customweb.jtwig.form.tag.element.FormRadioTag;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormRadioAddon extends FormElementAddon<FormRadioTag> {

	public FormRadioAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:radio";
	}

	@Override
	public FormRadioTag instance() {
		return new FormRadioTag();
	}

}
