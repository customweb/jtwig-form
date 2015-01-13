package com.customweb.jtwig.form.tag.element;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.customweb.jtwig.form.AbstractFormTest;
import com.lyncode.jtwig.JtwigModelMap;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;

public class TextareaTest extends AbstractFormTest {
	
	@Test
	public void inFormContext() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:form model=\"myModel\" %}{% form:textarea path=\"stringValue\" %}{% endform:textarea %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"myModel\" action=\"\" method=\"post\"   ><textarea id=\"stringValue\" name=\"stringValue\"    >stringValue</textarea></form>", output);
	}
	
	@Test
	public void nameAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:textarea path=\"myModel.stringValue\" name=\"myName\" %}{% endform:textarea %}", this.getDefaultMap());
        assertEquals("<textarea id=\"myName\" name=\"myName\"    >stringValue</textarea>", output);
	}
	
	@Test
	public void disabledAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:textarea path=\"myModel.stringValue\" disabled=\"true\" %}{% endform:textarea %}", this.getDefaultMap());
        assertEquals("<textarea id=\"stringValue\" name=\"stringValue\" disabled=\"disabled\"   >stringValue</textarea>", output);
	}
	
	@Test
	public void idAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:textarea path=\"myModel.stringValue\" id=\"myId\" %}{% endform:textarea %}", this.getDefaultMap());
        assertEquals("<textarea id=\"myId\" name=\"stringValue\"    >stringValue</textarea>", output);
	}
	
	@Test(expected = ParseException.class)
	public void missingPathAttribute() throws ParseException, CompileException, RenderException {
		renderTemplate("{% form:textarea %}{% endform:textarea %}", this.getDefaultMap());
	}
	
	@Test
	public void dynamicAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:textarea path=\"myModel.stringValue\" key=\"value\" %}{% endform:textarea %}", this.getDefaultMap());
        assertEquals("<textarea id=\"stringValue\" name=\"stringValue\"    key=\"value\">stringValue</textarea>", output);
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
