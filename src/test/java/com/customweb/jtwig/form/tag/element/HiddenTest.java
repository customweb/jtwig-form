package com.customweb.jtwig.form.tag.element;

import static org.junit.Assert.assertEquals;

import org.jtwig.JtwigModelMap;
import org.jtwig.exception.JtwigException;
import org.jtwig.exception.ParseException;
import org.junit.Test;

import com.customweb.jtwig.form.AbstractFormTest;

public class HiddenTest extends AbstractFormTest {
	
	@Test
	public void inFormContext() throws JtwigException {
		String output = renderTemplate("{% form:form model=\"myModel\" %}{% form:hidden path=\"stringValue\" %}{% endform:hidden %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"myModel\" action=\"\" method=\"post\"   ><input id=\"stringValue\" name=\"stringValue\" type=\"hidden\" value=\"stringValue\"    /></form>", output);
	}
	
	@Test
	public void nameAttribute() throws JtwigException {
		String output = renderTemplate("{% form:hidden path=\"myModel.stringValue\" name=\"myName\" %}{% endform:hidden %}", this.getDefaultMap());
        assertEquals("<input id=\"myName\" name=\"myName\" type=\"hidden\" value=\"stringValue\"    />", output);
	}
	
	@Test
	public void idAttribute() throws JtwigException {
		String output = renderTemplate("{% form:hidden path=\"myModel.stringValue\" id=\"myId\" %}{% endform:hidden %}", this.getDefaultMap());
        assertEquals("<input id=\"myId\" name=\"stringValue\" type=\"hidden\" value=\"stringValue\"    />", output);
	}
	
	@Test(expected = ParseException.class)
	public void missingPathAttribute() throws JtwigException {
		renderTemplate("{% form:hidden %}{% endform:hidden %}", this.getDefaultMap());
	}
	
	@Test(expected = ParseException.class)
	public void disallowedDynamicAttribute() throws JtwigException {
		renderTemplate("{% form:hidden path=\"myModel.stringValue\" type=\"test\" %}{% endform:hidden %}", this.getDefaultMap());
	}
	
	@Test
	public void dynamicAttribute() throws JtwigException {
		String output = renderTemplate("{% form:hidden path=\"myModel.stringValue\" key=\"value\" %}{% endform:hidden %}", this.getDefaultMap());
        assertEquals("<input id=\"stringValue\" name=\"stringValue\" type=\"hidden\" value=\"stringValue\"   key=\"value\" />", output);
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
