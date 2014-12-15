package com.customweb.jtwig.form.tag.element;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.customweb.jtwig.form.AbstractFormTest;
import com.lyncode.jtwig.JtwigModelMap;
import com.lyncode.jtwig.exception.CompileException;
import com.lyncode.jtwig.exception.ParseException;
import com.lyncode.jtwig.exception.RenderException;

public class RadioTest extends AbstractFormTest {
	
	@Test
	public void inFormContext() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:form model=\"myModel\" %}{% form:radio path=\"falseValue\" %}{% endform:radio %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"myModel\" action=\"\" method=\"post\"   ><input id=\"falseValue1\" name=\"falseValue\"  type=\"radio\" value=\"true\"     /></form>", output);
	}
	
	@Test
	public void labelAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:radio path=\"myModel.falseValue\" label=\"myLabel\" %}{% endform:radio %}", this.getDefaultMap());
        assertEquals("<label><input id=\"falseValue1\" name=\"falseValue\"  type=\"radio\" value=\"true\"     /> myLabel</label>", output);
	}
	
	@Test
	public void nameAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:radio path=\"myModel.falseValue\" name=\"myName\" %}{% endform:radio %}", this.getDefaultMap());
        assertEquals("<input id=\"myName1\" name=\"myName\"  type=\"radio\" value=\"true\"     />", output);
	}
	
	@Test
	public void disabledAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:radio path=\"myModel.falseValue\" disabled %}{% endform:radio %}", this.getDefaultMap());
        assertEquals("<input id=\"falseValue1\" name=\"falseValue\" disabled=\"disabled\" type=\"radio\" value=\"true\"     />", output);
	}
	
	@Test
	public void idAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:radio path=\"myModel.falseValue\" id=\"myId\" %}{% endform:radio %}", this.getDefaultMap());
        assertEquals("<input id=\"myId\" name=\"falseValue\"  type=\"radio\" value=\"true\"     />", output);
	}
	
	@Test
	public void idCount() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:radio path=\"myModel.falseValue\" %}{% endform:radio %}{% form:radio path=\"myModel.falseValue\" %}{% endform:radio %}", this.getDefaultMap());
        assertEquals("<input id=\"falseValue1\" name=\"falseValue\"  type=\"radio\" value=\"true\"     /><input id=\"falseValue2\" name=\"falseValue\"  type=\"radio\" value=\"true\"     />", output);
	}

	@Test
	public void booleanValueUnchecked() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:radio path=\"myModel.falseValue\" %}{% endform:radio %}", this.getDefaultMap());
        assertEquals("<input id=\"falseValue1\" name=\"falseValue\"  type=\"radio\" value=\"true\"     />", output);
	}
	
	@Test
	public void booleanValueChecked() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:radio path=\"myModel.trueValue\" %}{% endform:radio %}", this.getDefaultMap());
        assertEquals("<input id=\"trueValue1\" name=\"trueValue\"  type=\"radio\" value=\"true\" checked=\"checked\"    />", output);
	}
	
	@Test
	public void nonbooleanValueUnchecked() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:radio path=\"myModel.stringValue\" value=\"someValue\" %}{% endform:radio %}", this.getDefaultMap());
        assertEquals("<input id=\"stringValue1\" name=\"stringValue\"  type=\"radio\" value=\"someValue\"     />", output);
	}
	
	@Test
	public void nonbooleanValueChecked() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:radio path=\"myModel.stringValue\" value=\"stringValue\" %}{% endform:radio %}", this.getDefaultMap());
        assertEquals("<input id=\"stringValue1\" name=\"stringValue\"  type=\"radio\" value=\"stringValue\" checked=\"checked\"    />", output);
	}
	
	@Test(expected = RenderException.class)
	public void missingValueAttribute() throws ParseException, CompileException, RenderException {
		renderTemplate("{% form:radio path=\"myModel.stringValue\" %}{% endform:radio %}", this.getDefaultMap());
	}
	
	@Test(expected = ParseException.class)
	public void missingPathAttribute() throws ParseException, CompileException, RenderException {
		renderTemplate("{% form:radio %}{% endform:radio %}", this.getDefaultMap());
	}
	
	@Test(expected = ParseException.class)
	public void disallowedDynamicAttribute() throws ParseException, CompileException, RenderException {
		renderTemplate("{% form:radio path=\"myModel.falseValue\" type=\"test\" %}{% endform:radio %}", this.getDefaultMap());
	}
	
	@Test
	public void dynamicAttribute() throws ParseException, CompileException, RenderException {
		String output = renderTemplate("{% form:radio path=\"myModel.falseValue\" key=\"value\" %}{% endform:radio %}", this.getDefaultMap());
        assertEquals("<input id=\"falseValue1\" name=\"falseValue\"  type=\"radio\" value=\"true\"    key=\"value\" />", output);
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
