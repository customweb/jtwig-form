package com.customweb.jtwig.form;

import com.customweb.jtwig.form.model.FormLabel;
import com.customweb.jtwig.lib.AttributeAddon;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormLabelAddon extends AttributeAddon<FormLabel> {

	public FormLabelAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:label";
	}

	@Override
	public FormLabel instance() {
		return new FormLabel();
	}

}
