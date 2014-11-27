package com.customweb.jtwig.form.model;

import com.customweb.jtwig.form.addon.FormAddon;
import com.customweb.jtwig.lib.path.IPathResolver;

public abstract class AbstractPathResolver implements IPathResolver {

	public void register() {
		FormAddon.getPathHandler().addResolver(this);
	}

}
