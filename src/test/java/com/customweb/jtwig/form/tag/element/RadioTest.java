package com.customweb.jtwig.form.tag.element;

import static org.junit.Assert.assertEquals;

import org.jtwig.JtwigModelMap;
import org.jtwig.exception.JtwigException;
import org.jtwig.exception.ParseException;
import org.jtwig.exception.RenderException;
import org.junit.Test;

import com.customweb.jtwig.form.AbstractFormTest;

public class RadioTest extends AbstractFormTest {
	
	@Test
	public void inFormContext() throws JtwigException {
		String output = renderTemplate("{% form:form model=\"myModel\" %}{% form:radio path=\"falseValue\" %}{% endform:radio %}{% endform:form %}", this.getDefaultMap());
        assertEquals("<form id=\"myModel\" action=\"\" method=\"post\"   ><input id=\"falseValue1\" name=\"falseValue\"  type=\"radio\" value=\"true\"     /></form>", output);
	}
	
	@Test
	public void labelAttribute() throws JtwigException {
		String output = renderTemplate("{% form:radio path=\"myModel.falseValue\" label=\"myLabel\" %}{% endform:radio %}", this.getDefaultMap());
        assertEquals("<label><input id=\"falseValue1\" name=\"falseValue\"  type=\"radio\" value=\"true\"     /> myLabel</label>", output);
	}
	
	@Test
	public void nameAttribute() throws JtwigException {
		String output = renderTemplate("{% form:radio path=\"myModel.falseValue\" name=\"myName\" %}{% endform:radio %}", this.getDefaultMap());
        assertEquals("<input id=\"myName1\" name=\"myName\"  type=\"radio\" value=\"true\"     />", output);
	}
	
	@Test
	public void disabledAttribute() throws JtwigException {
		String output = renderTemplate("{% form:radio path=\"myModel.falseValue\" disabled=\"true\" %}{% endform:radio %}", this.getDefaultMap());
        assertEquals("<input id=\"falseValue1\" name=\"falseValue\" disabled=\"disabled\" type=\"radio\" value=\"true\"     />", output);
	}
	
	@Test
	public void idAttribute() throws JtwigException {
		String output = renderTemplate("{% form:radio path=\"myModel.falseValue\" id=\"myId\" %}{% endform:radio %}", this.getDefaultMap());
        assertEquals("<input id=\"myId\" name=\"falseValue\"  type=\"radio\" value=\"true\"     />", output);
	}
	
	@Test
	public void idCount() throws JtwigException {
		String output = renderTemplate("{% form:radio path=\"myModel.falseValue\" %}{% endform:radio %}{% form:radio path=\"myModel.falseValue\" %}{% endform:radio %}", this.getDefaultMap());
        assertEquals("<input id=\"falseValue1\" name=\"falseValue\"  type=\"radio\" value=\"true\"     /><input id=\"falseValue2\" name=\"falseValue\"  type=\"radio\" value=\"true\"     />", output);
	}

	@Test
	public void booleanValueUnchecked() throws JtwigException {
		String output = renderTemplate("{% form:radio path=\"myModel.falseValue\" %}{% endform:radio %}", this.getDefaultMap());
        assertEquals("<input id=\"falseValue1\" name=\"falseValue\"  type=\"radio\" value=\"true\"     />", output);
	}
	
	@Test
	public void booleanValueChecked() throws JtwigException {
		String output = renderTemplate("{% form:radio path=\"myModel.trueValue\" %}{% endform:radio %}", this.getDefaultMap());
        assertEquals("<input id=\"trueValue1\" name=\"trueValue\"  type=\"radio\" value=\"true\" checked=\"checked\"    />", output);
	}
	
	@Test
	public void nonbooleanValueUnchecked() throws JtwigException {
		String output = renderTemplate("{% form:radio path=\"myModel.stringValue\" value=\"someValue\" %}{% endform:radio %}", this.getDefaultMap());
        assertEquals("<input id=\"stringValue1\" name=\"stringValue\"  type=\"radio\" value=\"someValue\"     />", output);
	}
	
	@Test
	public void nonbooleanValueChecked() throws JtwigException {
		String output = renderTemplate("{% form:radio path=\"myModel.stringValue\" value=\"stringValue\" %}{% endform:radio %}", this.getDefaultMap());
        assertEquals("<input id=\"stringValue1\" name=\"stringValue\"  type=\"radio\" value=\"stringValue\" checked=\"checked\"    />", output);
	}
	
	@Test(expected = RenderException.class)
	public void missingValueAttribute() throws JtwigException {
		renderTemplate("{% form:radio path=\"myModel.stringValue\" %}{% endform:radio %}", this.getDefaultMap());
	}
	
	@Test(expected = ParseException.class)
	public void missingPathAttribute() throws JtwigException {
		renderTemplate("{% form:radio %}{% endform:radio %}", this.getDefaultMap());
	}
	
	@Test(expected = ParseException.class)
	public void disallowedDynamicAttribute() throws JtwigException {
		renderTemplate("{% form:radio path=\"myModel.falseValue\" type=\"test\" %}{% endform:radio %}", this.getDefaultMap());
	}
	
	@Test
	public void dynamicAttribute() throws JtwigException {
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
