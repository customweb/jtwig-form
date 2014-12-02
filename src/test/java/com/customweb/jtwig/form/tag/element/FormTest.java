package com.customweb.jtwig.form.tag.element;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.customweb.jtwig.form.AbstractFormTest;
import com.lyncode.jtwig.JtwigModelMap;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;

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
	
	private JtwigModelMap getDefaultMap() {
		JtwigModelMap map = new JtwigModelMap();
		map.add("formModel", new Object());
		return map;
	}

}