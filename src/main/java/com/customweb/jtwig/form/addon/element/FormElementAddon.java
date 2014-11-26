package com.customweb.jtwig.form.addon.element;

import com.customweb.jtwig.form.tag.AbstractFormElementTag;
import com.customweb.jtwig.lib.attribute.AttributeAddon;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public abstract class FormElementAddon<T extends AbstractFormElementTag<T>> extends AttributeAddon<T> {

	public FormElementAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

}