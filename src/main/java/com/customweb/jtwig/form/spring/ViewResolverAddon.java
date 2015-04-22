package com.customweb.jtwig.form.spring;

import javax.annotation.PostConstruct;

import org.jtwig.cache.impl.NoCacheSystem;
import org.jtwig.mvc.JtwigViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.customweb.jtwig.form.addon.FormAddon;

@Component
public final class ViewResolverAddon {
	@Autowired
	private JtwigViewResolver viewResolver;

	@PostConstruct
    public void register() {
		viewResolver.setCacheSystem(new NoCacheSystem());
		FormAddon.addons(viewResolver.getEnvironment().getConfiguration());
	}
}
