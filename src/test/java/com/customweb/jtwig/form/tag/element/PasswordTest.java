package com.customweb.jtwig.form.tag.element;

import static org.junit.Assert.assertEquals;

import org.jtwig.JtwigModelMap;
import org.jtwig.exception.JtwigException;
import org.jtwig.exception.ParseException;
import org.junit.Test;

import com.customweb.jtwig.form.AbstractFormTest;

public class PasswordTest extends AbstractFormTest {
	
	@Test
	public void inFormContext() throws JtwigException {
		String output = renderTemplate("{% form:form model=\"myModel\" %}{% form:password path=\"stringValue\" %}{% endform:password %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"myModel\" action=\"\" method=\"post\"   ><input id=\"stringValue\" name=\"stringValue\"  type=\"password\"     /></form>", output);
	}
	
	@Test
	public void nameAttribute() throws JtwigException {
		String output = renderTemplate("{% form:password path=\"myModel.stringValue\" name=\"myName\" %}{% endform:password %}", this.getDefaultMap());
        assertEquals("<input id=\"myName\" name=\"myName\"  type=\"password\"     />", output);
	}
	
	@Test
	public void disabledAttribute() throws JtwigException {
		String output = renderTemplate("{% form:password path=\"myModel.stringValue\" disabled=\"true\" %}{% endform:password %}", this.getDefaultMap());
        assertEquals("<input id=\"stringValue\" name=\"stringValue\" disabled=\"disabled\" type=\"password\"     />", output);
	}
	
	@Test
	public void idAttribute() throws JtwigException {
		String output = renderTemplate("{% form:password path=\"myModel.stringValue\" id=\"myId\" %}{% endform:password %}", this.getDefaultMap());
        assertEquals("<input id=\"myId\" name=\"stringValue\"  type=\"password\"     />", output);
	}
	
	@Test
	public void showPasswordAttribute() throws JtwigException {
		String output = renderTemplate("{% form:password path=\"myModel.stringValue\" showPassword %}{% endform:password %}", this.getDefaultMap());
        assertEquals("<input id=\"stringValue\" name=\"stringValue\"  type=\"password\" value=\"stringValue\"    />", output);
	}
	
	@Test(expected = ParseException.class)
	public void missingPathAttribute() throws JtwigException {
		renderTemplate("{% form:password %}{% endform:password %}", this.getDefaultMap());
	}
	
	@Test(expected = ParseException.class)
	public void disallowedDynamicAttribute() throws JtwigException {
		renderTemplate("{% form:password path=\"myModel.stringValue\" type=\"test\" %}{% endform:password %}", this.getDefaultMap());
	}
	
	@Test
	public void dynamicAttribute() throws JtwigException {
		String output = renderTemplate("{% form:password path=\"myModel.stringValue\" key=\"value\" %}{% endform:password %}", this.getDefaultMap());
        assertEquals("<input id=\"stringValue\" name=\"stringValue\"  type=\"password\"    key=\"value\" />", output);
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
