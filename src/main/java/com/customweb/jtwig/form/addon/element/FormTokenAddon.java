package com.customweb.jtwig.form.addon.element;

import org.jtwig.Environment;
import org.jtwig.loader.Loader;

import com.customweb.jtwig.form.model.AbstractTokenGenerator;
import com.customweb.jtwig.form.tag.element.FormTokenTag;

public class FormTokenAddon extends FormElementAddon<FormTokenTag> {
	
	private static AbstractTokenGenerator tokenGenerator;
	
	public static void setTokenGeneratorClass(Class<? extends AbstractTokenGenerator> tokenGeneratorClass) throws InstantiationException, IllegalAccessException {
		tokenGenerator = tokenGeneratorClass.newInstance();
	}
	
	public static AbstractTokenGenerator getTokenGenerator() {
		if (tokenGenerator == null) {
			throw new RuntimeException("The token generator class has not been defined.");
		}
		return tokenGenerator;
	}

	public FormTokenAddon(Loader.Resource resource, Environment environment) {
		super(resource, environment);
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