package com.customweb.jtwig.form.model;

import com.customweb.jtwig.lib.template.IResourceResolver;
import com.lyncode.jtwig.exception.ResourceException;
import com.lyncode.jtwig.resource.ClasspathJtwigResource;
import com.lyncode.jtwig.resource.JtwigResource;

public class DefaultResourceResolver implements IResourceResolver {

	@Override
	public JtwigResource resolve(String resourceName) throws ResourceException {
		JtwigResource resource = new ClasspathJtwigResource("classpath:/views/form/" + resourceName + ".twig");
		if (!resource.exists()) {
			throw new ResourceException("Form resource '" + resourceName + "' not found");
		}
		return resource;
	}

}
