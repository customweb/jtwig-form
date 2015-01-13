package com.customweb.jtwig.form.tag.element;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.customweb.jtwig.form.AbstractFormTest;
import com.lyncode.jtwig.JtwigModelMap;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;

public class CheckboxTest extends AbstractFormTest {
	
	@Test
	public void inFormContext() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:form model=\"myModel\" %}{% form:checkbox path=\"falseValue\" %}{% endform:checkbox %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"myModel\" action=\"\" method=\"post\"   ><input id=\"falseValue1\" name=\"falseValue\"  type=\"checkbox\" value=\"true\"     /></form>", output);
	}
	
	@Test
	public void labelAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:checkbox path=\"myModel.falseValue\" label=\"myLabel\" %}{% endform:checkbox %}", this.getDefaultMap());
        assertEquals("<label><input id=\"falseValue1\" name=\"falseValue\"  type=\"checkbox\" value=\"true\"     /> myLabel</label>", output);
	}
	
	@Test
	public void nameAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:checkbox path=\"myModel.falseValue\" name=\"myName\" %}{% endform:checkbox %}", this.getDefaultMap());
        assertEquals("<input id=\"myName1\" name=\"myName\"  type=\"checkbox\" value=\"true\"     />", output);
	}
	
	@Test
	public void disabledAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:checkbox path=\"myModel.falseValue\" disabled=\"true\" %}{% endform:checkbox %}", this.getDefaultMap());
        assertEquals("<input id=\"falseValue1\" name=\"falseValue\" disabled=\"disabled\" type=\"checkbox\" value=\"true\"     />", output);
	}
	
	@Test
	public void idAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:checkbox path=\"myModel.falseValue\" id=\"myId\" %}{% endform:checkbox %}", this.getDefaultMap());
        assertEquals("<input id=\"myId\" name=\"falseValue\"  type=\"checkbox\" value=\"true\"     />", output);
	}

	@Test
	public void booleanValueUnchecked() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:checkbox path=\"myModel.falseValue\" %}{% endform:checkbox %}", this.getDefaultMap());
        assertEquals("<input id=\"falseValue1\" name=\"falseValue\"  type=\"checkbox\" value=\"true\"     />", output);
	}
	
	@Test
	public void booleanValueChecked() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:checkbox path=\"myModel.trueValue\" %}{% endform:checkbox %}", this.getDefaultMap());
        assertEquals("<input id=\"trueValue1\" name=\"trueValue\"  type=\"checkbox\" value=\"true\" checked=\"checked\"    />", output);
	}
	
	@Test
	public void nonbooleanValueUnchecked() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:checkbox path=\"myModel.stringValue\" value=\"someValue\" %}{% endform:checkbox %}", this.getDefaultMap());
        assertEquals("<input id=\"stringValue1\" name=\"stringValue\"  type=\"checkbox\" value=\"someValue\"     />", output);
	}
	
	@Test
	public void nonbooleanValueChecked() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:checkbox path=\"myModel.stringValue\" value=\"stringValue\" %}{% endform:checkbox %}", this.getDefaultMap());
        assertEquals("<input id=\"stringValue1\" name=\"stringValue\"  type=\"checkbox\" value=\"stringValue\" checked=\"checked\"    />", output);
	}
	
	@Test(expected = RenderException.class)
	public void missingValueAttribute() throws ParseException, CompileException, RenderException {
		renderTemplate("{% form:checkbox path=\"myModel.stringValue\" %}{% endform:checkbox %}", this.getDefaultMap());
	}
	
	@Test(expected = ParseException.class)
	public void missingPathAttribute() throws ParseException, CompileException, RenderException {
		renderTemplate("{% form:checkbox %}{% endform:checkbox %}", this.getDefaultMap());
	}
	
	@Test(expected = ParseException.class)
	public void disallowedDynamicAttribute() throws ParseException, CompileException, RenderException {
		renderTemplate("{% form:checkbox path=\"myModel.falseValue\" type=\"test\" %}{% endform:checkbox %}", this.getDefaultMap());
	}
	
	@Test
	public void dynamicAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:checkbox path=\"myModel.falseValue\" key=\"value\" %}{% endform:checkbox %}", this.getDefaultMap());
        assertEquals("<input id=\"falseValue1\" name=\"falseValue\"  type=\"checkbox\" value=\"true\"    key=\"value\" />", output);
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
		
		public boolean getTrueValue() {
			return true;
		}
		
		public boolean getFalseValue() {
			return false;
		}
	}
	
}
