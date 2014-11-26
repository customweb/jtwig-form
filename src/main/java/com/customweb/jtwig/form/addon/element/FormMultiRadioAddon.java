package com.customweb.jtwig.form.addon.element;

import com.customweb.jtwig.form.tag.element.FormMultiRadioTag;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormMultiRadioAddon extends FormElementAddon<FormMultiRadioTag> {

	public FormMultiRadioAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:multiradio";
	}

	@Override
	public FormMultiRadioTag instance() {
		return new FormMultiRadioTag();
	}

}
