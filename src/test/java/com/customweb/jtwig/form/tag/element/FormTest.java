package com.customweb.jtwig.form.tag.element;


import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.jtwig.JtwigModelMap;
import org.jtwig.JtwigTemplate;
import org.jtwig.configuration.JtwigConfiguration;
import org.jtwig.configuration.JtwigConfigurationBuilder;
import org.jtwig.exception.JtwigException;
import org.jtwig.exception.ResourceException;
import org.jtwig.loader.Loader;
import org.jtwig.loader.Loader.Resource;
import org.jtwig.loader.impl.ClasspathLoader;
import org.junit.Test;

import com.customweb.jtwig.form.AbstractFormTest;
import com.customweb.jtwig.form.addon.FormAddon;

public class FormTest extends AbstractFormTest {
	
	@Test
	public void empty() throws JtwigException {
		String output = renderTemplate("{% form:form %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"formModel\" action=\"\" method=\"post\"   ></form>", output);
	}
	
	@Test
	public void emptyActionAttribute() throws JtwigException {
		String output = renderTemplate("{% form:form action=\"\" %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"formModel\" action=\"\" method=\"post\"   ></form>", output);
	}
	
	@Test
	public void actionAttribute() throws JtwigException {
        String output = renderTemplate("{% form:form action=\"myaction\" %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"formModel\" action=\"myaction\" method=\"post\"   ></form>", output);
	}
	
	@Test
	public void emptyMethodAttribute() throws JtwigException {
		String output = renderTemplate("{% form:form method=\"\" %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"formModel\" action=\"\" method=\"post\"   ></form>", output);
	}
	
	@Test
	public void methodAttribute() throws JtwigException {
		String output = renderTemplate("{% form:form method=\"get\" %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"formModel\" action=\"\" method=\"get\"   ></form>", output);
	}
	
	@Test
	public void modelAttribute() throws JtwigException {
		JtwigModelMap map = new JtwigModelMap();
		map.add("myModel", new Object());
		String output = renderTemplate("{% form:form model=\"myModel\" %}{% endform:form %}", map);
        assertEquals("<form id=\"myModel\" action=\"\" method=\"post\"   ></form>", output);
	}
	
	@Test
	public void dynamicAttribute() throws JtwigException {
		String output = renderTemplate("{% form:form key=\"value\" %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"formModel\" action=\"\" method=\"post\"   key=\"value\"></form>", output);
	}
	
	@Test
	public void customLoader() throws JtwigException {
		JtwigConfiguration configuration = JtwigConfigurationBuilder.newConfiguration().withLoader(new CustomLoader()).build();
		FormAddon.addons(configuration);
		JtwigTemplate template = JtwigTemplate.inlineTemplate("{% form:form %}{% endform:form %}", configuration);
        String output = template.render(this.getDefaultMap());
        assertEquals("<div class=\"form\" id=\"formModel\"></div>", output);
	}
	
	private JtwigModelMap getDefaultMap() {
		JtwigModelMap map = new JtwigModelMap();
		map.add("formModel", new Object());
		return map;
	}
	
	public static class CustomLoader extends Loader {
		private Loader internalLoader = new ClasspathLoader();

		@Override
		public boolean exists(String name) throws ResourceException {
			return this.internalLoader.exists(this.normalizeName(name));
		}

		@Override
		public Resource get(String name) throws ResourceException {
			return this.internalLoader.get(this.normalizeName(name));
		}
		
		protected String normalizeName(String name) {
	        if (StringUtils.startsWithIgnoreCase(name, "tpl:")) {
	            name = StringUtils.removeStartIgnoreCase(name, "tpl:");
	        }
	       return "/views/formTest/" + name + ".twig";
	    }
	}

}