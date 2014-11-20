package com.customweb.jtwig.form;

import com.customweb.jtwig.form.model.FormElement;
import com.customweb.jtwig.lib.AttributeAddon;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public abstract class FormElementAddon<T extends FormElement<T>> extends AttributeAddon<T> {

	public FormElementAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

}