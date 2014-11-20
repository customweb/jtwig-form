package com.customweb.jtwig.form;

import com.customweb.jtwig.form.model.Form;
import com.customweb.jtwig.lib.AttributeAddon;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormAddon extends AttributeAddon<Form> {

	public FormAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:form";
	}

	@Override
	public Form instance() {
		return new Form();
	}

}
