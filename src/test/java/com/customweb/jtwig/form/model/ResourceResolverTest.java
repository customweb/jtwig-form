package com.customweb.jtwig.form.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.customweb.jtwig.form.AbstractFormTest;
import com.customweb.jtwig.form.addon.FormAddon;
import com.lyncode.jtwig.JtwigModelMap;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.exception.ResourceException;
import com.lyncode.jtwig.resource.ClasspathJtwigResource;
import com.lyncode.jtwig.resource.JtwigResource;

public class ResourceResolverTest extends AbstractFormTest {

	@Test
	public void resourceResolver() throws ParseException, CompileException, RenderException {
		new ResourceResolver().register();
		JtwigModelMap map = new JtwigModelMap();
		map.add("formModel", new Object());
		String output = renderTemplate("{% form:form %}{% endform:form %}", map);
        assertEquals("<div id=\"formModel\"></div>", output);
        FormAddon.getResourceHandler().reset();
	}
	
	public static class ResourceResolver extends AbstractResourceResolver {
		@Override
		public JtwigResource resolve(String resourceName) throws ResourceException {
			JtwigResource resource = new ClasspathJtwigResource("/views/formTest/" + resourceName + ".twig");
			if (!resource.exists()) {
				throw new ResourceException("Resource '" + resourceName + "' not found");
			}
			return resource;
		}
	}
}
