package com.customweb.jtwig.form;


import static org.junit.Assert.*;

import org.junit.Test;

import com.customweb.jtwig.form.addon.FormAddon;
import com.customweb.jtwig.form.model.AbstractPathResolver;
import com.customweb.jtwig.form.model.AbstractResourceResolver;
import com.customweb.jtwig.lib.path.ResolverException;
import com.lyncode.jtwig.JtwigModelMap;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;
import com.lyncode.jtwig.exception.ResourceException;
import com.lyncode.jtwig.resource.ClasspathJtwigResource;
import com.lyncode.jtwig.resource.JtwigResource;

public class FormTest extends AbstractFormTest {
	
	@Test
	public void empty() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:form %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"formModel\" action=\"\" method=\"post\" ></form>", output);
	}
	
	@Test
	public void emptyActionAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:form action=\"\" %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"formModel\" action=\"\" method=\"post\" ></form>", output);
	}
	
	@Test
	public void actionAttribute() throws ParseException, CompileException, RenderException {
        String output = renderTemplate("{% form:form action=\"myaction\" %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"formModel\" action=\"myaction\" method=\"post\" ></form>", output);
	}
	
	@Test
	public void emptyMethodAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:form method=\"\" %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"formModel\" action=\"\" method=\"post\" ></form>", output);
	}
	
	@Test
	public void methodAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:form method=\"get\" %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"formModel\" action=\"\" method=\"get\" ></form>", output);
	}
	
	@Test
	public void modelAttribute() throws ParseException, CompileException, RenderException {
		JtwigModelMap map = new JtwigModelMap();
		map.add("myModel", new Object());
		String output = renderTemplate("{% form:form model=\"myModel\" %}{% endform:form %}", map);
        assertEquals("<form id=\"myModel\" action=\"\" method=\"post\" ></form>", output);
	}
	
	@Test
	public void dynamicAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:form key=\"value\" %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"formModel\" action=\"\" method=\"post\" key=\"value\"></form>", output);
	}
	
	@Test
	public void pathResolver() throws ParseException, CompileException, RenderException {
		new PathResolver().register();
		String output = renderTemplate("{% form:form action=\"myaction\" %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"formModel\" action=\"prefix://myaction\" method=\"post\" ></form>", output);
        FormAddon.getPathHandler().reset();
	}
	
	public static class PathResolver extends AbstractPathResolver {
		@Override
		public String resolve(String relativePath) throws ResolverException {
			return "prefix://" + relativePath;
		}
	}
	
	@Test
	public void resourceResolver() throws ParseException, CompileException, RenderException {
		new ResourceResolver().register();
		String output = renderTemplate("{% form:form %}{% endform:form %}", this.getDefaultMap());
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
	
	private JtwigModelMap getDefaultMap() {
		JtwigModelMap map = new JtwigModelMap();
		map.add("formModel", new Object());
		return map;
	}

}