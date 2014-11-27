package com.customweb.jtwig.form.model;

import com.customweb.jtwig.form.addon.FormAddon;
import com.customweb.jtwig.lib.template.IResourceResolver;

public abstract class AbstractResourceResolver implements IResourceResolver {

	public void register() {
		FormAddon.getResourceHandler().addResolver(this);
	}

}
