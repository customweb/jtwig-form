package com.customweb.jtwig.form.tag.element;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.customweb.jtwig.form.AbstractFormTest;
import com.lyncode.jtwig.JtwigModelMap;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;

public class OptionTest extends AbstractFormTest {
	
	@Test
	public void inFormContext() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:form model=\"myModel\" %}{% form:select path=\"stringValue\" %}{% form:option value=\"stringValue\" %}Label{% endform:option %}{% endform:select %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"myModel\" action=\"\" method=\"post\" ><select id=\"stringValue\" name=\"stringValue\"   ><option  value=\"stringValue\" selected=\"selected\" >Label</option></select></form>", output);
	}
	
	@Test
	public void disabledAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:select path=\"myModel.stringValue\" %}{% form:option value=\"stringValue\" disabled %}Label{% endform:option %}{% endform:select %}", this.getDefaultMap());
        assertEquals("<select id=\"stringValue\" name=\"stringValue\"   ><option disabled=\"disabled\" value=\"stringValue\" selected=\"selected\" >Label</option></select>", output);
	}
	
	@Test(expected = ParseException.class)
	public void missingValueAttribute() throws ParseException, CompileException, RenderException {
		renderTemplate("{% form:select path=\"myModel.stringValue\" %}{% form:option %}{% endform:option %}{% endform:select %}", this.getDefaultMap());
	}
	
	@Test(expected = RuntimeException.class)
	public void notInSelectContext() throws ParseException, CompileException, RenderException {
		renderTemplate("{% form:option value=\"stringValue\" %}{% endform:option %}", this.getDefaultMap());
	}
	
	@Test(expected = ParseException.class)
	public void disallowedDynamicAttribute() throws ParseException, CompileException, RenderException {
		renderTemplate("{% form:option value=\"stringValue\" selected=\"selected\" %}Label{% endform:option %}", this.getDefaultMap());
	}

	@Test
	public void dynamicAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:select path=\"myModel.stringValue\" %}{% form:option value=\"stringValue\" key=\"value\" %}Label{% endform:option %}{% endform:select %}", this.getDefaultMap());
        assertEquals("<select id=\"stringValue\" name=\"stringValue\"   ><option  value=\"stringValue\" selected=\"selected\" key=\"value\">Label</option></select>", output);
	}
	
	private JtwigModelMap getDefaultMap() {
		JtwigModelMap map = new JtwigModelMap();
		map.add("myModel", new Model());
		return map;
	}
	
	public static class Model {
		public String getStringValue() {
			return "stringValue";
		}
	}
	
}
