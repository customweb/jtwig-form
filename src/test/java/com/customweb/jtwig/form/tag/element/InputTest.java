package com.customweb.jtwig.form.tag.element;

import static org.junit.Assert.assertEquals;

import org.jtwig.JtwigModelMap;
import org.jtwig.exception.JtwigException;
import org.jtwig.exception.ParseException;
import org.junit.Test;

import com.customweb.jtwig.form.AbstractFormTest;

public class InputTest extends AbstractFormTest {
		
	@Test
	public void inFormContext() throws JtwigException {
		String output = renderTemplate("{% form:form model=\"myModel\" %}{% form:input path=\"stringValue\" %}{% endform:input %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"myModel\" action=\"\" method=\"post\"   ><input id=\"stringValue\" name=\"stringValue\"  type=\"text\" value=\"stringValue\"    /></form>", output);
	}
	
	@Test
	public void nameAttribute() throws JtwigException {
		String output = renderTemplate("{% form:input path=\"myModel.stringValue\" name=\"myName\" %}{% endform:input %}", this.getDefaultMap());
        assertEquals("<input id=\"myName\" name=\"myName\"  type=\"text\" value=\"stringValue\"    />", output);
	}
	
	@Test
	public void disabledAttribute() throws JtwigException {
		String output = renderTemplate("{% form:input path=\"myModel.stringValue\" disabled=\"true\" %}{% endform:input %}", this.getDefaultMap());
        assertEquals("<input id=\"stringValue\" name=\"stringValue\" disabled=\"disabled\" type=\"text\" value=\"stringValue\"    />", output);
	}
	
	@Test
	public void idAttribute() throws JtwigException {
		String output = renderTemplate("{% form:input path=\"myModel.stringValue\" id=\"myId\" %}{% endform:input %}", this.getDefaultMap());
        assertEquals("<input id=\"myId\" name=\"stringValue\"  type=\"text\" value=\"stringValue\"    />", output);
	}
	
	@Test(expected = ParseException.class)
	public void missingPathAttribute() throws JtwigException {
		renderTemplate("{% form:input %}{% endform:input %}", this.getDefaultMap());
	}
	
	@Test(expected = ParseException.class)
	public void disallowedDynamicAttribute() throws JtwigException {
		renderTemplate("{% form:input path=\"myModel.stringValue\" type=\"test\" %}{% endform:input %}", this.getDefaultMap());
	}
	
	@Test
	public void dynamicAttribute() throws JtwigException {
		String output = renderTemplate("{% form:input path=\"myModel.stringValue\" key=\"value\" %}{% endform:input %}", this.getDefaultMap());
        assertEquals("<input id=\"stringValue\" name=\"stringValue\"  type=\"text\" value=\"stringValue\"   key=\"value\" />", output);
	}
	
	@Test
	public void modelInheritance() throws JtwigException {
		JtwigModelMap map = new JtwigModelMap();
		map.add("myModel", new Client());
		String output = renderTemplate("{% form:input path=\"myModel.username\" %}{% endform:input %}", map);
        assertEquals("<input id=\"username\" name=\"username\"  type=\"text\" value=\"foo\"    />", output);
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
	
	public static class User {
	    public long id = 1;
	    public String username = "foo";
	}
	
	public static class Client extends User {
	    public String type = "bar";
	}
}
