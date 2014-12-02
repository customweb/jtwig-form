package com.customweb.jtwig.form.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.customweb.jtwig.form.AbstractFormTest;
import com.customweb.jtwig.form.addon.FormAddon;
import com.customweb.jtwig.lib.path.ResolverException;
import com.lyncode.jtwig.JtwigModelMap;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;

public class PathResolverTest extends AbstractFormTest {

	@Test
	public void pathResolver() throws ParseException, CompileException, RenderException {
		new PathResolver().register();
		JtwigModelMap map = new JtwigModelMap();
		map.add("formModel", new Object());
		String output = renderTemplate("{% form:form action=\"myaction\" %}{% endform:form %}", map);
        assertEquals("<form id=\"formModel\" action=\"prefix://myaction\" method=\"post\" ></form>", output);
        FormAddon.getPathHandler().reset();
	}
	
	public static class PathResolver extends AbstractPathResolver {
		@Override
		public String resolve(String relativePath) throws ResolverException {
			return "prefix://" + relativePath;
		}
	}
}
