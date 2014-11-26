package com.customweb.jtwig.form.addon.element;

import com.customweb.jtwig.form.tag.element.FormLabelTag;
import com.customweb.jtwig.lib.AttributeAddon;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormLabelAddon extends AttributeAddon<FormLabelTag> {

	public FormLabelAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:label";
	}

	@Override
	public FormLabelTag instance() {
		return new FormLabelTag();
	}

}
