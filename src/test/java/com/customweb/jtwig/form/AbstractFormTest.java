package com.customweb.jtwig.form;

import com.customweb.jtwig.form.addon.FormAddon;
import com.lyncode.jtwig.JtwigModelMap;
import com.lyncode.jtwig.JtwigTemplate;
import com.lyncode.jtwig.configuration.JtwigConfiguration;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;

abstract public class AbstractFormTest {
	
	protected String renderTemplate(String content, JtwigModelMap map) throws ParseException, CompileException, RenderException {
		JtwigConfiguration config = new JtwigConfiguration();
		FormAddon.addons(config);
		JtwigTemplate template = new JtwigTemplate(content, config);
        return template.output(map);
	}
	
	protected String renderTemplate(String content) throws ParseException, CompileException, RenderException {
		return this.renderTemplate(content, new JtwigModelMap());
	}

}