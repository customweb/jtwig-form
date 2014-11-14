package com.customweb.jtwig.form;

import org.parboiled.Rule;

import com.customweb.jtwig.form.model.Form;
import com.customweb.jtwig.lib.AttributeAddon;
import com.lyncode.jtwig.addons.AddonModel;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormAddon extends AttributeAddon<Form> {

	public FormAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	public Rule startRule() {
		return Optional(
			Sequence(
				push(new Form()),
				OneOrMore(
					FirstOf(
						variableAttribute(Form.Attributes.DATAOBJECT.name()),
						dynamicAttribute()
					)
				)
			)
		);
	}

	protected String keyword() {
		return "form";
	}

	@Override
	public java.lang.String beginKeyword() {
		return keyword();
	}

	@Override
	public java.lang.String endKeyword() {
		return "end" + keyword();
	}

	@Override
	public AddonModel<Form> instance() {
		throw new UnsupportedOperationException();
	}
	
}
