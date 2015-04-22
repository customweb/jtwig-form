package com.customweb.jtwig.form;

import org.jtwig.JtwigModelMap;
import org.jtwig.JtwigTemplate;
import org.jtwig.configuration.JtwigConfiguration;
import org.jtwig.configuration.JtwigConfigurationBuilder;
import org.jtwig.exception.JtwigException;

import com.customweb.jtwig.form.addon.FormAddon;

abstract public class AbstractFormTest {
	
	protected String renderTemplate(String content, JtwigModelMap map) throws JtwigException {
		JtwigConfiguration configuration = JtwigConfigurationBuilder.newConfiguration().build();
		FormAddon.addons(configuration);
		JtwigTemplate template = JtwigTemplate.inlineTemplate(content, configuration);
        return template.render(map);
	}
	
	protected String renderTemplate(String content) throws JtwigException {
		return this.renderTemplate(content, new JtwigModelMap());
	}

}