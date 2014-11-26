package com.customweb.jtwig.form.addon.element;

import com.customweb.jtwig.form.tag.element.FormTextareaTag;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormTextareaAddon extends FormElementAddon<FormTextareaTag> {

	public FormTextareaAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:textarea";
	}

	@Override
	public FormTextareaTag instance() {
		return new FormTextareaTag();
	}

}
