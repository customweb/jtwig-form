package com.customweb.jtwig.form.addon.element;

import com.customweb.jtwig.form.model.AbstractTokenGenerator;
import com.customweb.jtwig.form.tag.element.FormTokenTag;
import com.lyncode.jtwig.parser.config.ParserConfiguration;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormTokenAddon extends FormElementAddon<FormTokenTag> {
	
	private static AbstractTokenGenerator tokenGenerator;
	
	public static void setTokenGeneratorClass(Class<AbstractTokenGenerator> tokenGeneratorClass) throws InstantiationException, IllegalAccessException {
		tokenGenerator = tokenGeneratorClass.newInstance();
	}
	
	public static AbstractTokenGenerator getTokenGenerator() {
		if (tokenGenerator == null) {
			throw new RuntimeException("The token generator class has not been defined.");
		}
		return tokenGenerator;
	}

	public FormTokenAddon(JtwigResource resource, ParserConfiguration configuration) {
		super(resource, configuration);
	}

	@Override
	protected String keyword() {
		return "form:token";
	}

	@Override
	public FormTokenTag instance() {
		return new FormTokenTag(tokenGenerator);
	}

}