package com.customweb.jtwig.form.spring;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.customweb.jtwig.form.addon.FormAddon;
import com.lyncode.jtwig.mvc.JtwigViewResolver;

@Component
public final class ViewResolverAddon {
	@Autowired
	private JtwigViewResolver viewResolver;

	@PostConstruct
    public void register() {
		viewResolver.setCached(false);
		FormAddon.addons(viewResolver.configuration());
	}
}
